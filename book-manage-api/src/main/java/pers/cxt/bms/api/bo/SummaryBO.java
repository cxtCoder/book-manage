package pers.cxt.bms.api.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cxt
 * @Date 2020/7/8
 */
@Data
public class SummaryBO {
    @ApiModelProperty("馆藏图书总数")
    private long bookNumber;

    @ApiModelProperty("名人书画总数")
    private long paintingNumber;

    @ApiModelProperty("文物古迹总数")
    private long relicNumber;
}
