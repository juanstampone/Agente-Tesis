import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;


import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.annotation.AnnotationDescription.Builder;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.*;

public class JavaAgent {
    private static final Logger log = Logger.getLogger(JavaAgent.class.getName());
    /**
     * As soon as the JVM initializes, This  method will be called.
     * Configs for intercepting will be read and added to Transformer so that Transformer will intercept when the
     * corresponding Java Class and Method is loaded.
     *
     * @param agentArgs       The list of agent arguments
     * @param instrumentation The instrumentation object
     * @throws InstantiationException While  an instantiation of object cause an error.
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) throws InstantiationException {

        log.info("Starting Java Agent......");
        //InterceptingClassTransformer interceptingClassTransformer = new InterceptingClassTransformer();
        
        //Class<?>[] cLasses = instrumentation.getAllLoadedClasses();
        System.out.println("redefinir classes " + instrumentation.isRedefineClassesSupported());
        System.out.println("retransform classes " + instrumentation.isRetransformClassesSupported());
        
        
      //  AgentBuilder agentBuilder = createAgentBuilder(instrumentation);
      //  agentBuilder.installOn(instrumentation);
        
      /*  for (Class<?> cls : cLasses) {
            System.out.println("PreMainAgent get loaded class:" + cls.getName());
        }
        */
        /*
        interceptingClassTransformer.init();
        instrumentation.addTransformer(interceptingClassTransformer);*/
        /*
        new AgentBuilder.Default()
        .type(ElementMatchers.nameStartsWith("org.example"))
        .transform((builder, typeDescription, classLoader, module) -> builder
                .method(ElementMatchers.any())
                .intercept(Advice.to(MyAdvice.class))
        ).installOn(instrumentation);*/
        
        new AgentBuilder.Default()
        .with(new AgentBuilder.InitializationStrategy.SelfInjection.Eager())
        .type((ElementMatchers.any()))
        .transform((builder, typeDescription, classLoader, module) -> builder
                .method(ElementMatchers.any())
                .intercept(Advice.to(MyAdvice.class))
        ).installOn(instrumentation);
        
    }
    
    
   /* private static AgentBuilder createAgentBuilder(Instrumentation instrumentation) {
    	/*return new AgentBuilder.Default()
    			.type(ElementMatchers.nameStartsWith("org.example"))
    			.tra
    			.transform((builder, typeDescription, classLoader) -> { 
    				System.out.println("teesefsdf " + typeDescription);
    				return builder.visit(Advice.to(MyAdvice.class).on(ElementMatchers.any()));
    			});
    	
    	return new AgentBuilder.Default()
        .with(new AgentBuilder.InitializationStrategy.SelfInjection.Eager())
        .type((ElementMatchers.any()))
        .transform((builder, typeDescription, classLoader, module) -> builder
                .method(ElementMatchers.any())
                .intercept(Advice.to(MyAdvice.class))
        ).installOn(instrumentation);
    	*/
    }
    	

    
    
    
    
    
    

