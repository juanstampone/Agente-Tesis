
import javax.xml.ws.soap.Addressing;

import net.bytebuddy.asm.Advice;

public class MyAdvice {
	
	@Advice.OnMethodEnter  
	public static void enter(@Advice.Origin Class klass, @Advice.Origin("#m") String methodName) {
		Stack.push();
		Stack.log(klass.getSimpleName() + " " + methodName + "()");
	}
	
    @Advice.OnMethodExit
    static void getAllMethods(@Advice.Origin String method)  throws Exception {
    	
        System.out.println("este es el metodo " + method);
    }
	
}
