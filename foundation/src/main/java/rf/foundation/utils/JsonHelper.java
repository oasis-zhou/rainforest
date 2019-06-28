package rf.foundation.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;

@Service
public class JsonHelper {

	private ObjectMapper mapper;

	public JsonHelper(){
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
		mapper.configure(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS, true);
		mapper.configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);
	}

	public <T> T fromJSON(String json, Class<T> valueType) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			mapper.setDateFormat(sdf);
			return (T) mapper.readValue(json, valueType);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toJSON(Object obj) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			mapper.setDateFormat(sdf);
			String jsonString = mapper.writeValueAsString(obj);
			return jsonString;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public <T> T fromJSON(InputStream input, Class<T> valueType) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			mapper.setDateFormat(sdf);
			return (T) mapper.readValue(input, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
