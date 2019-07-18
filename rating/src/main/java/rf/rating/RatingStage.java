package rf.eval;

import rf.eval.model.EvalNode;
import java.util.List;

public class EvalStage {

    private EvalStrategy strategy;
    private Evaluator evaluator;
    private String purpose;

    public EvalStage(EvalStrategy strategy,Evaluator evaluator,String purpose){
        this.strategy = strategy;
        this.evaluator = evaluator;
        this.purpose = purpose;
    }

    public void doEval(EvalNode node){
        setPurposeAndEvaluator(node,purpose,evaluator);
        strategy.execute(node);
    }

    private void setPurposeAndEvaluator(EvalNode node, String prupose,Evaluator evaluator){
        node.setCurrentPurpose(purpose);
        node.setCurrentEvaluator(evaluator);

        List<EvalNode> subNodes = node.getAllSubNodes();
        subNodes.forEach(subNode -> {
            subNode.setCurrentPurpose(purpose);
            subNode.setCurrentEvaluator(evaluator);
        });
    }

}
