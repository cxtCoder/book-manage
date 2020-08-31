package pers.cxt.bms.api.enums;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
public enum FileTypeEnum {
    WORD("word", "WORD文档"),
    PDF("pdf", "PDF文档");

    private FileTypeEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
