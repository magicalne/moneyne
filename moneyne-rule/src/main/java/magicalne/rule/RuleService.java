package magicalne.rule;

/**
 * Created by magiclane on 20/06/2017.
 */
public interface RuleService {
    void compile(String rulePath);
    Object execute(String functionName, Object argument);
}
