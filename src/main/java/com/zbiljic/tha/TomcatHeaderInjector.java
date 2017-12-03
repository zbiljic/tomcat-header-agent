package com.zbiljic.tha;

/**
 * Used to control the default Tomcat header injector system.
 */
public enum TomcatHeaderInjector {
  ;

  public static final String THI_HEADER_NAME_ENVIRONMENT_VARIABLE = "THI_HEADER_NAME";

  public static final String THI_HEADER_VALUE_ENVIRONMENT_VARIABLE = "THI_HEADER_VALUE";

  public static final String DEFAULT_THI_HEADER_NAME = "X-Instrumented-By";

  public static final String DEFAULT_THI_HEADER_VALUE = "thi";

  private static final String headerName;
  private static final String headerValue;

  static {
    // header name
    String name = System.getenv(THI_HEADER_NAME_ENVIRONMENT_VARIABLE);
    if (name == null) {
      name = DEFAULT_THI_HEADER_NAME;
    }
    headerName = name;
    // header value
    String value = System.getenv(THI_HEADER_VALUE_ENVIRONMENT_VARIABLE);
    if (value == null) {
      value = DEFAULT_THI_HEADER_VALUE;
    }
    headerValue = value;
  }

  public static String getHeaderName() {
    return headerName;
  }

  public static String getHeaderValue() {
    return headerValue;
  }

}
