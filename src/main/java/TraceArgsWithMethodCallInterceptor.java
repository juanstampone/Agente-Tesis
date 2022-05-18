

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class TraceArgsWithMethodCallInterceptor {

  public static String NAME = "trace_args_with_method_call";

  private static String PARAM_INDEX = "param_index";

  private static String METHOD_TO_CALL = "method_to_call";

//  private static List<String> KNOWN_ARGS = Arrays.asList(CommonActionArgs.IS_DATE_LOGGED, PARAM_INDEX, METHOD_TO_CALL);



  private final int paramIndex;

  private final String methodToCallName;

  public TraceArgsWithMethodCallInterceptor(){
	this.paramIndex = 0;
	this.methodToCallName = METHOD_TO_CALL;
  }

  @Advice.OnMethodExit
  public Object intercept(@Origin Method method, @AllArguments Object[] allArguments, @SuperCall Callable<?> callable) throws Exception {
    String retVal = "";
    if (allArguments.length > 0) {
      try {
        Method methodToCall = allArguments[0].getClass().getMethod(METHOD_TO_CALL);
        retVal = methodToCall.invoke(allArguments[0]).toString();
      } catch (NoSuchMethodException e) {
        retVal = "No such method found: " + methodToCallName;
      }
    } else {
      retVal = "Parameter tried to be get with index " + paramIndex + " but max index is " + (allArguments.length - 1);
    }
    System.out.println("entro " + retVal);
    return callable.call();
  }
}
