package rf.saleshorizon.fs;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.model.*;
import rf.product.model.CoverageSpec;
import rf.product.model.DeductibleSpec;
import rf.product.model.LimitSpec;
import rf.product.model.ProductSpec;
import rf.saleshorizon.ds.ProductService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouzheng on 2017/5/18.
 */
@Component
public class SetupPolicyForFixCoverage implements FunctionSlice<Policy> {

    @Autowired
    private ProductService productService;

    @Override
    public void execute(Policy policy, Map<String, Object> context) {

        ProductSpec product = productService.pullFromChain(policy.getProductCode());

        policy.setProductName(product.getName());
        if(policy.getBusinessOrgan() == null)
            policy.setBusinessOrgan(product.getOrganization());

//        List<LimitSpec> pLimitSpecs = product.getSubComponentsByType(LimitSpec.class);
//        if(pLimitSpecs.size() > 0){
//            for(LimitSpec limitSpec : pLimitSpecs){
//                Limit limit = convertFromSpec(limitSpec);
//                policy.getSubComponents().add(limit);
//            }
//        }

        if(product.getFixedCoverage()){

            List<CoverageSpec> coverageSpecs = product.getAllSubComponentsByType(CoverageSpec.class);

            List<Coverage> coverages = Lists.newArrayList();

            coverageSpecs.forEach(spec -> {
                Coverage coverage = new Coverage();
                coverage.setName(spec.getName());
                coverage.setCode(spec.getCode());

                //build up limit
                buildupLimit(spec, coverage);
                //build up deductible
                buildupDeductible(spec, coverage);

                coverages.add(coverage);
            });

            List<InsuredObject> insuredObjects = policy.getSubComponentsByType(InsuredObject.class);
            insuredObjects.forEach(insured ->{
                insured.removeSubComponentsByType(Coverage.class);
                insured.getSubComponents().addAll(coverages);
            });

        }

    }

    private void buildupDeductible(CoverageSpec spec, Coverage coverage) {
        List<DeductibleSpec> deductibleSpecs = spec.getSubComponentsByType(DeductibleSpec.class);
        deductibleSpecs.forEach(deductibleSpec -> {
            try {
                String defaultValue = deductibleSpec.getDefaultValue();
                if (defaultValue != null) {
                    Deductible deductible = new Deductible();
                    Class deductibleCla = (Class) deductible.getClass();
                    Field[] fs = deductibleCla.getDeclaredFields();
                    String[] values = defaultValue.split(",");
                    for (String value : values) {
                        String[] fieldValue = value.split(":");
                        for (Field f : fs) {
                            if (f.getName().equals(fieldValue[0])) {

                                f.setAccessible(true);
                                String type = f.getType().toString();//得到此属性的类型
                                if (type.endsWith("String")) {
                                    f.set(deductible, fieldValue[1]);
                                }
                                if (type.endsWith("BigDecimal")) {
                                    f.set(deductible, new BigDecimal(fieldValue[1]));
                                }

                            }
                        }
                    }
                    coverage.setDeductible(deductible);
                }

            }catch (IllegalAccessException e){
                throw new GenericException(e);
            }
        });
    }

    private void buildupLimit(CoverageSpec spec, Coverage coverage) {
        List<LimitSpec> limitSpecs = spec.getSubComponentsByType(LimitSpec.class);
        limitSpecs.forEach(limitSpec -> {
            try {
                String defaultValue = limitSpec.getDefaultValue();
                if (defaultValue != null) {
                    Limit limit = new Limit();
                    limit.setPattern(limitSpec.getPattern());
                    limit.setIndemnityType(limitSpec.getIndemnityType().name());
                    Class limitCla = (Class) limit.getClass();
                    Field[] fs = limitCla.getDeclaredFields();
                    String[] values = defaultValue.split(",");
                    for (String value : values) {
                        String[] fieldValue = value.split(":");
                        for (Field f : fs) {
                            if (f.getName().equals(fieldValue[0])) {

                                f.setAccessible(true);
                                String type = f.getType().toString();//得到此属性的类型
                                if (type.endsWith("String")) {
                                    f.set(limit, fieldValue[1]);
                                }
                                if (type.endsWith("BigDecimal")) {
                                    f.set(limit, new BigDecimal(fieldValue[1]));
                                }

                            }
                        }
                    }
                    coverage.setLimit(limit);
                }

            }catch (IllegalAccessException e){
                throw new GenericException(e);
            }
        });
    }

//    private Limit convertFromSpec(LimitSpec limitSpec){
//
//        Limit limit = new Limit();
//
//        limit.setPattern(limitSpec.getPattern());
//        limit.setIndemnityType(limitSpec.getIndemnityType().name());
//
//        try {
//        String defaultValue = limitSpec.getDefaultValue();
//        if (defaultValue != null) {
//            Class limitCla = (Class) limit.getClass();
//            Field[] fs = limitCla.getDeclaredFields();
//            String[] values = defaultValue.split(",");
//            for (String value : values) {
//                String[] fieldValue = value.split(":");
//                for (Field f : fs) {
//                    if (f.getName().equals(fieldValue[0])) {
//
//                        f.setAccessible(true);
//                        String type = f.getType().toString();//得到此属性的类型
//                        if (type.endsWith("String")) {
//                            f.set(limit, fieldValue[1]);
//                        }
//                        if (type.endsWith("BigDecimal")) {
//                            f.set(limit, new BigDecimal(fieldValue[1]));
//                        }
//
//                    }
//                }
//            }
//        }
//        }catch (IllegalAccessException e){
//            throw new GenericException(e);
//        }
//        return limit;
//    }
}
