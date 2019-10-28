package com.ants.background.content.thread.announcement;

import com.ants.background.entity.announcement.Announcement;
import com.ants.background.service.announcement.AnnouncementService;
import com.ants.background.util.SpringUtil;

/**
 * 增加新公告
 * @Author czd
 * @Date:createed in 2019/10/18
 * @Version: V1.0
 */
public class InsertAnnouncementThread implements Runnable {
    //声明公告对象，存放参数信息
    private Announcement announcement;

    //获取公告接口
    private AnnouncementService announcementService = SpringUtil.getBean(AnnouncementService.class);

    public InsertAnnouncementThread(Announcement announcement){
        this.announcement = announcement;
    }

    @Override
    public void run() {
        //执行添加操作
        announcementService.insertAnnouncement(this.announcement);
    }
}
