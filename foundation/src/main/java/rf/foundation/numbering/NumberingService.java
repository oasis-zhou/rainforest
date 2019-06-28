package rf.foundation.numbering;


import java.util.Map;

public interface NumberingService {
	/**
	 * Generate number by factors
	 * @param rule
	 * @param factors
	 * @return
	 */
	public String generateNumber(NumberingType rule, Map<NumberingFactor, String> factors);
	
}
