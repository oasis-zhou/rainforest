package rf.foundation.local;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class LocalResourceBundleMessageSource extends ResourceBundleMessageSource {
	
	private LocaleResolver localeResolver;

	public LocaleResolver getLocaleResolver() {
		return localeResolver;
	}

	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}
	
	public String getMessage(String code, HttpServletRequest request){
		return this.getMessage(code, new Object[0],request);
	}
	
	public String getMessage(String code, Object[] args, HttpServletRequest request){
		try{
			return this.getMessage(code, args, this.localeResolver.resolveLocale(request));
		}
		catch( NoSuchMessageException ex ){
			return this.generateCodeNotExistWaring( code );
		}
	}
	
	public String getMessage(String code){
		
		return getMessage(code,LocaleUtil.getLocale());
	}
	
	public String getMessage( String code, Locale locale){
		try{
			return this.getMessage( code, new Object[0], locale );
		}
		catch( NoSuchMessageException ex ){
			return this.generateCodeNotExistWaring( code );
		}
	}
	
	protected String generateCodeNotExistWaring(String code){
		return "$$" + code + "$$";
	}
}
