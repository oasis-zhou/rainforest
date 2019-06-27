package rf.foundation.utils;


import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;
import rf.foundation.anno.FieldSpec;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

public class ObjFieldUtil {
	private static final String FIELDSPEC_ANNOTATION_FIELD_NAME = "code";

	public static Map<String,Object> getFieldValues(Object obj){
		Map<String,Object> ret = Maps.newHashMap();

		Map<Object,Field> fieldMap = Maps.newHashMap();
		getFields(obj.getClass(), FieldSpec.class,FIELDSPEC_ANNOTATION_FIELD_NAME,fieldMap);

		Iterator itr = fieldMap.keySet().iterator();
		while(itr.hasNext()){
			Object path = itr.next();
			Field field = fieldMap.get(path);

			if(field != null) {  
				field.setAccessible(true);  
				try {  
					Object fieldValue = field.get(obj);  
					ret.put((String)path, fieldValue);

				} catch (Exception e) {  
					e.printStackTrace();  
				}  
			}  
		}
		return ret;
	}

	private static void getFields(Class<?> clazz,Class annoClass,String annoFieldName,Map<Object,Field> fields) {  

		if (clazz != null) {

			Field[] fs = clazz.getDeclaredFields();  
			for(Field field : fs) { 
				if(field.getAnnotation(annoClass) != null){	            			 
					Annotation anno = field.getAnnotation(annoClass);
					Object annoValue = getAnnoFieldValue(anno,annoClass,annoFieldName);	            			 
					fields.put(annoValue, field);
				}
			}

			getFields(clazz.getSuperclass(),annoClass,annoFieldName,fields);  

		}  
	}  


	public static Map<String,Annotation[]> getFieldNameAndAnnatationsMap(Class clazz) {  
		Map<String,Annotation[]> ret = Maps.newHashMap();
		Field[] fields = clazz.getDeclaredFields();  
		for(Field field : fields) {  
			ret.put(field.getName(), field.getAnnotations());  
		}  
		return ret;  
	}  

	public static Map<String,Annotation> getFieldNameAndAnnotationMap(Class clazz,Class annoClass) {  
		Map<String,Annotation> ret = Maps.newHashMap();
		Field[] fields = clazz.getDeclaredFields();  
		for(Field field : fields) {  
			ret.put(field.getName(), field.getAnnotation(annoClass));  
		}  
		return ret;  
	}  

	public static Annotation getObjAnnotation(Class objClass,Class annoClass) {  
		return objClass.getAnnotation(annoClass);  
	}  

	public static Object getAnnoFieldValue(Annotation anno,Class<?> annoClass, String annoFieldName){
		Object ret = null;  
		if(anno != null) {  
			try {  
				Method method = annoClass.getMethod(annoFieldName); //Annotation field is a method
				ret = method.invoke(anno);  
			} catch (SecurityException e) {  
				e.printStackTrace();  
			} catch (NoSuchMethodException e) {  
				e.printStackTrace();  
			} catch (IllegalArgumentException e) {  
				e.printStackTrace();  
			} catch (IllegalAccessException e) {  
				e.printStackTrace();  
			} catch (InvocationTargetException e) {  
				e.printStackTrace();  
			}   
		} 
		return ret;  
	}

	/** 
	 * get object field annotation parameter's value
	 * eg: ObjFieldUtil.getAnnoFieldValue(OrgInfo.class, "parent", Column.class, "name")  
	 *  usage:get OrgInfo class parent field @Column annotation attribute 'name' value 
	 * @param objClass 
	 * @param objFieldName 
	 * @param annoClass 
	 * @param annoFieldName 
	 * @return 
	 */  
	public static Object getAnnoFieldValue(Class<?> objClass,String objFieldName,Class<?> annoClass, String annoFieldName) {  
		Map<String,Annotation> fieldNameAndAnnatationMap = getFieldNameAndAnnotationMap(objClass, annoClass);  
		Annotation anno = fieldNameAndAnnatationMap.get(objFieldName);  
		Object ret = getAnnoFieldValue(anno,annoClass,annoFieldName);

		return ret;  
	}  

	/** 
	 * get object instance field value (include parent class)
	 * @param obj object instance 
	 * @param fieldName 
	 * @return 
	 */  
	public static Object getFieldValue(Object obj, String fieldName) {  
		Object ret = null;  
		Field field = getField(obj.getClass(), fieldName);  
		if(field != null) {  
			field.setAccessible(true);  
			try {  
				ret = field.get(obj);  
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
		}  
		return ret;  
	}  


	/**
	 * get class field (include parent class)
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Field getField(Class<?> clazz, String fieldName) {  
		Field ret = null;  
		if (clazz != null && !StringUtils.isEmpty(fieldName)) {
			try {  
				fieldName = fieldName.trim();  
				ret = clazz.getDeclaredField(fieldName);  
			} catch (Exception e) {//if get null and then find it from it's parent
				ret = getField(clazz.getSuperclass(), fieldName);  
			}  
		}  
		return ret;  
	}  


}
