package rf.eval;

import rf.eval.model.EvalNode;
import java.util.List;
import java.util.Map;


public class MergeStrategy implements EvalStrategy {

    public void execute(EvalNode node){
        mergeAll(node);
    }

    private void mergeAll(EvalNode node){
        Evaluator evaluator = node.getCurrentEvaluator();
        if(evaluator != null)
            evaluator.eval(node);
        if(node.getSubNodes().size() > 0) {
            node.getSubNodes().forEach(subNode -> mergeAll(subNode));
            merge(node);
        }

    }

    private void merge(EvalNode node){
        Map<String, Object> pValues = node.getValues();

        List<EvalNode> subNodes = node.getSubNodes();

        for(EvalNode subNode : subNodes){
            Map<String, Object> cValues = subNode.getValues();
            pValues.putAll(cValues);
        }
    }

}
