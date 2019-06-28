package rf.eval.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import rf.eval.Evaluator;
import rf.foundation.model.BaseModel;

import java.util.List;
import java.util.Map;


public class EvalNode {
    private Map<String,Object> factors = Maps.newHashMap();
    private Map<String,Object> values = Maps.newHashMap();
    private List<Expression> expressions = Lists.newArrayList();
    private List<EvalNode> subNodes = Lists.newArrayList();
    private Evaluator currentEvaluator;
    private String currentPurpose;
    private Map<String,Object> currentValues = Maps.newHashMap();
    private BaseModel refBizObject;

    public List<EvalNode> getAllSubNodes() {
        List<EvalNode> subComponents = Lists.newArrayList();
        for (EvalNode sub : this.subNodes) {
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

    public List<EvalNode> getSubNodes() {
        return subNodes;
    }

    public void setSubNodes(List<EvalNode> subNodes) {
        this.subNodes = subNodes;
    }

    public Evaluator getCurrentEvaluator() {
        return currentEvaluator;
    }

    public void setCurrentEvaluator(Evaluator currentEvaluator) {
        this.currentEvaluator = currentEvaluator;
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
