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
public class Perf4jContributionAdapter implements Perf4jContributionHandler {
    private Perf4jProvider provider = DefaultPerf4jProvider.getInstance();

    public void setPerf4jProvider(Perf4jProvider provider) {
        this.provider = provider != null ? provider : DefaultPerf4jProvider.getInstance();
    }

    public Perf4jProvider getPerf4jProvider() {
        return provider;
    }

    public <R> R withStopwatch(Closure<R> closure) {
        return provider.withStopwatch(closure);
    }

    public <R> R withStopwatch(Callable<R> callable) {
        return provider.withStopwatch(callable);
    }

    public <R> R withStopwatch(Map<String, Object> params, Closure<R> closure) {
        return provider.withStopwatch(params, closure);
    }

    public <R> R withStopwatch(Map<String, Object> params, Callable<R> callable) {
        return provider.withStopwatch(params, callable);
    }
}
