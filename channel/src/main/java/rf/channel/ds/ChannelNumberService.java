package rf.channel.ds;

import rf.channel.model.ChannelSpec;

public interface ChannelNumberService {

    String generateChannelCode(ChannelSpec channelSpec);
}
