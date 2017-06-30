package workflow;

import com.google.common.base.Objects;

/**
 * Created by magiclane on 29/06/2017.
 */
public class RuleElement {
    private String ruleName;
    private String dependOn;

    public RuleElement() {
    }

    public String getRuleName() {
        return ruleName;
    }

    public RuleElement setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public String getDependOn() {
        return dependOn;
    }

    public RuleElement setDependOn(String dependOn) {
        this.dependOn = dependOn;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleElement that = (RuleElement) o;
        return Objects.equal(ruleName, that.ruleName) &&
                Objects.equal(dependOn, that.dependOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ruleName, dependOn);
    }

    @Override
    public String toString() {
        return "RuleElement{" +
                "ruleName='" + ruleName + '\'' +
                ", dependOn='" + dependOn + '\'' +
                '}';
    }
}
