package rf.eval;

import rf.eval.model.EvalNode;

public interface EvalStrategy {

	public void execute(EvalNode node);
}
