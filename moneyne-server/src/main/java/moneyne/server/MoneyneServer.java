package moneyne.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.MoneyneService;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
@Slf4j
public class MoneyneServer {
    public static void main(String[] args) {
        try {
            final MoneyneServiceHandler handler = new MoneyneServiceHandler();
            final MoneyneService.Processor<MoneyneServiceHandler> processor = new MoneyneService.Processor<>(handler);

            Runnable simpleServer = () -> {
                TNonblockingServerSocket serverTransport = null;
                try {
                    serverTransport = new TNonblockingServerSocket(9090);
                } catch (TTransportException e) {
                    e.printStackTrace();
                }
                TThreadedSelectorServer.Args thriftArgs = new TThreadedSelectorServer.Args(serverTransport);
                thriftArgs.workerThreads(200);
                TServer server = new TThreadedSelectorServer(thriftArgs.processor(processor));

                log.info("Starting the simple server...");
                server.serve();
            };
            new Thread(simpleServer).start();
        } catch (Exception e) {
            log.error("Start thrift server with exception: ", e);
        }
    }
}
