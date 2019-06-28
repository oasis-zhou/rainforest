package rf.policyadmin.model.enums;

/**
 * Created by zhouzheng on 2017/5/5.
 */
public enum TerminalReason {
    TERMINAL_BY_CLAIM,
    TERMINAL_BY_CANCELLATION,
    TERMINAL_BY_EXPIRED,  //保单已失效，但是失效原因为到期失效
    TERMINAL_BY_CLIENT,
    TERMINAL_BY_UNDERWRITING,
    TERMINAL_BY_PAYMENT_FAILED
}
