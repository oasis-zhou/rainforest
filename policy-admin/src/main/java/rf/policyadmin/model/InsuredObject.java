package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;


@Data
public class InsuredObject extends ModelComponent {

    private String insuredObjectName;
    private String insuredObjectCategory;

}
