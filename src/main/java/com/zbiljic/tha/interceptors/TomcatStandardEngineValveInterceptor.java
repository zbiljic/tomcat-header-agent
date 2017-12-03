package com.zbiljic.tha.interceptors;

import com.zbiljic.tha.TomcatHeaderInjector;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * A {@code net.bytebuddy.ByteBuddy} interceptor for {@link org.apache.catalina.core.StandardEngineValve#invoke(Request,
 * Response)} method.
 */
public class TomcatStandardEngineValveInterceptor {

  private static final Class[] SET_HEADER_PARAMS;

  static {
    SET_HEADER_PARAMS = new Class[2];
    SET_HEADER_PARAMS[0] = String.class;
    SET_HEADER_PARAMS[1] = String.class;
  }

  @RuntimeType
  public static Object intercept(@SuperCall Callable<?> zuper,
                                 @AllArguments Object[] allArguments) throws Exception {

    try {
      if (allArguments.length == 2) {
        if (allArguments[1].getClass().getName().equals("org.apache.catalina.connector.Response")) {

          /*
           * Performed through reflection because ClassLoader cannot access Response class.
           */
          Object obj = allArguments[1];
          Class<?> clazz = obj.getClass();
          Method setHeaderMethod = clazz.getDeclaredMethod("setHeader", SET_HEADER_PARAMS);
          setHeaderMethod.invoke(obj,
            TomcatHeaderInjector.getHeaderName(),
            TomcatHeaderInjector.getHeaderValue()
          );

        }
      }
    } catch (Exception e) {
      // ignore
    }

    return zuper.call();
  }
}
