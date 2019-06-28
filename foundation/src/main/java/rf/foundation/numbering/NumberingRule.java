package rf.foundation.numbering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NumberingRule {
	private String template;
	private List<NumberingRuleItem> items = new ArrayList<NumberingRuleItem>();
	
	public NumberingRule(String template){
		this.setTemplate(template);
	}

	public String getTemplate() {
		return template;
	}

	//{FIEXED_PREFIX}4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}10{SEQUENCE}
	public void setTemplate(String template) {
		for(String str:template.split("}")){
			NumberingRuleItem item = new NumberingRuleItem(str+"}");
			this.items.add(item);
		}
		this.template = template;
	}

	public List<NumberingRuleItem> getItems() {
		return items;
	}

	public void setItems(List<NumberingRuleItem> items) {
		this.items = items;
	}
	
	public String generateNumbering(Map<NumberingFactor,String> factors){
		StringBuffer numbering = new StringBuffer(this.getTemplate());
		for(NumberingRuleItem item : this.getItems()){
			String value = factors.get(item.getFactor());
			if(item.getFixValue() != null)
				value = item.getFixValue();
			if(value!=null){
				String str = item.generateNumbering(value);
				int index=numbering.indexOf(item.getTemplate());
				numbering.replace(index, index+item.getTemplate().length(), str);
			}
		}
		return numbering.toString();
	}
	
	public NumberingRuleItem findRuleItem(NumberingFactor factor){
		NumberingRuleItem result=null;
		for(NumberingRuleItem item:this.getItems()){
			if(factor.equals(item.getFactor())){
				result=item;
				break;
			}
		}
		return result;
	}

}
