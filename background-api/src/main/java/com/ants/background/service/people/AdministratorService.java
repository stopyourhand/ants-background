package com.ants.background.service.people;

import java.util.Map;

/**
 * 处理管理员的service层
 *
 * @Author czd
 * @Date:createed in 2019/10/24
 * @Version: V1.0
 */
public interface AdministratorService {
    /**
     * 获取管理员账号对应的密码，进行登录判断
     * @param adminId
     * @return
     */
    String getAdministratorPassword(Integer adminId);

    /**
     * 获取管理员原来的密码
     * @param parameterMap
     * @return
     */
    String getOldPassword(Integer adminId);

    /**
     * 修改管理员密码
     * @param parameterMap
     * @return
     */
    int updateAdminPassword(Map parameterMap);

}
