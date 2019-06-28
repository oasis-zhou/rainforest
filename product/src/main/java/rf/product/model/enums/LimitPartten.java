package rf.product.model.enums;


public enum LimitPartten {

    //Amount Per Occurrence
    APO("${limitAmount}"),
    //Amount Per Unit Per Occurrence
    APUPO("${unitAmount}/${numberOfUnit}${unitType}"),
    //Amount Per Unit Per Occurrence+Max Amount
    APUPO_MA("${unitAmount}/${numberOfUnit}${unitType}max${maxUnitAmount}"),
    //Amount Per Unit Per Occurrence+Max Number
    APUPO_MN("${unitAmount}/${numberOfUnit}${unitType}max${maxNumberOfUnit}"),
    //By formula
    FORMULA("${limitFormula}");

    private String value;

    private LimitPartten(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
