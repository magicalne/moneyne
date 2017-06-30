package moneyne.server;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import moneyne.server.exception.UnknownMethodNameException;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import thrift.generated.MobileDataBodyList;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by magiclane on 29/06/2017.
 */
@Slf4j
class InternalHttpClient {
    private static final AsyncHttpClient CLIENT = new DefaultAsyncHttpClient();
    private static final TDeserializer DESERIALIZER = new TDeserializer(TJSONProtocol::new);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Response getHttpRequest(String url) throws ExecutionException, InterruptedException {
        CompletableFuture<Response> future = CLIENT
                .prepareGet("http://localhost:8890/internal" + url)
                .execute()
                .toCompletableFuture();

        return future.get();
    }

    /**
     *
     * @param reqMethod getContacts, getSMSs, getXXX, etc...
     * @param parameterList Url parameters, like /130123123/chandler/25
     * @return Deserialize response body to POJO.
     */
    public static Object getResponse(String reqMethod, List<Object> parameterList) {
        Preconditions.checkState(!Strings.isNullOrEmpty(reqMethod));
        Preconditions.checkState(reqMethod.startsWith("get"));

        final StringBuilder url = new StringBuilder("");
        final char slash = '/';
        if (parameterList != null && !parameterList.isEmpty()) {
            for (Object parameter : parameterList) {
                url.append(slash).append(parameter);
            }
        }

        String methodName = reqMethod.substring(3).toLowerCase(); //drop prefix "get" and lower case.
        try {
            switch (methodName) {
                case "contacts":
                    return getResponseBody("/contacts", url);
                case "SMSs":
                    return getResponseBody("/SMSs", url);
                case "calls":
                    return getResponseBody("/calls", url);
                default: throw new UnknownMethodNameException("Received unknown method name: " + methodName);

            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RemoteServiceFailedException("Cannot fetch remote data.", e);
        }
    }

    private static Object getResponseBody(String startWith, StringBuilder url)
            throws ExecutionException, InterruptedException {
        Response res = getHttpRequest(startWith + url.toString());
        if (res.getStatusCode() == 200) {
            String responseBody = res.getResponseBody();
            OBJECT_MAPPER.
//                throw new RemoteServiceFailedException("Deserialize mobile data body list failed.", e);
            return mobileDataBodyList;
        } else {
            throw new RemoteServiceFailedException("Send http request failed.");
        }
    }
}
