package com.netcracker;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
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
                	<avp name='NC-Call-Direction' value='O'/>
                 	<avp name='NC-Premium-Indicator' value='F'/>
                 	<avp name='NC-Service-Context-Id' value='32260@3gpp.org'/>
                 	<avp name='NC-Roaming-Indicator' value='F'/>
                 	<% if (!context['isMSC']) {%>
                 	<avp name='NC-VLR-Number' value='${calc.call('NC-VLR-Number')}'/>
                 	<% } else {%>
                 	<avp name='NC-MSC-Address' value='${calc.call('NC-MSC-Address')}'/>
                 	<% } %>
                </command>
                """;
        String ncRoamingIndicator = """
                if (context['Country'] == 'Hong Kong') {
                    return 'F'
                } else {
                    return 'T'
                }
                """;


        String ncVlrNumber = """
                        import groovy.xml.*
                        def writer = new StringWriter()
                        def html = new MarkupBuilder(writer)
                        html.html{
                            head {
                                title 'Simple document'
                            }
                        }
                        return writer.toString()
                """;
        String ncMscAddress = """
                def generator = { String alphabet, int n ->
                    new Random().with {
                        (1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
                    }
                }
                return generator( (('A'..'Z')+('0'..'9')+('a'..'z')).join(), 15 )
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
                        def result = new GroovyShell().evaluate(head + script)
                        return result
                    }
                    
                    engine = new GStringTemplateEngine()
                    tpl = engine.createTemplate(binding.getVariable('template') as String)
                            .make([
                                    'calc': calc,
                                    'context': binding.getVariable('context')
                            ])
                    return tpl.toString()
                """;

        Map<String, Object> context = new HashMap<>();
        context.put("Country", "Belarus");
        context.put("isMSC", false);

        Map<String, String> scripts = new HashMap<>();
        scripts.put("NC-Roaming-Indicator", ncRoamingIndicator);
        scripts.put("NC-VLR-Number", ncVlrNumber);
        scripts.put("NC-MSC-Address", ncMscAddress);


        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        binding.setProperty("template", template);
        binding.setProperty("scriptService", (ScriptService) scripts::get);
        binding.setProperty("context", context);
        Object evaluate = shell.evaluate(groovyScript);

        System.out.println(evaluate);


        Script script = shell.parse("""
                if (true) {
                rturn "exxxxxx"
                } else {
                rturn 'xxx'
                }
                """);

        Object run = script.run();
        System.out.println(run);

    }

}
