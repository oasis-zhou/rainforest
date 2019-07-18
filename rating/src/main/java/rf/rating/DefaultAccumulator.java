package rf.eval;

import rf.eval.model.EvalNode;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DefaultAccumulator implements Accumulator {

	@Override
	public void accumulate(EvalNode node) {
		Map<String, Object> pValues = node.getCurrentValues();

		List<EvalNode> subNodes = node.getSubNodes();

		for(EvalNode subNode : subNodes){

			Map<String, Object> cValues = subNode.getCurrentValues();

			Iterator itr = cValues.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				BigDecimal pAmount = (BigDecimal) pValues.get(key);
				BigDecimal cAmount = (BigDecimal) cValues.get(key);
				if (pAmount != null) {
					pAmount = pAmount.add(cAmount);
					pValues.put(key, pAmount);
				} else {
					pValues.put(key, cAmount);
				}
			}
		}

		node.getFactors().putAll(pValues);
		node.getValues().putAll(pValues);
	}

}
