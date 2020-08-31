package pers.cxt.bms.api.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Data
@Api("地区信息")
public class RegionDTO {
    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区/县")
    private String district;

    @ApiModelProperty("条件")
    private String condition;
}
