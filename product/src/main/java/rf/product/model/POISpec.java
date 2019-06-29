package rf.product.model;

import com.google.common.collect.Lists;
import lombok.Data;
import rf.foundation.model.ModelComponent;
import java.util.List;


@Data
public class POISpec extends ModelComponent {
    private String effDateFormat;
    private String expDateFormat;
    private String defaultPeriod;
    private String maxPeriod;
    private List<String> periodOptions = Lists.newArrayList();

}
