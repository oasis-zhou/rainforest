package rf.foundation.numbering;

public class NumberingRuleItem {
	private String template;
	private NumberingFactor factor;
	private Integer length = 0;
	private String padding = "0";
	private String fixValue;
	
	public NumberingRuleItem(String template){
		this.setTemplate(template);
	}

	public String getTemplate() {
		return template;
	}

	//4{PRODUCT_CODE}
	public void setTemplate(String template) {
		int left = template.indexOf("{");
		int right = template.indexOf("}");
		if(left<0 || right<0){
			return;
		}
		String code = template.substring(left+1,right);

		boolean codeExist = false;
		for(NumberingFactor factor : NumberingFactor.values()){
			if(factor.name().equals(code)){
				codeExist = true;
			}
		}

		if(!codeExist){
			this.fixValue = code;
			this.template = "{" + code + "}";
		}else {
			this.setFactor(NumberingFactor.valueOf(code));
			if (left > 0) {
				String value = "";
				String temp = template.substring(0, left);
				for (int i = 0; i < temp.length(); i++) {
					char ch = temp.charAt(i);
					if (Character.isDigit(ch)) {
						value = value + ch;
					}
				}
				if (value.length() > 0) {
					this.setLength(Integer.valueOf(value));
				}
				if (!value.equals(temp)) {
					template = value + "{" + code + "}";
				}
			}

			this.template = template;
		}
	}

	public NumberingFactor getFactor() {
		return factor;
	}

	public void setFactor(NumberingFactor factor) {
		this.factor = factor;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
	
	public String getPadding() {
		return padding;
	}

	public void setPadding(String padding) {
		this.padding = padding;
	}

	public String getFixValue() {
		return fixValue;
	}

	public void setFixValue(String fixValue) {
		this.fixValue = fixValue;
	}

	public String generatePadding(int len){
		StringBuffer str = new StringBuffer("");
		for(int i = 0 ; i < len ;i++){
			str.append(this.getPadding());
		}
		return str.toString();
	}

	public String generateNumbering(String value){
		String numbering = value;
		if(this.getLength() > 0){
			if(value.length() > this.getLength()){
				numbering = value.substring(value.length() - this.getLength());
			}else{
				numbering = this.generatePadding(this.getLength()-value.length()) + value;
			}
			
		}
		return numbering;
	}

}
