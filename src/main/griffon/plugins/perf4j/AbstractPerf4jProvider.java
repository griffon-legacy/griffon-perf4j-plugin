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

import griffon.exceptions.GriffonException;
import groovy.lang.Closure;
import org.perf4j.StopWatch;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Andres Almiray
 */
public abstract class AbstractPerf4jProvider implements Perf4jProvider {
    public <R> R withStopwatch(Closure<R> closure) {
        return withStopwatch(Collections.<String, Object>emptyMap(), closure);
    }

    public <R> R withStopwatch(Callable<R> callable) {
        return withStopwatch(Collections.<String, Object>emptyMap(), callable);
    }

    public <R> R withStopwatch(Map<String, Object> params, Closure<R> closure) {
        if (closure != null) {
            StopWatch stopWatch = getPerf4jStopWatch(params);
            R returnVal = null;
            try {
                stopWatch.start();
                returnVal = closure.call();
            } finally {
                stopWatch.stop();
            }
            return returnVal;
        }
        return null;
    }

    public <R> R withStopwatch(Map<String, Object> params, Callable<R> callable) {
        if (callable != null) {
            StopWatch stopWatch = getPerf4jStopWatch(params);
            R returnVal = null;
            try {
                stopWatch.start();
                returnVal = callable.call();
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                } else if (e instanceof InvocationTargetException) {
                    throw new GriffonException(((InvocationTargetException) e).getTargetException());
                } else {
                    throw new GriffonException(e);
                }
            } finally {
                stopWatch.stop();
            }
            return returnVal;
        }
        return null;
    }

    protected abstract StopWatch getPerf4jStopWatch(Map<String, Object> params);
}
