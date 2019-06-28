package rf.eval;


import rf.eval.model.EvalNode;

public class AccumulationStrategy implements EvalStrategy {

	private Accumulator accumulator;

	public AccumulationStrategy(Accumulator accumulator){
		this.accumulator = accumulator;
	}

	public void execute(EvalNode node){

		accumulate(node);
	}

	private void accumulate(EvalNode node){
		Evaluator evaluator = node.getCurrentEvaluator();
		if(evaluator != null)
			evaluator.eval(node);

		if(node.getSubNodes().size() > 0){
			node.getSubNodes().forEach((subNode) -> accumulate(subNode));
			accumulator.accumulate(node);
		}
	}

}
