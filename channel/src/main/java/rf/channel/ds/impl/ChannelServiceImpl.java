package rf.channel.ds.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rf.channel.ds.ChannelService;
import rf.channel.model.ChannelSpec;
import rf.channel.repository.ChannelDao;
import rf.channel.repository.pojo.TChannel;
import rf.foundation.utils.JsonHelper;

/**
 * Created by admin on 2017/8/22.
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private JsonHelper jsonHelper;

    @Override
    public void saveChannel(ChannelSpec channelSpec) {
        TChannel tchannel = channelDao.findById(channelSpec.getUuid()).orElse(new TChannel());

        BeanUtils.copyProperties(channelSpec, tchannel);
        String json = jsonHelper.toJSON(channelSpec);
        tchannel.setContent(json);
        tchannel.setType(channelSpec.getType().name());
        channelDao.save(tchannel);
    }

    @Override
    @Cacheable(value="channel" , key = "#accessKey")
    public ChannelSpec findByAccessKey(String accessKey) {
        TChannel byAccessKey = channelDao.findByAccessKey(accessKey);
        ChannelSpec channelSpec = jsonHelper.fromJSON(byAccessKey.getContent(), ChannelSpec.class);
        return channelSpec;
    }
}
