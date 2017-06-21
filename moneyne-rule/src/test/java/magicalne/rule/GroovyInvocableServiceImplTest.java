package magicalne.rule;

import magicalne.rule.impl.GroovyInvocableServiceImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by magiclane on 20/06/2017.
 */
public class GroovyInvocableServiceImplTest {
    private final RuleService test = new GroovyInvocableServiceImpl();

    @Test
    public void compileScript() {
        String path = test.getClass().getClassLoader().getResource("test.groovy").getPath();
        test.compile(path);
    }

    @Test
    public void executeScript() {
        compileScript();
        Object execute = test.execute("test", "moneyne");
        Assert.assertEquals(execute, true);
    }

    @Test(expected = RuleException.class)
    public void compileScriptNotExists() {
        test.compile("not exists");
    }

    @Test(expected = RuleException.class)
    public void executeFunctionNotExists() {
        compileScript();
        test.execute("not exists", "moneyne");
    }
}