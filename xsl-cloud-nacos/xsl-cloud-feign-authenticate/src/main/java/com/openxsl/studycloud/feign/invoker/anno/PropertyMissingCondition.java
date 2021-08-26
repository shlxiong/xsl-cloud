package com.openxsl.studycloud.feign.invoker.anno;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.MethodMetadata;

public class PropertyMissingCondition implements Condition{
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String classOrMethodName = this.getClassOrMethodName(metadata);
		try {
			String annotationClass = ConditionalOnMissingProperty.class.getName();
			Map<String,?> attributeMap = metadata.getAnnotationAttributes(annotationClass);
			String[] properties = (String[])attributeMap.get("names");
			for (String property : properties) {
				if (context.getEnvironment().getProperty(property) != null) {
					logger.info("ignore {} on-property-absent [{}]",
								classOrMethodName, property);
					return false;
				}
			}
			
			return true;
		} catch (NoClassDefFoundError ex) {
			throw new IllegalStateException(
					"Could not evaluate condition on " + classOrMethodName + " due to "
							+ ex.getMessage() + " not found. ",
					ex);
		}catch (RuntimeException ex) {
			throw new IllegalStateException(
					"Error processing condition on " + classOrMethodName, ex);
		}
	}
	
	private String getClassOrMethodName(AnnotatedTypeMetadata metadata) {
		if (metadata instanceof ClassMetadata) {
			ClassMetadata classMetadata = (ClassMetadata) metadata;
			return classMetadata.getClassName();
		}
		MethodMetadata methodMetadata = (MethodMetadata) metadata;
		return methodMetadata.getDeclaringClassName() + "#"
					+ methodMetadata.getMethodName();
	}

}
