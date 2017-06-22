package workflow;

import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by magiclane on 20/06/2017.
 */
public class ParserTest {
    @Test
    public void parse() throws IOException {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final URL resource = classLoader.getResource("workflow.policy");
        if (resource != null) {
            final Path path = Paths.get(resource.getPath());
            final byte[] bytes = Files.readAllBytes(path);
            final String content = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(content);

            final PolicyParser parser = Parboiled.createParser(PolicyParser.class);
            ParsingResult<Policy> result = new TracingParseRunner<Policy>(parser.Policy("workflow")).run(content);
            String parseTreePrintOut = ParseTreeUtils.printNodeTree(result);
            System.out.println(parseTreePrintOut);
            System.out.println("==============================================");
            Policy policy = result.resultValue;
            System.out.println(policy);

            Assert.assertEquals("workflow", policy.getPolicyId());
            Assert.assertEquals(4, policy.getInitStep().getRuleSet().size());
            Assert.assertEquals(Mode.NORMAL, policy.getInitStep().getMode());
            Assert.assertEquals(2, policy.getSteps().size());
        }
    }
}
