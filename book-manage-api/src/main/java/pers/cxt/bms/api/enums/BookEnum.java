package pers.cxt.bms.api.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
public enum BookEnum {
    WS("0", "文史类"),
    ZS("1", "志书类"),
    WX("2", "文学类"),
    LS("3", "历史类"),
    GJ("4", "工具类"),
    QT("5", "其他类");

    private BookEnum(String id, String name) {
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

    public static List<String> getBookEnumName() {
        List<String> bookEnums = new ArrayList<>();
        for (BookEnum bookEnum : BookEnum.values()) {
            bookEnums.add(bookEnum.getName());
        }
        return bookEnums;
    }
}
