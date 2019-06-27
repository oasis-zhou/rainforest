package rf.foundation.model;

import com.google.common.collect.Lists;

import java.util.List;

public class Pair<T> {

	private T originalObj;
	private T newObj;
	
	private List<Pair> subPairs = Lists.newArrayList();
	
	public Pair(T originalObj, T newObj){
		this.originalObj = originalObj;
		this.newObj = newObj;
	}

	public T getOriginalObj() {
		return originalObj;
	}

	public T getNewObj() {
		return newObj;
	}

	public List<Pair> getSubPairs() {
		return subPairs;
	}

	public void setSubPairs(List<Pair> subPairs) {
		this.subPairs = subPairs;
	}
	
	
	
	
}
