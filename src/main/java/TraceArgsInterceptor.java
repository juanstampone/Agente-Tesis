import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;


public class TraceArgsInterceptor {

	  public static String NAME = "trace_args";

	  private static String LOG_THRESHOLD_MILLISECONDS = "log_threshold_ms";


	  private final long logThresholdMs = 0L;


	  @RuntimeType
	  public Object intercept(@Origin Method method, @AllArguments Object[] allArguments, @SuperCall Callable<?> callable) throws Exception {
	    long start = (logThresholdMs == 0) ? 0 : System.currentTimeMillis();
	    try {
	      return callable.call();
	    } finally {
	      long end = (this.logThresholdMs == 0) ? 0 : System.currentTimeMillis();
	      if (this.logThresholdMs == 0 || end - start >= this.logThresholdMs) {
	        System.out.println("metodo " + method + " con los parametros " + Arrays.toString(allArguments));
	      }
	    }
	  }

}
