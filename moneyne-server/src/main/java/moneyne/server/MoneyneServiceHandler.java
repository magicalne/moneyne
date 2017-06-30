package moneyne.server;

import lombok.extern.slf4j.Slf4j;
import moneyne.server.exception.MonenyeExecutionException;
import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import thrift.generated.*;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
@Slf4j
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
            return executionService.execute(policyName, instance, id);
        } catch (InstantiationException | IllegalAccessException |
                ClassNotFoundException | MonenyeExecutionException e) {
            log.info("Something wrong when handle service. {}", e);
            throw new TException(e);
        }
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
