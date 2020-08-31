package pers.cxt.bms.api.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cxt
 * @Date 2020/7/8
 */
@Data
public class BookSummaryBO {
    @ApiModelProperty("文史类")
    private long wsNumber;

    @ApiModelProperty("志书类")
    private long zsNumber;

    @ApiModelProperty("文学类")
    private long wxNumber;

    @ApiModelProperty("历史类")
    private long lsNumber;

    @ApiModelProperty("工具类")
    private long gjNumber;

    @ApiModelProperty("其他类")
    private long qtNumber;
}
