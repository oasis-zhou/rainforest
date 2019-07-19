package rf.rating;

import rf.rating.model.RatingNode;

public interface Accumulator {

	public void accumulate(RatingNode node);
}
