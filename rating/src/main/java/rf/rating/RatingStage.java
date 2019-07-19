package rf.rating;

import rf.rating.model.RatingNode;
import java.util.List;

public class RatingStage {

    private RatingStrategy strategy;
    private Calculator calculator;
    private String purpose;

    public RatingStage(RatingStrategy strategy, Calculator calculator, String purpose){
        this.strategy = strategy;
        this.calculator = calculator;
        this.purpose = purpose;
    }

    public void doEval(RatingNode node){
        setPurposeAndEvaluator(node,purpose, calculator);
        strategy.execute(node);
    }

    private void setPurposeAndEvaluator(RatingNode node, String prupose, Calculator calculator){
        node.setCurrentPurpose(purpose);
        node.setCurrentCalculator(calculator);

        List<RatingNode> subNodes = node.getAllSubNodes();
        subNodes.forEach(subNode -> {
            subNode.setCurrentPurpose(purpose);
            subNode.setCurrentCalculator(calculator);
        });
    }

}
