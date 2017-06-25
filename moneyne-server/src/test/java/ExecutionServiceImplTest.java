import moneyne.server.ExecutionService;
import moneyne.server.ExecutionServiceImpl;
import org.junit.Test;
import thrift.generated.Person;
import thrift.generated.PolicyExecutionReport;

/**
 * Created by magiclane on 23/06/2017.
 */
public class ExecutionServiceImplTest {
    @Test
    public void executionTest() {
        ExecutionService test = new ExecutionServiceImpl();

        Person person = new Person("zhangsan", 25, "shanghai", "123");
        PolicyExecutionReport report = test.execute("workflow", person, person.getSsn());
        System.out.println(report);
    }

}
