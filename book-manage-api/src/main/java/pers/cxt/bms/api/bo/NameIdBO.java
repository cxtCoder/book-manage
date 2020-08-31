package pers.cxt.bms.api.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cxt
 * @Date 2020/7/8
 */
@Data
public class NameIdBO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("name")
    private String name;
}
