import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import thrift.generated.Mode;
import thrift.generated.MoneyneService;
import thrift.generated.Person;
import thrift.generated.PolicyExecutionReport;
import thrift.generated.Result;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
public class MoneyneServiceHandler implements MoneyneService.Iface {
    @Override
    public PolicyExecutionReport execute(String policyName, ByteBuffer object, String id) throws TException {
        TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory() );

        try {
            final Person person = Person.class.newInstance();
            final byte[] bytes = new byte[object.remaining()];
            object.get(bytes, 0, bytes.length);
            deserializer.deserialize(person, bytes);
            System.out.println(person);
            return new PolicyExecutionReport(policyName, LocalDateTime.now().toString(), Result.PASS, Mode.NORMAL,
                                             Collections.emptyList());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String ping() throws TException {
        return "pong";
    }
}
