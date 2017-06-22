package magicalne.rule;

import java.nio.file.Path;

/**
 * Created by magiclane on 20/06/2017.
 */
public interface RuleService {
    void compile(Path rulePath);
    Object execute(String functionName, Object argument);
}
