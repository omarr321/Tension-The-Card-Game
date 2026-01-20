package Engine;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.UUID;

public class JS_Parser {
    private final ScriptEngine engine;
    private final Invocable invocable;

    public JS_Parser() {
        ScriptEngineManager manager = new ScriptEngineManager();

        this.engine = manager.getEngineByName("JavaScript");
        this.invocable = (Invocable) engine;
    }

    public void registerEffect(String effectName, String scriptContent) throws ScriptException, ScriptException {
        String functionWrapper = String.format(
                "function %s(selfId, world) { %s }",
                effectName, scriptContent
        );
        engine.eval(functionWrapper);
    }

    public void executeEffect(String effectName, UUID selfId, Object gameController) {
        try {
            // "world" is your GameController
            invocable.invokeFunction(effectName, selfId.toString(), gameController);
        } catch (ScriptException | NoSuchMethodException e) {
            // This is where the Middleman/Watcher picks up the error
            System.err.println("[JS_ERROR] Failed to execute: " + effectName);
            e.printStackTrace();
        }
    }
}
