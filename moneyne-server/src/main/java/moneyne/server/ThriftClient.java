package moneyne.server;

import lombok.extern.slf4j.Slf4j;
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
 * Author:chandler on 6/22/17.
 */
@Slf4j
public class ThriftClient {
    public static void main(String[] args) {
        try {
            TTransport transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            MoneyneService.Client client = new MoneyneService.Client(protocol);

            final String pong = client.ping();

            log.info("ping: ", pong);

            final Person person = new Person("chandler", 24, "shanghai", "12312312312321");
            TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory() );
            final byte[] bytes = serializer.serialize(person);
            final PolicyExecutionReport report = client.execute("workflow",
                                                                 ByteBuffer.wrap(bytes),
                                                                 Person.class.getName(),
                                                                 person.getSsn());
            log.info("Response report: {}", report);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }

    }
}
