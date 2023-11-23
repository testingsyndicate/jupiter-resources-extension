package com.testingsyndicate.jupiter.extensions.resources;

import static org.assertj.core.api.InstanceOfAssertFactories.*;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.testkit.engine.EngineExecutionResults;
import org.junit.platform.testkit.engine.EngineTestKit;
import org.junit.platform.testkit.engine.Event;

public class TestSupport {
  private static final String ENABLE_IN_TESTKIT = TestSupport.class.getName() + "::enableInTestKit";

  public static EngineExecutionResults runTest(
      Class<?> clazz, String name, Class<?>... parameters) {
    return EngineTestKit.engine("junit-jupiter")
        .selectors(selectMethod(clazz, name, parameters))
        .configurationParameter(ENABLE_IN_TESTKIT, "true")
        .execute();
  }

  public static <T extends Throwable> AbstractThrowableAssert<?, T> assertFailure(
      Class<T> clazz, EngineExecutionResults results) {
    return results
        .testEvents()
        .failed()
        .assertThatEvents()
        .singleElement()
        .extracting(Event::getPayload, optional(TestExecutionResult.class))
        .get(type(TestExecutionResult.class))
        .extracting(TestExecutionResult::getThrowable, optional(clazz))
        .get(throwable(clazz));
  }

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Test
  @ExtendWith(TestKitTestCondition.class)
  public @interface TestKitTest {}

  static final class TestKitTestCondition implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
      return context
          .getConfigurationParameter(ENABLE_IN_TESTKIT)
          .map(v -> enabled(ENABLE_IN_TESTKIT + "-> enabled (" + v + ")"))
          .orElse(disabled(ENABLE_IN_TESTKIT + "-> disabled"));
    }
  }
}
