import com.google.common.base.Preconditions;
import magicalne.rule.RuleService;
import magicalne.rule.impl.GroovyInvocableServiceImpl;
import thrift.generated.PolicyExecutionReport;
import thrift.generated.Result;
import thrift.generated.RuleResult;
import workflow.DSLParseService;
import workflow.Mode;
import workflow.Policy;
import workflow.Step;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
public class ExecutionServiceImpl implements ExecutionService {
    private static final String REPO_PATH = "repo";
    private static final ConcurrentMap<String, Policy> POLICY_CONCURRENT_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, String> RULE_CONCURRENT_MAP = new ConcurrentHashMap<>();
    protected static final RuleService GROOVY_INVOCABLE_SERVICE = new GroovyInvocableServiceImpl();


    static {
        final URL repoUrl = ExecutionService.class.getClass().getClassLoader().getResource(REPO_PATH);
        Preconditions.checkNotNull(repoUrl, "There is no repo!");
        final String repoPath = repoUrl.getPath();
        try {
            Files.walk(Paths.get(repoPath)).forEach(path -> {
                final boolean isDir = Files.isDirectory(path);
                if (isDir) {
                    try {
                        Files.walk(path).forEach(innerPath -> {
                            if (innerPath.endsWith(".policy")) {
                                try {
                                    final Policy policy = DSLParseService.INSTANCE.policyParser(path);
                                    Preconditions.checkNotNull(policy, "Policy cannot be null here");
                                    POLICY_CONCURRENT_MAP.put(policy.getPolicyId(), policy);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (innerPath.endsWith(".groovy")) {
                                GROOVY_INVOCABLE_SERVICE.compile(innerPath);
                                try {
                                    final byte[] bytes = Files.readAllBytes(innerPath);
                                    final String ruleContent = new String(bytes, StandardCharsets.UTF_8);
                                    final String filename = innerPath.getFileName().toString();
                                    final String ruleName = com.google.common.io.Files.getNameWithoutExtension(
                                            filename);
                                    RULE_CONCURRENT_MAP.putIfAbsent(ruleName, ruleContent);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                throw new UnknownFileTypeException(innerPath.toString());
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public PolicyExecutionReport execute(String policyName, Object object, String id) {
        final PolicyExecutionReport res = new PolicyExecutionReport();
//        res.getste
        res.setPolicyName(policyName);
        final Policy policy = POLICY_CONCURRENT_MAP.get(policyName);
        final Step initStep = policy.getInitStep();
//        execute(initStep, object);
        return null;
    }

    /*private Result execute(Step step, Object object) {
        if (step.getMode() == null || step.getMode() == Mode.NORMAL) {
            final Set<String> ruleSet = step.getRuleSet();
            if (ruleSet != null && !ruleSet.isEmpty()) {
                ruleSet.stream()
                        .map(ruleName -> {
                            final RuleResult ruleResult = new RuleResult();
                            ruleResult.setRuleName(ruleName);
                            final Result result = (Result) GROOVY_INVOCABLE_SERVICE.execute(ruleName, object);
                            ruleResult.setResult(result);

                            return ruleResult;
                        });
            }
        }
    }*/
}
