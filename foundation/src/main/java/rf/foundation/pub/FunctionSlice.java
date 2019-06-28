package rf.foundation.pub;

import java.util.Map;

public interface FunctionSlice<T> {
	
	public void execute(T t, Map<String, Object> context);

}
