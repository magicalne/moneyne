package magicalne.rule.impl;

import magicalne.rule.RuleException;
import magicalne.rule.RuleService;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created by magiclane on 20/06/2017.
 */
public class GroovyInvocableServiceImpl implements RuleService {
    private static final ScriptEngineManager FACTORY = new ScriptEngineManager();
    private static final ScriptEngine ENGINE = FACTORY.getEngineByName("groovy");
    private Invocable INVOCABLE = (Invocable) ENGINE;


    @Override
    public void compile(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            String content = new String(bytes, StandardCharsets.UTF_8);
            ENGINE.eval(content);
        } catch (IOException e) {
            throw new RuleException("Read rule file failed. filePath is: " + path.toString(), e);
        } catch (ScriptException e) {
            throw new RuleException("Compile rule failed. filePath is: " + path.toString(), e);
        }
    }

    @Override
    public Object execute(String functionName, Object... arguments) {
        try {
            return INVOCABLE.invokeFunction(functionName, arguments);
        } catch (ScriptException e) {
            throw new RuleException("Execute rule function failed, functionName: " +functionName +
                    " arguments: " + Arrays.toString(arguments), e);
        } catch (NoSuchMethodException e) {
            throw new RuleException("No such method:" + functionName, e);
        }
    }
}
