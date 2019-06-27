package rf.foundation.exception;


import rf.foundation.context.AppContext;
import rf.foundation.local.LocalResourceBundleMessageSource;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 4214471812942123410L;
	private Long errCode;
	private String dynamicStr;
	private LocalResourceBundleMessageSource msgSource;

	public GenericException(Throwable cause){
		super(cause);
	}

	public GenericException(Long errCode){
		this.errCode = errCode;
		this.msgSource = AppContext.getBean("messageSource", LocalResourceBundleMessageSource.class);
	}
	
	public GenericException(Long errCode, String dynamicStr){
		this.errCode = errCode;
		this.dynamicStr = dynamicStr;
		this.msgSource = AppContext.getBean("messageSource", LocalResourceBundleMessageSource.class);
	}

	public GenericException(Long errCode, Throwable cause) {
		super(cause);
		this.errCode = errCode;
	}

	public GenericException(String msg){
		super(msg);
	}

	public Long getErrorCode() {
		return errCode;
	}

	public String getMessage(){
		if(this.errCode != null) {
			String msg = msgSource.getMessage(String.valueOf(this.errCode));
			if (dynamicStr != null)
				msg = String.format(msg,dynamicStr);
			return msg;
		}
		return super.getMessage();
	}

}
