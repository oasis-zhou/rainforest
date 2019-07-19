package rf.rating.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import rf.foundation.model.BaseModel;
import rf.rating.Calculator;

import java.util.List;
import java.util.Map;


public class RatingNode {
    private Map<String,Object> factors = Maps.newHashMap();
    private Map<String,Object> values = Maps.newHashMap();
    private List<Expression> expressions = Lists.newArrayList();
    private List<RatingNode> subNodes = Lists.newArrayList();
    private Calculator currentCalculator;
    private String currentPurpose;
    private Map<String,Object> currentValues = Maps.newHashMap();
    private BaseModel refBizObject;

    public List<RatingNode> getAllSubNodes() {
        List<RatingNode> subComponents = Lists.newArrayList();
        for (RatingNode sub : this.subNodes) {
                subComponents.add(sub);
            subComponents.addAll(sub.getAllSubNodes());
        }

        return subComponents;
    }

    public Map<String, Object> getFactors() {
        return factors;
    }

    public void setFactors(Map<String, Object> factors) {
        this.factors = factors;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<RatingNode> getSubNodes() {
        return subNodes;
    }

    public void setSubNodes(List<RatingNode> subNodes) {
        this.subNodes = subNodes;
    }

    public Calculator getCurrentCalculator() {
        return currentCalculator;
    }

    public void setCurrentCalculator(Calculator currentCalculator) {
        this.currentCalculator = currentCalculator;
    }

    public String getCurrentPurpose() {
        return currentPurpose;
    }

    public void setCurrentPurpose(String currentPurpose) {
        this.currentPurpose = currentPurpose;
    }

    public Map<String, Object> getCurrentValues() {
        return currentValues;
    }

    public void setCurrentValues(Map<String, Object> currentValues) {
        this.currentValues = currentValues;
    }

    public BaseModel getRefBizObject() {
        return refBizObject;
    }

    public void setRefBizObject(BaseModel refBizObject) {
        this.refBizObject = refBizObject;
    }
}
