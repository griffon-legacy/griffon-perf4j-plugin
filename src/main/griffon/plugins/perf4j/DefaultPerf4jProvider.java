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

import groovy.util.ConfigObject;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;

import java.util.Map;

import static griffon.util.ApplicationHolder.getApplication;
import static griffon.util.ConfigUtils.*;
import static griffon.util.GriffonNameUtils.isBlank;

/**
 * @author Andres Almiray
 */
public class DefaultPerf4jProvider extends AbstractPerf4jProvider {
    private static final DefaultPerf4jProvider INSTANCE;

    static {
        INSTANCE = new DefaultPerf4jProvider();
    }

    public static DefaultPerf4jProvider getInstance() {
        return INSTANCE;
    }

    @Override
    protected StopWatch getPerf4jStopWatch(Map<String, Object> params) {
        ConfigObject config = mergeParams(params);
        Slf4JStopWatch stopWatch = createStopwatch(config);
        int intVal = getInt(config, "exceptionPriority", Integer.MIN_VALUE);
        if (intVal != Integer.MIN_VALUE) stopWatch.setExceptionPriority(intVal);
        boolean normalAndSlowSuffixesEnabled = getBoolean(config, "normalAndSlowSuffixesEnabled", false);
        stopWatch.setNormalAndSlowSuffixesEnabled(normalAndSlowSuffixesEnabled);
        intVal = getInt(config, "normalPriority", Integer.MIN_VALUE);
        if (intVal != Integer.MIN_VALUE) stopWatch.setNormalPriority(intVal);
        if (normalAndSlowSuffixesEnabled) {
            String stringVal = getString(config, "normalSuffix", "");
            if (!isBlank(stringVal)) stopWatch.setNormalSuffix(stringVal);
            stringVal = getString(config, "slowSuffix", "");
            if (!isBlank(stringVal)) stopWatch.setSlowSuffix(stringVal);
        }
        long longVal = getLong(config, "timeThreshold", Long.MIN_VALUE);
        if (longVal != Long.MIN_VALUE) stopWatch.setTimeThreshold(longVal);
        return stopWatch;
    }

    private static Slf4JStopWatch createStopwatch(ConfigObject config) {
        String tag = getConfigValueAsString(config, "tag", "");
        String message = getConfigValueAsString(config, "message", "");
        if (getConfigValueAsBoolean(config, "enabled", true)) {
            return !isBlank(message) ? new Slf4JStopWatch(tag, message) : new Slf4JStopWatch(tag);
        }
        return new NoopStopWatch();
    }

    private static ConfigObject mergeParams(Map<String, Object> params) {
        ConfigObject config = new ConfigObject();
        ConfigObject per4jConfig = (ConfigObject) getApplication().getConfig().get("perf4j");
        if (null != per4jConfig) config.merge(per4jConfig);
        if (null != params) config.putAll(params);
        return config;
    }

    private static int getInt(ConfigObject config, String key, int defaultValue) {
        return getConfigValueAsInt(config, key, defaultValue);
    }

    private static boolean getBoolean(ConfigObject config, String key, boolean defaultValue) {
        return getConfigValueAsBoolean(config, key, defaultValue);
    }

    private static String getString(ConfigObject config, String key, String defaultValue) {
        return getConfigValueAsString(config, key, defaultValue);
    }

    private static long getLong(ConfigObject config, String key, long defaultValue) {
        Object value = getConfigValue(config, key, defaultValue);
        return DefaultTypeTransformation.castToNumber(value).longValue();
    }
}
