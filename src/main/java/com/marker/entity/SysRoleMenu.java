package com.marker.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_menu")
@ApiModel(value="SysRoleMenu对象", description="角色菜单关系表")
public class SysRoleMenu implements Serializable {

    public static void main(String[] args) {

    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
      private Integer roleId;

    @ApiModelProperty(value = "菜单id")
    private Integer menuId;


}
