package com.ants.background.provider.impl.classify;

import com.alibaba.dubbo.config.annotation.Service;
import com.ants.background.provider.mapper.classify.ClassificationMapper;
import com.ants.background.service.classify.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 处理商品所属的大分类的交易信息统计数量的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service
public class ClassificationServiceImpl implements ClassificationService {

    @Autowired
    private ClassificationMapper classificationMapper;

    /**
     * 计算交易完成的商品中,商品所属的大分类的交易信息统计数量
     * @param parameterMap
     * @return
     */
    public Integer countClassificationNumber(Map<String,Integer> parameterMap){
        return classificationMapper.countClassificationNumber(parameterMap);
    }

}
