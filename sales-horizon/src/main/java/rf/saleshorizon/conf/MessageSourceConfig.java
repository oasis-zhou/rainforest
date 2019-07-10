package rf.saleshorizon.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import rf.foundation.local.LocalResourceBundleMessageSource;


/**
 * Created by zhouzheng on 2017/9/12.
 */
@Configuration
public class MessageSourceConfig {
    @Value(value = "${spring.messages.basename}")
    private String basename;

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource() {
        LocalResourceBundleMessageSource messageSource = new LocalResourceBundleMessageSource();
        messageSource.setBasename(basename);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
