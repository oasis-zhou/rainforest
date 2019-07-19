package rf.rating;

import com.google.common.collect.Lists;
import rf.rating.model.RatingNode;
import java.util.List;


public class RatingJob {
    private List<RatingStage> stages = Lists.newArrayList();

    public void process(RatingNode node){
        if(stages != null && stages.size() > 0){
            for(RatingStage stage : stages){
                stage.doEval(node);
            }
        }
    }

    public RatingJob addStage(RatingStage stage) {
        this.stages.add(stage);
        return this;
    }
}
