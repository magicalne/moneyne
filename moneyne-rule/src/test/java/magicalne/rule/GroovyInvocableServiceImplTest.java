package magicalne.rule;

import magicalne.rule.impl.GroovyInvocableServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import thrift.generated.Person;
import thrift.generated.Result;

import java.nio.file.Paths;

/**
 * Created by magiclane on 20/06/2017.
 */
public class GroovyInvocableServiceImplTest {
    private final RuleService test = new GroovyInvocableServiceImpl();

    @Test
    public void compileScript() {
        String path = test.getClass().getClassLoader().getResource("test.groovy").getPath();
        test.compile(Paths.get(path));
    }

    @Test
    public void executeScript() {
        compileScript();
        Object execute = test.execute("test", "moneyne");
        Assert.assertEquals(execute, true);
    }

    @Test(expected = RuleException.class)
    public void compileScriptNotExists() {
        test.compile(Paths.get("not exists"));
    }

    @Test(expected = RuleException.class)
    public void executeFunctionNotExists() {
        compileScript();
        test.execute("not exists", "moneyne");
    }

    @Test
    public void executeFunctionImportThrift() {
        String path = test.getClass().getClassLoader().getResource("personTest.groovy").getPath();
        test.compile(Paths.get(path));
        Object execute = test.execute("personTest", new Person("zhangsan", 24, "shanghai", "123"));
        System.out.println(execute);
        Assert.assertEquals(Result.PASS, execute);
    }
}
