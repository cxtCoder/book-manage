package pers.cxt.bms.api.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cxt
 * @Date 2020/7/4
 */
@Data
@Api("小类别信息")
public class SubClassifyDTO {

    @ApiModelProperty("大类别")
    private String classify;

    @ApiModelProperty("小类别")
    private String subClassify;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("页大小")
    private Integer pageSize;
}
