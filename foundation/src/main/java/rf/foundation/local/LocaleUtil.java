package rf.foundation.local;

import java.util.Locale;

public class LocaleUtil {
	
    public static Locale getLocale()   
    {  
//    	Locale locale = LocaleContextHolder.getLocale();
		Locale locale = null;
    	if (locale == null)
    		return Locale.SIMPLIFIED_CHINESE;
    	else
    		return locale;
    }
    
}
