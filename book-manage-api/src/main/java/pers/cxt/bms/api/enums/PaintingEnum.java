package pers.cxt.bms.api.enums;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
public enum PaintingEnum {
    MRSH("0", "名人书画");

    private PaintingEnum(String id, String name) {
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
