package pers.cxt.bms.api.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cxt
 * @Date 2020/7/4
 */
@Data
@Api("类别信息")
public class ClassifyDTO {

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("大类别")
    private String classify;

    @ApiModelProperty("小类别")
    private String subClassify;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;
}
