package pers.cxt.bms.database.entity;

import java.io.Serializable;

public class BmContent implements Serializable {
    private String contentId;

    private String name;

    private String upContentId;

    private Integer level;

    private String author;

    private Integer startPage;

    private String description;

    private String bookId;

    private static final long serialVersionUID = 1L;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId == null ? null : contentId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUpContentId() {
        return upContentId;
    }

    public void setUpContentId(String upContentId) {
        this.upContentId = upContentId == null ? null : upContentId.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }
}