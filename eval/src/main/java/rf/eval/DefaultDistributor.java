package rf.eval;

import rf.eval.model.EvalNode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DefaultDistributor implements Distributor {

	@Override
	public void distribute(EvalNode node){

		Map<String, Object> factors = node.getFactors();
		
		List<EvalNode> subNodes = node.getSubNodes();

		int calCount = 0;
		int subSize = subNodes.size();
		BigDecimal percentage = BigDecimal.ZERO;
		percentage = BigDecimal.ONE.divide(new BigDecimal(subSize),50, BigDecimal.ROUND_HALF_UP);
		
		Map sumSubValueM = new HashMap();
		for (EvalNode subNode : subNodes) {
			Map<String, Object> subFactors = subNode.getFactors();

			if (calCount != subSize - 1) {
				
				BigDecimal factorValue = (BigDecimal) factors.get(EvalConstant.DISTRIBUTION_FACTOR);
				BigDecimal subFactorValue = (BigDecimal) subFactors.get(EvalConstant.DISTRIBUTION_FACTOR);
				
				if (factorValue != null && factorValue.compareTo(BigDecimal.ZERO) != 0 
						&& subFactorValue != null && subFactorValue.compareTo(BigDecimal.ZERO) != 0) {				
					percentage = subFactorValue.divide(factorValue,50, BigDecimal.ROUND_HALF_UP);
				}

				Iterator itr = factors.keySet().iterator();
				while (itr.hasNext()) {            	  
					String valueKey = (String) itr.next();
					if(!valueKey.equals(EvalConstant.DISTRIBUTION_FACTOR)){
						BigDecimal distributionValue = (BigDecimal) factors.get(valueKey);

						BigDecimal subValue = distributionValue.multiply(percentage);
						subValue = RoundingUtils.round(subValue,null);
						subFactors.put(valueKey, subValue);
						subNode.getValues().put(valueKey, subValue);

						BigDecimal sumSubValue = (BigDecimal) sumSubValueM.get(valueKey);
						if (sumSubValue != null) {
							sumSubValue = sumSubValue.add(subValue);
						} else {
							sumSubValue = subValue;
						}
						sumSubValueM.put(valueKey, sumSubValue);
					}
				}

			} else {
				Iterator itr = factors.keySet().iterator();
				while (itr.hasNext()) {
					String valueKey = (String) itr.next();
					if(!valueKey.equals(EvalConstant.DISTRIBUTION_FACTOR)){
						BigDecimal distributionValue = (BigDecimal) factors.get(valueKey);
						BigDecimal sumSubValue = (BigDecimal) sumSubValueM.get(valueKey);
						if(sumSubValue == null)
							sumSubValue = BigDecimal.ZERO;
						BigDecimal subValue = distributionValue.subtract(sumSubValue);					
						subFactors.put(valueKey, subValue);
						subNode.getValues().put(valueKey, subValue);
					}
				} 

				calCount++;
			}
		}
	}

}
