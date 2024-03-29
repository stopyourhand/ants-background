package com.ants.background.service.commodity;

import com.ants.background.dto.commodity.AuditDTO;
import com.ants.background.dto.commodity.CommodityDTO;
import com.ants.background.entity.commodity.IdleGoods;

import java.util.List;
import java.util.Map;

/**
 * 处理闲置的service层
 *
 * @Author czd
 * @Date:createed in 2019/10/24
 * @Version: V1.0
 */
public interface IdleService {
    /**
     * 获取在指定时间内(七天)发布闲置的数量
     * @return
     */
    Integer countReleaseIdleNumber(Map<String, String> parameterMap);

    /**
     * 获取在已经通过审核的闲置的商品名称,价格,发布类型以及所属卖家信息的列表
     * @param parameterMap
     * @return
     */
    List<AuditDTO> listAuditedIdleGoods(Map<String, Integer> parameterMap);

    /**
     * 获取交易分析的列出不同时间段的发布成功的闲置的商品的信息
     * @param parameterMap
     * @return
     */
    List<IdleGoods> listIdleAnalysis(Map<String, String> parameterMap);

    /**
     * 统计审核通过的闲置的商品的数量
     * @return
     */
    Integer countAuditedIdleGoods();

    /**
     * 获取在闲置表的所有商品(审核通过,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    List<CommodityDTO> listIdleCommodity(Map<String, Integer> parameterMap);

    /**
     * 统计闲置的商品中通过审核或者正在回收站的商品的数量
     *
     * @param goodsType
     * @return
     */
    Integer countIdleCommodity(int goodsType);

    /**
     * 获取在指定时间内不同分类的闲置商品的数量
     * @param parameterMap
     * @return
     */
    Integer countReleaseClassifyIdleNumber(Map<String, Integer> parameterMap);

    /**
     * 彻底删除闲置商品的信息
     * @param goodsId
     * @return
     */
    Integer deleteIdle(Integer goodsId);

    /**
     * 将闲置商品状态进行更改，即进入回收站 -> 商品状态: goodsType 0:审核通过 1:回收站
     * @param parameterMap
     * @return
     */
    Integer updateIdle(Map<String, Integer> parameterMap);

}
