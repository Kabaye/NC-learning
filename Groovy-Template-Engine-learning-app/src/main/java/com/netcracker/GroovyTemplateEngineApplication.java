package com.netcracker;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class GroovyTemplateEngineApplication {

    public static void main(String[] args) {
//        SpringApplication.run(GroovyTemplateEngineApplication.class, args);
        String template = """ 
                <command name='CCR-I'>
                	<avp name='NC-Roaming-Indicator' value='${calc.call('NC-Roaming-Indicator')}'/>
                </command>
                """;
        String script = """
                if (context['Country'] == 'Hong Kong') {
                    return 'F'
                } else {
                    return 'T'
                }
                """;

        String groovyScript = """
                    import groovy.text.GStringTemplateEngine
                    import com.netcracker.ScriptService
                    
                    def calc = { String scriptName ->
                        def scriptService = binding.getVariable('scriptService') as ScriptService
                        def script = scriptService.getScript(scriptName)
                        def head = ""
                        for (e in binding.getVariables()) {
                            if (e.key as String in ['context']){
                                head += "def ${e.key} = ${e.value.inspect()}\\n"
                            }
                        }
                        println(head + script)
                        def result = new GroovyShell().evaluate(head + script)
                        return result
                    }
                    
                    engine = new GStringTemplateEngine()
                    tpl = engine.createTemplate(binding.getVariable('template') as String)
                            .make([
                                    'calc': calc
                            ])
                    return tpl.toString()
                """;

        Map<String, Object> context = new HashMap<>();
        context.put("Country", "Hong Kong");


        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        binding.setProperty("template", template);
        binding.setProperty("scriptService", (ScriptService) name -> script);
        binding.setProperty("context", context);
        Object evaluate = shell.evaluate(groovyScript);

        System.out.println(evaluate);

    }

}
