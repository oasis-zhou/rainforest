package rf.foundation.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppContext implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static <T> T getBean(String beanName, Class<T> clazz) {
		return applicationContext.getBean(beanName, clazz);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
}