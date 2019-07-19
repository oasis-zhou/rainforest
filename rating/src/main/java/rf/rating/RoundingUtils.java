package rf.rating;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RoundingUtils {

	public static BigDecimal round(BigDecimal value, String currency){

		if(currency == null){
			return value.setScale(2,BigDecimal.ROUND_HALF_UP);
		}else{
			//TODO get rounding configuration to round value
			return value.setScale(2,BigDecimal.ROUND_HALF_UP);
		}

	}

	public static Map round(Map values,String currency){
		Map result = new HashMap();

		Iterator itr = values.keySet().iterator();
		while(itr.hasNext()){
			Object key = itr.next();

			Object value = values.get(key);
			if(value instanceof BigDecimal){
				BigDecimal roundValue = round((BigDecimal)value,currency);
				result.put(key, roundValue);
			}
		}
		return result;
	}
}
