package rf.channel.ds;

import rf.channel.model.ChannelSpec;

/**
 * Created by admin on 2017/8/22.
 */
public interface ChannelService {

    void saveChannel(ChannelSpec channelSpec);
    ChannelSpec findByAccessKey(String accessKey);

}
