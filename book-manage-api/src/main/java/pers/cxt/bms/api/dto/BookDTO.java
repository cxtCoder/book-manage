package pers.cxt.bms.api.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.cxt.bms.api.bo.BookContentBO;

import java.util.List;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Data
@Api("图书信息")
public class BookDTO {

    @ApiModelProperty("图书ID")
    private String bookId;

    @ApiModelProperty("图书名称")
    private String bookName;

    @ApiModelProperty("图书名称（副）")
    private String bookSubName;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("出版社")
    private String publishing;

    @ApiModelProperty("出版日期")
    private String publishedDate;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("大类")
    private String classify;

    @ApiModelProperty("小类")
    private String subClassify;

    @ApiModelProperty("分类ID")
    private String classifyId;

    @ApiModelProperty("辑")
    private String series;

    @ApiModelProperty("辑次序")
    private Integer seriesOrder;

    @ApiModelProperty("缩略图")
    private String thumbPath;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区/县")
    private String district;

    @ApiModelProperty("存放位置")
    private String position;

    @ApiModelProperty("录入者")
    private String createUser;

    @ApiModelProperty("录入者ID")
    private String userId;

    @ApiModelProperty("目录信息")
    private List<BookContentBO> contentData;

    @ApiModelProperty("保存时间")
    private String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;

    @ApiModelProperty("查询模式")
    private Integer findMode;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("页大小")
    private Integer pageSize;
}
