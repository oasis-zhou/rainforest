package rf.salesplatform.fs;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.exception.GenericException;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.FunctionSlice;
import rf.foundation.utils.ObjFieldUtil;
import rf.policyadmin.model.Policy;
import rf.product.ds.FactorsValidation;

import java.util.List;
import java.util.Map;


@Component
public class DataValidation implements FunctionSlice<Policy> {

    @Autowired
    private FactorsValidation validation;

    @Override
    public void execute(Policy policy, Map<String, Object> context){

        Map<String,String> msg = Maps.newHashMap();

        msg.putAll(validation.verify(ObjFieldUtil.getFieldValues(policy)));
        msg.putAll(validation.verify(policy.getDynamicFields()));

        List<ModelComponent> subModelComponents = policy.getSubComponents();
        subModelComponents.forEach(component -> {
            msg.putAll(validation.verify(ObjFieldUtil.getFieldValues(component)));
            msg.putAll(validation.verify(component.getDynamicFields()));
        });

        if(msg.size() > 0)
            throw new GenericException(msg.toString());

    }
}
