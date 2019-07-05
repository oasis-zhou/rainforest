package rf.channel.ds;

import rf.channel.model.ChannelSpec;
import rf.channel.model.QueryCondition;
import rf.channel.model.SalesAgreementSpec;
import rf.foundation.model.ResponsePage;

/**
 * Created by admin on 2017/8/22.
 */
public interface ChannelService {
    void saveChannel(ChannelSpec channelSpec);
    ChannelSpec loadChannel(String code);
    ChannelSpec findByAccessKey(String accessKey);

    void saveSalesAgreement(SalesAgreementSpec agreementSpec);

    ResponsePage<ChannelSpec> findChannels(QueryCondition condition);
}
