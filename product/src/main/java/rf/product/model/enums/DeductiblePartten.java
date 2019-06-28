package rf.product.model.enums;

/**
 * Created by zhouzheng on 2017/6/23.
 */
public enum DeductiblePartten {

    //deductible amount
    DEDUCTIBLE_ADMOUNT("${deductibleAmount}"),
    DEDUCTIBLE_RATE("${deductibleRate}");

    private String value;

    private DeductiblePartten(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
