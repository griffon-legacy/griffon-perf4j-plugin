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

package lombok.javac.handlers;

import com.sun.tools.javac.tree.JCTree;
import griffon.plugins.perf4j.Perf4jAware;
import lombok.core.AnnotationValues;
import lombok.core.handlers.Perf4jAwareConstants;
import lombok.core.handlers.Perf4jAwareHandler;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.ast.JavacType;

import static lombok.core.util.ErrorMessages.canBeUsedOnClassAndEnumOnly;
import static lombok.javac.handlers.JavacHandlerUtil.deleteAnnotationIfNeccessary;

/**
 * @author Andres Almiray
 */
public class HandlePerf4jAware extends JavacAnnotationHandler<Perf4jAware> {
    private final JavacPerf4jAwareHandler handler = new JavacPerf4jAwareHandler();

    @Override
    public void handle(final AnnotationValues<Perf4jAware> annotation, final JCTree.JCAnnotation source, final JavacNode annotationNode) {
        deleteAnnotationIfNeccessary(annotationNode, Perf4jAware.class);

        JavacType type = JavacType.typeOf(annotationNode, source);
        if (type.isAnnotation() || type.isInterface()) {
            annotationNode.addError(canBeUsedOnClassAndEnumOnly(Perf4jAware.class));
            return;
        }

        JavacUtil.addInterface(type.node(), Perf4jAwareConstants.PERF4j_CONTRIBUTION_HANDLER_TYPE);
        handler.addPerf4jProviderField(type);
        handler.addPerf4jProviderAccessors(type);
        handler.addPerf4jContributionMethods(type);
        type.editor().rebuild();
    }

    private static class JavacPerf4jAwareHandler extends Perf4jAwareHandler<JavacType> {
    }
}
