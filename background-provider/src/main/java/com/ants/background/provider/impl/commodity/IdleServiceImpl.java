package com.ants.background.provider.impl.commodity;

import com.alibaba.dubbo.config.annotation.Service;
import com.ants.background.dto.commodity.AuditDTO;
import com.ants.background.dto.commodity.CommodityDTO;
import com.ants.background.entity.commodity.IdleGoods;
import com.ants.background.provider.mapper.commodity.IdleMapper;
import com.ants.background.service.commodity.IdleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 处理闲置的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service
public class IdleServiceImpl implements IdleService {
    @Autowired
    private IdleMapper idleMapper;

    /**
     * 获取在指定时间内(七天)发布闲置的数量
     *
     * @return
     */
    public Integer countReleaseIdleNumber(Map<String, String> parameterMap) {
        return idleMapper.countReleaseIdleNumber(parameterMap);
    }

    /**
     * 获取在已经通过审核的闲置的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<AuditDTO> listAuditedIdleGoods(Map<String, Integer> parameterMap) {
        return idleMapper.listAuditedIdleGoods(parameterMap);
    }

    /**
     * 获取交易分析的列出不同时间段的发布成功的闲置的商品的信息
     * @param parameterMap
     * @return
     */
    public List<IdleGoods> listIdleAnalysis(Map<String,String> parameterMap){
        return idleMapper.listIdleAnalysis(parameterMap);
    }

    /**
     * 统计审核通过的闲置的商品的数量
     *
     * @return
     */
    public Integer countAuditedIdleGoods() {
        return idleMapper.countAuditedIdleGoods();
    }

    /**
     * 获取在闲置表的所有商品(审核通过,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<CommodityDTO> listIdleCommodity(Map<String, Integer> parameterMap) {
        return idleMapper.listIdleCommodity(parameterMap);
    }

    /**
     * 统计闲置的商品中通过审核或者正在回收站的商品的数量
     *
     * @param goodsType
     * @return
     */
    public Integer countIdleCommodity(int goodsType) {
        return idleMapper.countIdleCommodity(goodsType);
    }

    /**
     * 获取在指定时间内不同分类的闲置商品的数量
     * @param parameterMap
     * @return
     */
    public Integer countReleaseClassifyIdleNumber(Map<String,Integer> parameterMap){
        return idleMapper.countReleaseClassifyIdleNumber(parameterMap);
    }

    /**
     * 彻底删除闲置商品的信息
     * @param goodsId
     * @return
     */
    public Integer deleteIdle(Integer goodsId){
        return idleMapper.deleteIdle(goodsId);
    }

    /**
     * 将闲置商品状态进行更改，即进入回收站 -> 商品状态: goodsType 0:审核通过 1:回收站
     * @param parameterMap
     * @return
     */
    public Integer updateIdle(Map<String,Integer> parameterMap){
        return idleMapper.updateIdle(parameterMap);
    }
}
