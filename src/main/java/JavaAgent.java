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

	public static void premain(String agentArgs, Instrumentation instrumentation) throws InstantiationException {
		TraceArgsInterceptor interceptor = new TraceArgsInterceptor();
		AgentBuilder agentBuilder = new AgentBuilder.Default();
		agentBuilder
				.type(ElementMatchers.any()).transform((builder, type, classLoader, module) -> builder
						.method(ElementMatchers.any()).intercept(MethodDelegation.to(interceptor)))
				.installOn(instrumentation);
	}

}
