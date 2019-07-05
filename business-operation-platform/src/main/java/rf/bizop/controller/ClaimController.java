package rf.bizop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.bizop.pub.Constants;
import rf.claim.ds.ClaimService;
import rf.claim.ds.NoticeOfLossService;
import rf.claim.model.Claim;
import rf.claim.model.NoticeOfLoss;
import rf.claim.model.QueryCondition;
import rf.claim.model.enums.NoticeStatus;
import rf.foundation.model.ResponsePage;
import rf.foundation.utils.JsonHelper;
import javax.websocket.server.PathParam;

/**
 * @ClassName ClaimController
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@RestController
@RequestMapping("v0/api/claim")
public class ClaimController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${operation.mode}")
    private String operationMode;
    @Autowired
    private NoticeOfLossService noticeOfLossService;
    @Autowired
    private ClaimService claimService;
    @Autowired
    private JsonHelper jsonHelper;

    @PostMapping("/registration")
    public ResponseEntity createClaim(@RequestBody NoticeOfLoss noticeOfLoss){

        noticeOfLoss.setNoticeStatus(NoticeStatus.CLAIMED);
        noticeOfLossService.saveNotice(noticeOfLoss);

        //TODO 报案数据进入核心系统

        if(Constants.OPERATION_MODE_DECENTRALIZED.equals(operationMode)) {
            //TODO 同步信息到区块链
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/loss/notice/reject")
    public ResponseEntity rejectNoticeOfLoss(@RequestBody NoticeOfLoss noticeOfLoss){
        noticeOfLoss.setNoticeStatus(NoticeStatus.REJECT);
        noticeOfLossService.saveNotice(noticeOfLoss);

        if(Constants.OPERATION_MODE_DECENTRALIZED.equals(operationMode)) {
            //TODO 同步信息到区块链
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/loss/notice/{noticeNumber}")
    public ResponseEntity getNoticeOfLoss(@PathParam("noticeNumber") String noticeNumber){
        NoticeOfLoss noticeOfLoss = noticeOfLossService.loadNoticeOfLoss(noticeNumber);
        return new ResponseEntity(noticeOfLoss,HttpStatus.OK);
    }

    @PostMapping(value = "/loss/notice/query")
    public ResponseEntity queryNoticeOfLoss(@RequestBody QueryCondition condition) {

        logger.debug("noticeOfLoss query condition:" + jsonHelper.toJSON(condition));
        ResponsePage<NoticeOfLoss> notices = noticeOfLossService.queryNoticeOfLoss(condition);

        return new ResponseEntity(notices, HttpStatus.OK);
    }

    @PostMapping(value = "/query")
    public ResponseEntity queryClaim(@RequestBody QueryCondition condition) {

        logger.debug("claim query condition:" + jsonHelper.toJSON(condition));
        ResponsePage<Claim> claims = claimService.queryClaim(condition);

        return new ResponseEntity(claims, HttpStatus.OK);
    }
}
