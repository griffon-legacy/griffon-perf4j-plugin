/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lombok.core.handlers;

import lombok.core.BaseConstants;
import lombok.core.util.MethodDescriptor;

import static lombok.core.util.MethodDescriptor.*;

/**
 * @author Andres Almiray
 */
public interface Perf4jAwareConstants extends BaseConstants {
    String PERF4j_PROVIDER_TYPE = "griffon.plugins.perf4j.Perf4jProvider";
    String DEFAULT_PERF4j_PROVIDER_TYPE = "griffon.plugins.perf4j.DefaultPerf4jProvider";
    String PERF4j_CONTRIBUTION_HANDLER_TYPE = "griffon.plugins.perf4j.Perf4jContributionHandler";
    String JAVA_UTIL_CONCURRENT_CALLABLE = "java.util.concurrent.Callable";
    String PERF4j_PROVIDER_FIELD_NAME = "this$perf4jProvider";
    String METHOD_GET_PERF4j_PROVIDER = "getPerf4jProvider";
    String METHOD_SET_PERF4j_PROVIDER = "setPerf4jProvider";
    String METHOD_WITH_STOPWATCH = "withStopwatch";
    String PROVIDER = "provider";

    MethodDescriptor[] METHODS = new MethodDescriptor[]{
        MethodDescriptor.method(
            type(R),
            typeParams(R),
            METHOD_WITH_STOPWATCH,
            args(
                type(GROOVY_LANG_CLOSURE, R))
        ),
        MethodDescriptor.method(
            type(R),
            typeParams(R),
            METHOD_WITH_STOPWATCH,
            args(
                type(JAVA_UTIL_CONCURRENT_CALLABLE, R))
        ),
        MethodDescriptor.method(
            type(R),
            typeParams(R),
            METHOD_WITH_STOPWATCH,
            args(
                type(JAVA_UTIL_MAP, JAVA_LANG_STRING, JAVA_LANG_OBJECT),
                type(GROOVY_LANG_CLOSURE, R))
        ),
        MethodDescriptor.method(
            type(R),
            typeParams(R),
            METHOD_WITH_STOPWATCH,
            args(
                type(JAVA_UTIL_MAP, JAVA_LANG_STRING, JAVA_LANG_OBJECT),
                type(JAVA_UTIL_CONCURRENT_CALLABLE, R))
        )
    };
}
