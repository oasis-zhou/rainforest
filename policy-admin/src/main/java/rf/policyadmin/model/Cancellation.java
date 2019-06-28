package rf.policyadmin.model;

import rf.policyadmin.model.enums.CancellationType;

public class Cancellation extends Endorsement {

    private CancellationType cancellationType;
    private String reason;

    public CancellationType getCancellationType() {
        return cancellationType;
    }

    public void setCancellationType(CancellationType cancellationType) {
        this.cancellationType = cancellationType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
