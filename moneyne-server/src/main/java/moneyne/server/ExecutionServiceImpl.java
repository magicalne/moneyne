package moneyne.server;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import magicalne.rule.RuleService;
import magicalne.rule.impl.GroovyInvocableServiceImpl;
import thrift.generated.PolicyExecutionReport;
import thrift.generated.Result;
import thrift.generated.RuleResult;
import thrift.generated.StepResult;
import workflow.DSLParseService;
import workflow.Mode;
import workflow.Policy;
import workflow.Step;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
public class ExecutionServiceImpl implements ExecutionService {
    private static final String REPO_PATH = "/repo";
    private static final ConcurrentMap<String, Policy> POLICY_CONCURRENT_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, String> RULE_CONCURRENT_MAP = new ConcurrentHashMap<>();
    private static final RuleService GROOVY_INVOCABLE_SERVICE = new GroovyInvocableServiceImpl();
    private static final String RETURN = "return";

    /*
     * Load files into memory.
     */
    static {
        final URL repoUrl = ExecutionServiceImpl.class.getClass().getResource(REPO_PATH);
        Preconditions.checkNotNull(repoUrl, "There is no repo!");
        final String repoPath = repoUrl.getPath();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(repoPath))) {
            for (Path projectPath: stream) {
                if (Files.isDirectory(projectPath)) {
                    try (DirectoryStream<Path> projectStream = Files.newDirectoryStream(projectPath)) {
                        for (Path innerPath : projectStream) {
                            String filename = innerPath.getFileName().toString();
                            if (filename.endsWith(".policy")) {
                                final Policy policy = DSLParseService.INSTANCE.policyParser(innerPath);
                                Preconditions.checkNotNull(policy, "Policy cannot be null here");
                                POLICY_CONCURRENT_MAP.put(policy.getPolicyId(), policy);
                            } else if (filename.endsWith(".groovy")) {
                                GROOVY_INVOCABLE_SERVICE.compile(innerPath);
                                final byte[] bytes = Files.readAllBytes(innerPath);
                                final String ruleContent = new String(bytes, StandardCharsets.UTF_8);
                                final String ruleName = com.google.common.io.Files.getNameWithoutExtension(
                                        filename);
                                RULE_CONCURRENT_MAP.putIfAbsent(ruleName, ruleContent);
                            } else {
                                throw new UnknownFileTypeException(innerPath.toString());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Execute a policy.
     * @param policyName policy DSL filename without suffix.
     * @param object The argument object used in ruleSet. The objects should be defined in thrift IDL file.
     * @param id Should specify an Id. Make it easy to identify in log and do A/B testing experiment.
     * @return PolicyExecutionReport -- A detail report about what is going on.
     */
    @Override
    public PolicyExecutionReport execute(String policyName, Object object, String id) {
        final List<StepResult> stepResults = new LinkedList<>();
        final Policy policy = POLICY_CONCURRENT_MAP.get(policyName);
        final Step initStep = policy.getInitStep();
        StepResult initStepResult = executeStep(initStep, object);
        stepResults.add(initStepResult);
        Map<String, Step> stepMap = policy.getSteps();
        Result finalResult = initStepResult.getStepResult();
        finalResult = stepSwitch(object, stepResults, initStep, initStepResult, stepMap, finalResult);
        return new PolicyExecutionReport(policyName, LocalDateTime.now().toString(), finalResult, stepResults);
    }

    /**
     * Continue execute according to next step flow.
     * @param object Argument of ruleSet
     * @param stepResults Stored old step results.
     * @param step Present step. Could find next step from the present one.
     * @param stepResult According to present step result, it decides which flow should be executed next.
     * @param stepMap Stored all the step in memory.
     * @param finalResult The final result of whole policy.
     * @return The final Result.
     */
    private Result stepSwitch(Object object, List<StepResult> stepResults, Step step,
                            StepResult stepResult, Map<String, Step> stepMap, Result finalResult) {
        switch (stepResult.getStepResult()) {
            case PASS:
                return executeWorkflow(object, step.getPassStep(), stepMap, stepResults, finalResult);
            case UNDEFINE:
                return executeWorkflow(object, step.getUndefineStep(), stepMap, stepResults, finalResult);
            case REJECT:
                return executeWorkflow(object, step.getRejectStep(), stepMap, stepResults, finalResult);
//            default: break;
            default: return Result.PASS;
        }
    }

    /**
     * Recursively execute workflow.
     * @param object The argument of ruleSet.
     * @param nextStepName Next step name.
     * @param stepMap All the steps stored in memory.
     * @param stepResults All the old step results.
     * @param finalResult The final result.
     * @return Next workflow or final result.
     */
    private Result executeWorkflow(Object object, String nextStepName, Map<String, Step> stepMap,
                                 List<StepResult> stepResults, Result finalResult) {

        if (Strings.isNullOrEmpty(nextStepName) || RETURN.equals(nextStepName)) {
            return finalResult;
        }
        Step step = stepMap.get(nextStepName);
        StepResult stepResult = executeStep(step, object);
        finalResult = setFinalResult(stepResult.getStepResult(), finalResult);
        stepResults.add(stepResult);
        return stepSwitch(object, stepResults, step, stepResult, stepMap, finalResult);
    }

    /**
     * Execute single step.
     * According to <code>Mode</code>, specify a way to execute.
     * Normally, just run ruleSet in serial sequence.
     * @param step Present step.
     * @param object The argument.
     * @return Detail step result.
     */
    private StepResult executeStep(Step step, Object object) {
        Preconditions.checkNotNull(step);
        Preconditions.checkNotNull(object);

        if (step.getMode() == null || step.getMode() == Mode.NORMAL) {
            Set<String> ruleSet = step.getRuleSet();
            final Set<RuleResult> ruleResultSet = new HashSet<>(ruleSet.size() / 3 * 4);
            Result finalResult = Result.PASS;
            for (String ruleName : ruleSet) {
                String functionName = com.google.common.io.Files.getNameWithoutExtension(ruleName);
                Result presentResult = (Result) GROOVY_INVOCABLE_SERVICE.execute(functionName, object);
                RuleResult ruleResult = new RuleResult(ruleName, presentResult);
                ruleResultSet.add(ruleResult);
                finalResult = setFinalResult(presentResult, finalResult);
            }
            return new StepResult(step.getName(), ruleResultSet, finalResult);
        }

        throw new MoneyneExecutionException("Unknown mode type.");
    }

    /**
     * REJECT > UNDEFINE > pass
     * @return finalResult
     */
    private Result setFinalResult(Result presentResult, Result finalResult) {
        switch (presentResult) {
            case REJECT: return Result.REJECT;
            case UNDEFINE:  return (finalResult == Result.REJECT) ? Result.REJECT : Result.UNDEFINE;
            case PASS: return finalResult;
            default: return finalResult;
        }
    }
}
