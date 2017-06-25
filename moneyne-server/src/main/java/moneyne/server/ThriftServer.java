package moneyne.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.MoneyneService;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
public class ThriftServer {
    public static void main(String[] args) {
        try {
            final MoneyneServiceHandler handler = new MoneyneServiceHandler();
            final MoneyneService.Processor<MoneyneServiceHandler> processor = new MoneyneService.Processor<>(handler);

            Runnable simpleServer = () -> {
                TServerTransport serverTransport = null;
                try {
                    serverTransport = new TServerSocket(9090);
                } catch (TTransportException e) {
                    e.printStackTrace();
                }
                TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

                // Use this for a multithreaded server
                // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

                System.out.println("Starting the simple server...");
                server.serve();
            };
            new Thread(simpleServer).start();
        } catch (Exception e) {
            System.err.println("Exception" + e);
        }
    }
}
