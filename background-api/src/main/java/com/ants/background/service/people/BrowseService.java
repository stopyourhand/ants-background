package com.ants.background.service.people;

import java.util.Map;

/**
 * 流量访问
 * @Author czd
 * @Date:createed in 2019/10/8
 * @Version: V1.0
 */
public interface BrowseService {
    /**
     * 获取指定时间内，不同用户类型(0:学生 1:教师 2:游客)的访问次数
     *
     * @param parameterMap
     * @return
     */
    Integer countBrowseNumber(Map parameterMap);
}
