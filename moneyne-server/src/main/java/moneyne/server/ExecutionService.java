package moneyne.server;

import thrift.generated.PolicyExecutionReport;

import java.util.List;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
public interface ExecutionService {
    PolicyExecutionReport execute(String policyName, Object object, String id);

    List<String> publish();
}
