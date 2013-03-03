/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package griffon.plugins.perf4j;

import groovy.lang.Closure;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Andres Almiray
 */
public interface Perf4jProvider {
    <R> R withStopwatch(Closure<R> closure);

    <R> R withStopwatch(Callable<R> callable);

    <R> R withStopwatch(Map<String, Object> params, Closure<R> closure);

    <R> R withStopwatch(Map<String, Object> params, Callable<R> callable);
}
