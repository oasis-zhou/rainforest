package rf.bizop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.bizop.pub.Constants;
import rf.channel.ds.ChannelService;
import rf.channel.model.ChannelSpec;
import rf.channel.model.QueryCondition;
import rf.channel.model.SalesAgreementSpec;
import rf.foundation.model.ResponsePage;

/**
 * @ClassName ChannelController
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@RestController
@RequestMapping("v0/api/channel")
public class ChannelController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${operation.mode}")
    private String operationMode;
    @Autowired
    private ChannelService channelService;

    @PostMapping("/registration")
    public ResponseEntity createChannel(@RequestBody ChannelSpec channelSpec){
        channelService.saveChannel(channelSpec);

        return new ResponseEntity(channelSpec.getCode(),HttpStatus.OK);
    }

    @GetMapping(value = "/load/{channelCode}")
    public ResponseEntity getChannel(@PathVariable("channelCode") String channelCode){

        ChannelSpec channelSpec = channelService.loadChannel(channelCode);

        return new ResponseEntity(channelSpec,HttpStatus.OK);
    }

    @PostMapping("/sales/agreement")
    public ResponseEntity buildSalesAgreement(@RequestBody SalesAgreementSpec agreementSpec){
        channelService.saveSalesAgreement(agreementSpec);

        if(Constants.OPERATION_MODE_DECENTRALIZED.equals(operationMode)) {
            //TODO 同步信息到区块链
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/query")
    public ResponseEntity query(@RequestBody QueryCondition condition) {

        ResponsePage<ChannelSpec> channels = channelService.findChannels(condition);

        return new ResponseEntity(channels,HttpStatus.OK);
    }

}
