package rf.product.model;

import com.google.common.collect.Lists;
import rf.foundation.model.ModelComponent;
import java.util.List;


public class POISpec extends ModelComponent {
    private String effDateFormat;
    private String expDateFormat;
    private String defaultPeriod;
    private String maxPeriod;
    private List<String> periodOptions = Lists.newArrayList();

    public String getEffDateFormat() {
        return effDateFormat;
    }

    public void setEffDateFormat(String effDateFormat) {
        this.effDateFormat = effDateFormat;
    }

    public String getExpDateFormat() {
        return expDateFormat;
    }

    public void setExpDateFormat(String expDateFormat) {
        this.expDateFormat = expDateFormat;
    }

    public String getDefaultPeriod() {
        return defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public String getMaxPeriod() {
        return maxPeriod;
    }

    public void setMaxPeriod(String maxPeriod) {
        this.maxPeriod = maxPeriod;
    }

    public List<String> getPeriodOptions() {
        return periodOptions;
    }

    public void setPeriodOptions(List<String> periodOptions) {
        this.periodOptions = periodOptions;
    }
}
