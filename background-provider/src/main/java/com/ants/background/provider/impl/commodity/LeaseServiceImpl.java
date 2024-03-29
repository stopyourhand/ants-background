package com.ants.background.provider.impl.commodity;

import com.alibaba.dubbo.config.annotation.Service;
import com.ants.background.dto.commodity.AuditDTO;
import com.ants.background.dto.commodity.CommodityDTO;
import com.ants.background.entity.commodity.LeaseGoods;
import com.ants.background.provider.mapper.commodity.LeaseMapper;
import com.ants.background.service.commodity.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 处理租赁的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service
public class LeaseServiceImpl implements LeaseService {
    @Autowired
    private LeaseMapper leaseMapper;

    /**
     * 获取在指定时间内(七天)发布租赁的数量
     * @return
     */
    public Integer countReleaseLeaseNumber(Map<String,String> parameterMap){
        return leaseMapper.countReleaseLeaseNumber(parameterMap);
    }

    /**
     * 获取在已经通过审核的闲置的商品名称,价格,发布类型以及所属卖家信息的列表
     * @param parameterMap
     * @return
     */
    public List<AuditDTO> listAuditedLeaseGoods(Map<String,Integer> parameterMap){
        return leaseMapper.listAuditedLeaseGoods(parameterMap);
    }

    /**
     * 获取交易分析的列出不同时间段的发布成功的租赁的商品的信息
     * @param parameterMap
     * @return
     */
    public List<LeaseGoods> listLeaseAnalysis(Map<String,String> parameterMap){
        return leaseMapper.listLeaseAnalysis(parameterMap);
    }

    /**
     * 统计审核通过的闲置的商品的数量
     * @return
     */
    public Integer countAuditedLeaseGoods(){
        return leaseMapper.countAuditedLeaseGoods();
    }

    /**
     * 获取在闲置表的所有商品(审核通过,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<CommodityDTO> listLeaseCommodity(Map<String, Integer> parameterMap){
        return leaseMapper.listLeaseCommodity(parameterMap);
    }

    /**
     * 统计闲置的商品中通过审核或者正在回收站的商品的数量
     *
     * @param goodsType
     * @return
     */
    public Integer countLeaseCommodity(int goodsType){
        return leaseMapper.countLeaseCommodity(goodsType);
    }

    /**
     * 获取在指定时间内不同分类的租赁商品的数量
     * @param parameterMap
     * @return
     */
    public Integer countReleaseClassifyLeaseNumber(Map<String,Integer> parameterMap){
        return leaseMapper.countReleaseClassifyLeaseNumber(parameterMap);
    }

    /**
     * 彻底删除租赁商品的信息
     * @param goodsId
     * @return
     */
    public Integer deleteLease(Integer goodsId){
        return leaseMapper.deleteLease(goodsId);
    }

    /**
     * 将租赁商品状态进行更改，即进入回收站 -> 商品状态: goodsType 0:审核通过 1:回收站
     * @param parameterMap
     * @return
     */
    public Integer updateLease(Map<String,Integer> parameterMap){
        return leaseMapper.updateLease(parameterMap);
    }
}
