package rf.eval;

import com.google.common.collect.Lists;
import rf.eval.model.EvalNode;
import java.util.List;


public class EvalJob {
    private List<EvalStage> stages = Lists.newArrayList();

    public void process(EvalNode node){
        if(stages != null && stages.size() > 0){
            for(EvalStage stage : stages){
                stage.doEval(node);
            }
        }
    }

    public EvalJob addStage(EvalStage stage) {
        this.stages.add(stage);
        return this;
    }
}
