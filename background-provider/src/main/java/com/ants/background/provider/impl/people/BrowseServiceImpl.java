package com.ants.background.provider.impl.people;

import com.alibaba.dubbo.config.annotation.Service;
import com.ants.background.provider.mapper.people.BrowseMapper;
import com.ants.background.service.people.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 处理流量访问的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service
public class BrowseServiceImpl implements BrowseService {

    @Autowired
    private BrowseMapper browseMapper;

    /**
     * 获取指定时间内，不同用户类型(0:学生 1:教师 2:游客)的访问次数
     *
     * @param parameterMap
     * @return
     */
    public Integer countBrowseNumber(Map parameterMap){
        return browseMapper.countBrowseNumber(parameterMap);
    }
}
