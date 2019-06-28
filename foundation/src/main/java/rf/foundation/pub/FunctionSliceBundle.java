package rf.foundation.pub;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import rf.foundation.context.AppContext;
import java.beans.Introspector;
import java.util.List;
import java.util.Map;

public class FunctionSliceBundle<T> {
	
	private T t;
	private Map<String,Object> context = Maps.newHashMap();
	
	private List<FunctionSlice<T>> workers = Lists.newArrayList();
	
	public FunctionSliceBundle(T t) {
		this.t = t;

	}
	
	public FunctionSliceBundle(T t, Map<String,Object> context){
		this.t = t;
		this.context = context;
	}
	
	public FunctionSliceBundle<T> register(Class<?> clazz) {
		return this.register(Introspector.decapitalize(clazz.getSimpleName()), FunctionSlice.class);
	}

	@SuppressWarnings("unchecked")
	public FunctionSliceBundle<T> register(String beanName, Class<?> clazz) {
		FunctionSlice<T> worker = AppContext.getBean(beanName, FunctionSlice.class);
		this.workers.add(worker);
		return this;
	}
	
	public FunctionSliceBundle<T> register(FunctionSlice<T> worker) {
		this.workers.add(worker);
		return this;
	}
	
	public void execute(){
		for(FunctionSlice<T> workItem : this.workers) {
			workItem.execute(this.t, this.context);
		}
	}

	public Map<String, Object> getContext() {
		return context;
	}
}