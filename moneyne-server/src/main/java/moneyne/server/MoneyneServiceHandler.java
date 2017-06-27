package moneyne.server;

import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import thrift.generated.*;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
public class MoneyneServiceHandler implements MoneyneService.Iface {
    private final ExecutionService executionService = new ExecutionServiceImpl();
    @Override
    public PolicyExecutionReport execute(String policyName, ByteBuffer object, String className, String id)
            throws TException {

        TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory() );
        try {
            Class clazz = Class.forName(className);
            final TBase instance = (TBase) clazz.newInstance();
            final byte[] bytes = new byte[object.remaining()];
            object.get(bytes, 0, bytes.length);
            deserializer.deserialize(instance, bytes);
            System.out.println(instance);
            return executionService.execute(policyName, instance, id);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> publish() throws TException {
        return executionService.publish();
    }

    @Override
    public String ping() throws TException {
        return "pong";
    }
}
