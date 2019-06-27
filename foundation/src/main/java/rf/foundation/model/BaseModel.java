package rf.foundation.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
public class BaseModel implements Serializable{

	private String uuid;
	private Map<String,Object> dynamicFields = Maps.newHashMap();
	private List<ModelComponent> subComponents = Lists.newArrayList();

	public <T extends ModelComponent> List<T> getSubComponentsByType(Class<T> clazz) {
		List<T> subComponents = Lists.newArrayList();
		for (ModelComponent sub : this.subComponents) {
			if (clazz.isAssignableFrom(sub.getClass()))
				subComponents.add((T) sub);
		}

		return subComponents;
	}

	public <T extends ModelComponent> List<T> getAllSubComponentsByType(Class<T> clazz) {
		List<T> subComponents = Lists.newArrayList();
		for (ModelComponent sub : this.subComponents) {
			if (clazz.isAssignableFrom(sub.getClass()))
				subComponents.add((T) sub);
			subComponents.addAll(sub.getAllSubComponentsByType(clazz));
		}

		return subComponents;
	}

	public <T extends ModelComponent> List<T> getAllSubComponents() {
		List<T> subComponents = Lists.newArrayList();
		for (ModelComponent sub : this.subComponents) {
				subComponents.add((T) sub);
			subComponents.addAll(sub.getAllSubComponents());
		}

		return subComponents;
	}

	public <T extends ModelComponent> void removeSubComponentsByType(Class<T> clazz){
		Iterator itr = this.subComponents.iterator();
		while(itr.hasNext()){
			if (clazz.isAssignableFrom(itr.next().getClass())){
				itr.remove();
			}
		}
	}

	public Object getDynamicFieldValue(String key){
		return this.dynamicFields.get(key);
	}
	
	
}
