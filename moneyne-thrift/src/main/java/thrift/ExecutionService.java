import thrift.generated.PolicyExecutionReport;

/**
 * Author: zehui.lv@dianrong on 6/22/17.
 */
public interface ExecutionService {
    PolicyExecutionReport execute(String policyName, Object object, String id);
}
