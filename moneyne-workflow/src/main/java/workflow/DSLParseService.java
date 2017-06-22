package workflow;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
public enum DSLParseService {
    INSTANCE;

    DSLParseService() {
    }

    final PolicyParser POLICY_PARSER = Parboiled.createParser(PolicyParser.class);

    public Policy policyParser(Path path) throws IOException {
        final byte[] bytes = Files.readAllBytes(path);
        final String content = new String(bytes, StandardCharsets.UTF_8);
        final String filename = path.getFileName().toString();
        final String nameWithoutExtension = com.google.common.io.Files.getNameWithoutExtension(filename);
        return policyParser(nameWithoutExtension, content);
    }

    public Policy policyParser(String policyName, String content) {

        final ReportingParseRunner<Policy> runner = new ReportingParseRunner<>(POLICY_PARSER.Policy(policyName));
        final ParsingResult<Policy> parsingResult = runner.run(content);
        //TODO handle exception
        return parsingResult.resultValue;
    }
}
