package rf.rating;

import rf.rating.model.RatingNode;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LimitAccumulator implements Accumulator {

	@Override
	public void accumulate(RatingNode node) {
		Map<String, Object> parentValue = node.getCurrentValues();

		List<RatingNode> subNodes = node.getSubNodes();

		for(RatingNode subModel : subNodes){

			Map<String, Object> childValue = subModel.getCurrentValues();

			Iterator itr = childValue.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				BigDecimal cAmount = (BigDecimal) childValue.get(key);
				if (cAmount != null) {
					
					BigDecimal sumAmount = (BigDecimal)parentValue.get(key);
					
					if(sumAmount != null){
						sumAmount = sumAmount.add(cAmount);
						parentValue.put(key, sumAmount);
					}else{
						parentValue.put(key, cAmount);
					}
					
				}
			}
		}

		node.getFactors().putAll(parentValue);
		node.getValues().putAll(parentValue);
	}

}
