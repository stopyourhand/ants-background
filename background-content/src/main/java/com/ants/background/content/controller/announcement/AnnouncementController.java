package com.ants.background.content.controller.announcement;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ants.background.constant.PageConsts;
import com.ants.background.content.thread.announcement.InsertAnnouncementThread;
import com.ants.background.content.thread.announcement.UpdateAnnouncementThread;
import com.ants.background.dto.feedback.DecorationDTO;
import com.ants.background.entity.announcement.Announcement;
import com.ants.background.service.announcement.AnnouncementService;
import com.ants.background.util.SpringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 对用户反馈进行业务操作
 *
 * @Author czd
 * @Date: create in 2019/9/24
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/background/announcement")
public class AnnouncementController {

    @Reference
    private AnnouncementService announcementService;

    /**
     * 列出公告信息的列表
     *
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map listAnnouncement(@RequestParam(value = "currentPage") int currentPage) {
        //返回给前端数据的map
        Map resultMap = new HashMap(16);
        //用来保存listAnnouncement方法中的head和length参数
        Map<String, Integer> parameterMap = new HashMap<>(16);

        //获取用户反馈信息的数量,state为0，表示公告为正常状态，未被回收进回收站
        int state = 0;
        //正常状态的公告数量
        int countAnnouncementNumber = announcementService.countAnnouncementNumber(state);
        //回收站状态的公告数量
        state = 1;
        int countAnnouncementRecycleNumber = announcementService.countAnnouncementNumber(state);

        int allAnnouncementNumber = countAnnouncementNumber + countAnnouncementRecycleNumber;

        //计算反馈列表的总页数
        int allPage = (allAnnouncementNumber / PageConsts.ANNOUNCEMENT_PAGE_NUMBER) + 1;
        if (allPage <= 0) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }
        resultMap.put("allPage", allPage);

        if (currentPage <= 0) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }

        //获取页面数开始的数据信息在数据库的坐标信息
        int head = (currentPage - 1) * PageConsts.ANNOUNCEMENT_PAGE_NUMBER;

        //传入初始页面的列表返回值
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.ANNOUNCEMENT_PAGE_NUMBER);

        //获取符合条件的返回列表的数据
        List<DecorationDTO> announcementList = announcementService.listAnnouncement(parameterMap);
        resultMap.put("announcementList", announcementList);
        return resultMap;
    }

    /**
     * 列出公告回收站信息的列表
     *
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/listRecycle", method = RequestMethod.GET)
    public Map listAnnouncementRecycle(@RequestParam(value = "currentPage") int currentPage) {
        //返回给前端数据的map
        Map resultMap = new HashMap(16);
        //用来保存listAnnouncementRecycle方法中的head和length参数
        Map<String, Integer> parameterMap = new HashMap<>(16);

        //获取用户反馈信息的数量,state为1,表示公告进入回收站，获取回收站的公告信息
        int state = 1;
        int countAnnouncementNumber = announcementService.countAnnouncementNumber(state);

        if (currentPage <= 0) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }
        //计算反馈列表的总页数
        int allPage = (countAnnouncementNumber / PageConsts.ANNOUNCEMENT_PAGE_NUMBER) + 1;
        if (allPage <= 0) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }
        resultMap.put("allPage", allPage);

        //获取页面数开始的数据信息在数据库的坐标信息
        int head = (currentPage - 1) * PageConsts.ANNOUNCEMENT_PAGE_NUMBER;

        //传入初始页面的列表返回值
        parameterMap.put("state", 1);
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.ANNOUNCEMENT_PAGE_NUMBER);

        //获取符合条件的返回列表的数据
        List<DecorationDTO> announcementList = announcementService.listAnnouncementRecycle(parameterMap);
        resultMap.put("announcementList", announcementList);
        return resultMap;
    }


    /**
     * 增加新的公告
     *
     * @param title
     * @param content
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Map insertAnnouncement(@RequestParam(value = "title") String title,
                                  @RequestParam(value = "content") String content) {
        //用来保存返回给前端数据的map
        Map resultMap = new HashMap();
        //存放参数信息
        Announcement announcement = new Announcement();

        if (title == null || "".equals(title)) {
            resultMap.put("msg", "标题不可以为空！");
            return resultMap;
        }
        announcement.setAnnTitle(title);

        if (content == null || "".equals(content)) {
            resultMap.put("msg", "内容不可以为空！");
            return resultMap;
        }
        announcement.setAnnContent(content);

        //获取系统时间
        Date date = new Date();
        //设置时间的输出格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取公告发布时间
        String annReleaseTime = simpleDateFormat.format(date);
        announcement.setAnnTime(annReleaseTime);
        //0为正常 1为回收站
        announcement.setState(0);

        //获取线程池
        ExecutorService executorService = (ExecutorService) SpringUtil.getBean("ExecutorService");
        //将对象传入线程中并且获取参数值执行添加方法
        InsertAnnouncementThread announcementThread = new InsertAnnouncementThread(announcement);
        executorService.execute(announcementThread);

//        int result = announcementService.insertAnnouncement(announcement);
//        //判断是否添加一条新的公告成功
//        if (result <= 0) {
//            resultMap.put("msg", "添加新的公告失败!");
//            return resultMap;
//        }
        resultMap.put("msg", "插入信息成功");
        return resultMap;
    }


    /**
     * 更改公告操作
     *
     * @param annId
     * @param annTitle
     * @param annContent
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Map updateAnnouncement(@RequestParam(value = "id") Integer annId,
                                  @RequestParam(value = "title") String annTitle,
                                  @RequestParam(value = "content") String annContent) {
        //保存返回给前端的数据的map
        Map resultMap = new HashMap();
        //用来保存数据库方法的传入的参数的值
        Map parameterMap = new HashMap<>();

        //判断公告编号是否出现错误
        if (annId <= 0) {
            resultMap.put("msg", "公告编号出现错误！");
            return resultMap;
        }
        parameterMap.put("annId", annId);

        //判断标题是否满足格式
        if (annTitle == null || "".equals(annTitle)) {
            resultMap.put("msg", "更改的标题不可以为空！");
            return resultMap;
        }
        parameterMap.put("annTitle", annTitle);

        //判断公告内容是否满足格式
        if (annContent == null || "".equals(annContent)) {
            resultMap.put("msg", "更改的内容不可以为空！");
            return resultMap;
        }
        parameterMap.put("annContent", annContent);

        //获取修改公告的系统时间
        Date date = new Date();
        //设置时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取修改公告的常规时间
        String annTime = simpleDateFormat.format(date);
        parameterMap.put("annTime", annTime);
        //获取线程池
        ExecutorService executorService = (ExecutorService)SpringUtil.getBean("ExecutorService");
        //将parameterMap对象传入线程中并且获取参数值执行添加方法
        UpdateAnnouncementThread updateAnnouncementThread = new UpdateAnnouncementThread(parameterMap);
        executorService.execute(updateAnnouncementThread);

//        int result = announcementService.updateAnnouncement(parameterMap);
//        if (result <= 0) {
//            resultMap.put("msg", "更改公告失败！");
//            return resultMap;
//        }

        resultMap.put("msg", "修改成功");
        return resultMap;
    }

    /**
     * 判断对公告信息进行哪种删除操作
     * type -> 0 -> 删除
     * type -> 1 -> 撤销删除
     * type -> 2 -> 彻底删除
     *
     * @param type
     * @param idList
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Map deleteFeedback(@RequestParam(value = "type") Integer type,
                              @RequestParam(value = "idList") List<Integer> idList) {
        //存放返回给前端数据的一个map
        Map resultMap = new HashMap(16);
        //存放对数据库的操作方法的参数的值
        Map<String, Integer> parameterMap = new HashMap<>(16);

        //判断id列表是否为空，若为空即代表没选中要删除的公告信息
        if (idList.size() <= 0) {
            resultMap.put("msg", "请选择要删除的公告信息！");
            return resultMap;
        }
        //判断删除操作是否是删除，撤销删除还是彻底删除
        if (type < 0 || type > 2) {
            resultMap.put("msg", "删除类型错误！");
            return resultMap;
        }
        System.out.println("type:" + type);
        //对删除的操作类型进下判断
        switch (type) {
            //删除操作，即将用户反馈信息放进回收站
            case 0:
                System.out.println("删除操作");
                //获取要删除的反馈信息的id列表，对其进行状态改变，即弄进回收站里面
                for (int annId : idList) {
                    parameterMap.put("annId", annId);
                    parameterMap.put("state", 1);
                    System.out.println("anmId: " + annId);
                    int result = announcementService.updateAnnouncementState(parameterMap);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，公告编号出现错误!");
                        return resultMap;
                    }
                }
                break;
            //撤销删除操作
            case 1:
                //获取要撤销删除的反馈信息的id列表，对其进行状态改变
                for (int annId : idList) {
                    parameterMap.put("annId", annId);
                    parameterMap.put("state", 0);
                    int result = announcementService.updateAnnouncementState(parameterMap);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
                break;
            //彻底删除
            case 2:
                //获取要撤销删除的反馈信息的id列表，对其进行状态改变
                for (int annId : idList) {
                    int result = announcementService.deleteAnnouncement(annId);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
        }
        resultMap.put("msg", "删除成功!");
        return resultMap;
    }



}
