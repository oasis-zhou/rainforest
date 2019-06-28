package rf.product.model;

import rf.foundation.model.ModelComponent;
import rf.product.model.enums.TagSpecType;

public class TagSpec extends ModelComponent {
    private String code;
    private String name;
    private TagSpecType type;
    private String content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagSpecType getType() {
        return type;
    }

    public void setType(TagSpecType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
