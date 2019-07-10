package rf.cohorizon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import rf.cohorizon.ds.IdentityService;

/**
 * @ClassName StartupRunner
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/10
 * @Version V1.0
 **/
public class StartupRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${crypto.public.key}")
    private String pubKey;
    @Value("${guid.org.code}")
    private String orgCode;
    @Autowired
    private IdentityService identityService;

    @Override
    public void run(String... args) throws Exception {

        logger.info("<<<<<<<<<<<<<<<<<StarupRunner>>>>>>>>>>>>>>>>");
        //TODO identity registration
        identityService.register(orgCode,pubKey);

        logger.info("<<<<<<<<<<<<<<<<<PubKey Registration Success!>>>>>>>>>>>>>>>>");
    }
}
