package com.jiradar.jiradarback.infrastructure.common.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ModuleDisablerPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

	private Environment environment;

	private static final String INFRA_BASE_PACKAGE = "com.jiradar.jiradarback.infrastructure.";
	private static final String COMMON_PACKAGE_MODULE = "common";

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		String[] beanNames = registry.getBeanDefinitionNames();
		Set<String> beansToRemove = new HashSet<>();

		for (String beanName : beanNames) {
			if (shouldRemoveBean(registry.getBeanDefinition(beanName))) {
				beansToRemove.add(beanName);
			}
		}

		for (String beanName : beanNames) {
			String factoryBeanName = registry.getBeanDefinition(beanName).getFactoryBeanName();
			if (factoryBeanName != null && beansToRemove.contains(factoryBeanName)) {
				beansToRemove.add(beanName);
			}
		}

		beansToRemove.forEach(registry::removeBeanDefinition);
	}

	private boolean shouldRemoveBean(BeanDefinition beanDef) {
		String beanClassName = beanDef.getBeanClassName();
		if (beanClassName == null || !beanClassName.startsWith(INFRA_BASE_PACKAGE)) {
			return false;
		}

		String remainingPath = beanClassName.substring(INFRA_BASE_PACKAGE.length());
		int firstDotIndex = remainingPath.indexOf('.');
		if (firstDotIndex == -1) {
			return false;
		}

		String moduleName = remainingPath.substring(0, firstDotIndex);
		if (COMMON_PACKAGE_MODULE.equals(moduleName)) {
			return false;
		}

		String propertyKey = "issue-tracker." + moduleName + ".config.enabled";
		boolean isModuleEnabled = environment.getProperty(propertyKey, Boolean.class, true);

		return !isModuleEnabled;
	}
}