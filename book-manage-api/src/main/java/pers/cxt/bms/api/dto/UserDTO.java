package pers.cxt.bms.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cxt
 * @Date 2020/7/9
 */
@Data
public class UserDTO {

    @ApiModelProperty("用户名")
    private String userId;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("类型ID")
    private String typeId;

    @ApiModelProperty("类型名称")
    private String typeName;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("页大小")
    private Integer pageSize;
}
