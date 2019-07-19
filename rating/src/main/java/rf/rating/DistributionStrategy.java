package rf.rating;

import rf.rating.model.RatingNode;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;


public class DistributionStrategy implements RatingStrategy {

	private Distributor distributor;

	public DistributionStrategy(Distributor distributor){
		this.distributor = distributor;
	}
	
	@Override
	public void execute(RatingNode node) {
		
		Map<String, Object> factors = node.getFactors();
		
		Iterator i = factors.keySet().iterator();
		while (i.hasNext()) {            	  
			String valueKey = (String) i.next();
			if(!valueKey.equals(RatingConstants.DISTRIBUTION_FACTOR)){
				BigDecimal distributionValue = (BigDecimal) factors.get(valueKey);
				node.getValues().put(valueKey, distributionValue);
			}
		}
	}
	
	private void distribute(RatingNode node){
		if(node.getSubNodes().size() > 0) {
			distributor.distribute(node);
			node.getSubNodes().forEach((subNode) -> distribute(subNode));
		}
	}


}
