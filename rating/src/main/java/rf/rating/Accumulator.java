package rf.eval;

import rf.eval.model.EvalNode;

public interface Accumulator {

	public void accumulate(EvalNode node);
}
