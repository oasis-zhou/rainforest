package rf.inshorizon.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @ClassName MultipartFileConfig
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/3
 * @Version V1.0
 **/
@Configuration
public class MultipartFileConfig {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }
}
