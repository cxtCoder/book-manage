package pers.cxt.bms.api.bo;

import lombok.Data;

import java.util.List;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Data
public class BookContentBO {
    /**
     * 目录ID
     */
    private String id;
    /**
     * 目录名称
     */
    private String label;
    /**
     * 上级目录ID，-1为根节点
     */
    private String upperId;
    /**
     * 目录层级
     */
    private Integer level;
    /**
     * 目录作者
     */
    private String author;
    /**
     * 目录页码
     */
    private Integer startPage;
    /**
     * 目录内容
     */
    private String desc;

    List<BookContentBO> children;
}
