package workflow;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: zehui.lv@dianrong on 6/15/17.
 */
public class Policy {
    private String namespace;
    private String policyId;
    private Step initStep;
    private Map<String, Step> steps;

    public Policy() {
    }

    public String getNamespace() {
        return namespace;
    }

    public Policy setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public Step getInitStep() {
        return initStep;
    }

    public Policy setInitStep(Step initStep) {
        this.initStep = initStep;
        return this;
    }

    public Map<String, Step> getSteps() {
        return steps;
    }

    public Policy addStep(String stepName, Step step) {
        if (this.steps == null) {
            this.steps = new HashMap<>();
        }
        this.steps.put(stepName, step);
        return this;
    }

    public void setSteps(Map<String, Step> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "namespace='" + namespace + '\'' +
                ", policyId='" + policyId + '\'' +
                ", initStep=" + initStep +
                ", steps=" + steps +
                '}';
    }
}
