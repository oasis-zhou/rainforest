package rf.rating;

import rf.rating.model.RatingNode;
import java.util.List;
import java.util.Map;


public class MergeStrategy implements RatingStrategy {

    public void execute(RatingNode node){
        mergeAll(node);
    }

    private void mergeAll(RatingNode node){
        Calculator calculator = node.getCurrentCalculator();
        if(calculator != null)
            calculator.eval(node);
        if(node.getSubNodes().size() > 0) {
            node.getSubNodes().forEach(subNode -> mergeAll(subNode));
            merge(node);
        }

    }

    private void merge(RatingNode node){
        Map<String, Object> pValues = node.getValues();

        List<RatingNode> subNodes = node.getSubNodes();

        for(RatingNode subNode : subNodes){
            Map<String, Object> cValues = subNode.getValues();
            pValues.putAll(cValues);
        }
    }

}
