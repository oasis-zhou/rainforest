package rf.rating;

import rf.rating.model.RatingNode;
import java.util.Map;


public interface Calculator {

    public Map<String,Object> eval(RatingNode node);
}
