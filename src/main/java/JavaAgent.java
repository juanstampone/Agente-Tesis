import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;


import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.annotation.AnnotationDescription.Builder;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
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
        
        System.out.println("redefinir classes " + instrumentation.isRedefineClassesSupported());
        System.out.println("retransform classes " + instrumentation.isRetransformClassesSupported());
        
        TraceArgsInterceptor interceptor = new TraceArgsInterceptor();
        
        AgentBuilder agentBuilder = new AgentBuilder.Default();
        
        agentBuilder.
        	type(ElementMatchers.any())
        	.transform((builder, type, classLoader, module) -> builder.method(ElementMatchers.any()).intercept(MethodDelegation.to(interceptor)))
            .installOn(instrumentation);
     
    }
    
    

    }
    	

    
    
    
    
    
    

