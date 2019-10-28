package com.ants.background.entity.people;

import lombok.Data;

/**
 * 对应着ants_management_administrator数据库表
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class Administrator {
    /** 主键 */
    private Integer adminId;
    /** 管理员名称 */
    private String adminName;
    /** 管理员密码 */
    private String adminPassword;
    /** 管理员头像 */
    private String adminPicture;
    /** 管理员上次登录时间 */
    private String lastLoginTime;
    /** 管理员上次登录IP地址 */
    private String lastLoginIp;
}
