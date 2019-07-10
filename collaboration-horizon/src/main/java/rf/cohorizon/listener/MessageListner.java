package rf.cohorizon.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @ClassName MessageListner
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/10
 * @Version V1.0
 **/
@Component
public class MessageListner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron="0 */1 * * * ?")
    public void listenMsg() {
        logger.info("Start to get message from chain!");
        //TODO 获取消息，解析消息，调用业务方法处理业务逻辑，归档消息
    }
}
