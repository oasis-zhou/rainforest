package rf.foundation.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;


@Data
public class Pair<T> {

	private T originalObj;
	private T newObj;
	
	private List<Pair> subPairs = Lists.newArrayList();
	
	public Pair(T originalObj, T newObj){
		this.originalObj = originalObj;
		this.newObj = newObj;
	}
	
}
