package com.zbiljic.tha;

import com.zbiljic.tha.interceptors.TomcatStandardEngineValveInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Agent {

  private static final AgentBuilder.Listener LISTENER = new AgentBuilder.Listener() {

    public void onDiscovery(final String typeName,
                            final ClassLoader classLoader,
                            final JavaModule module,
                            final boolean loaded) {
    }

    public void onTransformation(final TypeDescription typeDescription,
                                 final ClassLoader classLoader,
                                 final JavaModule module,
                                 final boolean loaded,
                                 final DynamicType dynamicType) {
      System.out.println("TRANSFORM " + typeDescription.getName());
    }

    public void onIgnored(final TypeDescription typeDescription,
                          final ClassLoader classLoader,
                          final JavaModule module,
                          final boolean loaded) {
    }

    public void onError(final String typeName,
                        final ClassLoader classLoader,
                        final JavaModule module,
                        final boolean loaded,
                        final Throwable throwable) {
      System.out.println("ERROR " + typeName);
      throwable.printStackTrace(System.out);
    }

    public void onComplete(final String typeName,
                           final ClassLoader classLoader,
                           final JavaModule module,
                           final boolean loaded) {
    }
  };

  private static void agent(final boolean shouldRedefine, final Instrumentation instrumentation) {

    new AgentBuilder.Default()
      .with(LISTENER)
      .with(shouldRedefine
        ? AgentBuilder.RedefinitionStrategy.RETRANSFORMATION
        : AgentBuilder.RedefinitionStrategy.DISABLED)
      .type(named("org.apache.catalina.core.StandardEngineValve"))
      .transform(new AgentBuilder.Transformer() {
                   @Override
                   public Builder<?> transform(Builder<?> builder,
                                               TypeDescription typeDescription,
                                               ClassLoader classLoader,
                                               JavaModule module) {
                     return builder
                       .method(named("invoke"))
                       .intercept(MethodDelegation.to(TomcatStandardEngineValveInterceptor.class));
                   }
                 }
      )
      .installOn(instrumentation);
  }

  public static void premain(final String agentArgs, final Instrumentation instrumentation) {
    agent(false, instrumentation);
  }

  public static void agentmain(final String agentArgs, final Instrumentation instrumentation) {
    agent(true, instrumentation);
  }

}
