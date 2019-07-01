package rf.claim.model.enums;

/**
 * Created by hecy on 2017/12/27.
 * 理赔案件审批 状态
 */
public enum NoticeStatus {

    TOAPPLY("01", "待申请"),
    TOEXAMINE("02", "待审核"),
    TOCLAIM("03", "待赔付"),
    SETTLE_A_LAWSUIT("04", "已结案"), //已结案 包含 已赔付 和 拒赔付
    FAILEXAMINE("05", "审核失败"),
    CLOSE("06", "已取消");

    // 构造方法
    NoticeStatus(String des, String value) {
        this.des = des;
        this.value = value;
    }

    // 成员变量
    private String des;

    private String value;

    public String getValue() {
        return value;
    }

    public String getDes() {
        return des;
    }


}
