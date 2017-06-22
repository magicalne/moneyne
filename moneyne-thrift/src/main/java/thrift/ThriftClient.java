import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.MoneyneService;
import thrift.generated.Person;
import thrift.generated.PolicyExecutionReport;

import java.nio.ByteBuffer;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
public class ThriftClient {
    public static void main(String[] args) {
        try {
            TTransport transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            MoneyneService.Client client = new MoneyneService.Client(protocol);

            final String pong = client.ping();
            System.out.println("ping：" + pong);

            final Person person = new Person("chandler", 22, "shanghai", "12312312312321");
            TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory() );
            final byte[] bytes = serializer.serialize(person);
            final PolicyExecutionReport report = client.execute("workflow.policy",
                                                                 ByteBuffer.wrap(bytes),
                                                                 person.getSsn());
            System.out.println(report);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }

    }
}
