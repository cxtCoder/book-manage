package pers.cxt.bms.api.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author cxt
 * @Date 2020/7/3
 */
@Data
@Api("图书ID信息")
public class BookIdDTO {

    @ApiModelProperty("图书ID")
    private List<String> selectedIds;
}
