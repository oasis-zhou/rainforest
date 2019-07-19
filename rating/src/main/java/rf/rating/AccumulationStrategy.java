package rf.rating;


import rf.rating.model.RatingNode;

public class AccumulationStrategy implements RatingStrategy {

	private Accumulator accumulator;

	public AccumulationStrategy(Accumulator accumulator){
		this.accumulator = accumulator;
	}

	public void execute(RatingNode node){

		accumulate(node);
	}

	private void accumulate(RatingNode node){
		Calculator calculator = node.getCurrentCalculator();
		if(calculator != null)
			calculator.eval(node);

		if(node.getSubNodes().size() > 0){
			node.getSubNodes().forEach((subNode) -> accumulate(subNode));
			accumulator.accumulate(node);
		}
	}

}
