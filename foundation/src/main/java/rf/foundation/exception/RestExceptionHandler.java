package rf.foundation.exception;

import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(GenericException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map genericExceptionHandler(GenericException e) {
        e.printStackTrace();
        Map<String, Object> errorBody = buildErrorBody(e);
        return errorBody;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map exceptionHandler(Exception e) {
        e.printStackTrace();
        GenericException ge = new GenericException(10001L);
        Map<String, Object> errorBody = buildErrorBody(ge);
        return errorBody;
    }


    private Map<String, Object> buildErrorBody(GenericException ge) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("ErrorCode", ge.getErrorCode());
        result.put("ErrorMsg", ge.getMessage());
        return result;
    }
}
