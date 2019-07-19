package rf.rating;

public class RatingConstants {

	public static final String INDEMNITY_AOA = "AOA";
	public static final String INDEMNITY_AOP = "AOP";

	//internal fix rf factor key
	public static final String FORMULA_FACTOR = "FORMULA";
	public static final String MULTI_FORMULA_FACTOR = "MULTI_FORMULA";
	public static final String DISTRIBUTION_FACTOR = "DISTRIBUTION_FACTOR";
	public static final String LIMIT_FACTOR = "LIMIT_FACTOR";
	public static final String AOA_LIMIT_AMOUNT = "AOA_LIMIT_AMOUNT";
	public static final String AOP_LIMIT_AMOUNT = "AOP_LIMIT_AMOUNT";	
	public static final String FIX_PREMIUM_FACTOR = "FIX_PREMIUM";
	public static final String FIX_COMMISSION_FACTOR = "FIX_COMMISSION";
	
	//definition rf factor key
	public static final String ENDORSEMENT_TYPE_FACTOR = "ENDORSEMENT_TYPE";
	public static final String ORIGINAL_PREMIUM = "ORIGINAL_PREMIUM";
	public static final String NEW_PREMIUM = "NEW_PREMIUM";
	public static final String ENDO_EFF_DATE = "ENDO_EFF_DATE";
	public static final String ENDO_PRO_RATE = "ENDO_PRO_RATE";

	//definition fee type
	public static final String FEE_TYPE_SGP = "SGP";
	public static final String FEE_TYPE_SNP = "SNP";
	public static final String FEE_TYPE_APP = "APP";

	public static final String LIMIT_AMOUNT = "LIMIT_AMOUNT";
	public static final String LIMIT_NUMBER = "LIMIT_NUMBER";
	public static final String LIMIT_UNIT_AMOUNT = "LIMIT_UNIT_AMOUNT";
	public static final String LIMIT_NUMBER_OF_UNIT = "LIMIT_NUMBER_OF_UNIT";
	public static final String LIMIT_UNIT_TYPE = "LIMIT_UNIT_TYPE";
	public static final String LIMIT_MAX_UNIT_AMOUNT = "LIMIT_MAX_UNIT_AMOUNT";
	public static final String LIMIT_MAX_NUMBER_OF_UNIT = "LIMIT_MAX_NUMBER_OF_UNIT";
	public static final String LIMIT_PATTERN = "LIMIT_PATTERN";
	public static final String LIMIT_INDEMNITY_TYPE = "LIMIT_INDEMNITY_TYPE";
	public static final String LIMIT_FORMULA = "LIMIT_FORMULA";


	//Amount Per Occurrence
	public static final String APO = "${limitAmount}";
	//Amount Per Unit Per Occurrence
	public static final String APUPO = "${unitAmount}/${numberOfUnit}${unitType}";
	//Amount Per Unit Per Occurrence+Max Amount
	public static final String APUPO_MA = "${unitAmount}/${numberOfUnit}${unitType}max${maxUnitAmount}";
	//Amount Per Unit Per Occurrence+Max Number
	public static final String APUPO_MN = "${unitAmount}/${numberOfUnit}${unitType}max${maxNumberOfUnit}";
	//By formula
	public static final String FORMULA = "${limitFormula}";

}
