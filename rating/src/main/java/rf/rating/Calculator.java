package rf.eval;

import rf.eval.model.EvalNode;
import java.util.Map;


public interface Evaluator {

    public Map<String,Object> eval(EvalNode node);
}
