package rf.eval;

import rf.eval.model.EvalNode;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;


public class DistributionStrategy implements EvalStrategy {

	private Distributor distributor;

	public DistributionStrategy(Distributor distributor){
		this.distributor = distributor;
	}
	
	@Override
	public void execute(EvalNode node) {
		
		Map<String, Object> factors = node.getFactors();
		
		Iterator i = factors.keySet().iterator();
		while (i.hasNext()) {            	  
			String valueKey = (String) i.next();
			if(!valueKey.equals(EvalConstant.DISTRIBUTION_FACTOR)){
				BigDecimal distributionValue = (BigDecimal) factors.get(valueKey);
				node.getValues().put(valueKey, distributionValue);
			}
		}
	}
	
	private void distribute(EvalNode node){
		if(node.getSubNodes().size() > 0) {
			distributor.distribute(node);
			node.getSubNodes().forEach((subNode) -> distribute(subNode));
		}
	}


}
