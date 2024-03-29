package com.ants.background.provider.mapper.announcement;

import com.ants.background.entity.announcement.Announcement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 处理公告的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface AnnouncementMapper {
    /**
     * 获取公告列表信息
     *
     * @param parameterMap
     * @return
     */
    List<Announcement> listAnnouncement(Map<String, Integer> parameterMap);

    /**
     * 获取公告的数量
     * @param state
     * @return
     */
    Integer countAnnouncementNumber(Integer state);

    /**
     * 增加一条新公告
     *
     * @param announcement
     * @return
     */
    Integer insertAnnouncement(Announcement announcement);

    /**
     * 修改公告信息
     *
     * @param parameterMap
     * @return
     */
    Integer updateAnnouncement(Map parameterMap);

    /**
     * 将反馈信息状态进行更改，即进入回收站
     *
     * @param map
     * @return
     */
    Integer updateAnnouncementState(Map<String, Integer> map);

    /**
     * 彻底公告反馈信息
     *
     * @param annId
     * @return
     */
    Integer deleteAnnouncement(Integer annId);

    /**
     * 增加新公告
     * @param parameterMap
     * @return
     */
    List<Announcement> listAnnouncementRecycle(Map<String, Integer> parameterMap);


}
