package com.ants.background.content.controller.people;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ants.background.constant.PageConsts;
import com.ants.background.dto.people.UserDTO;
import com.ants.background.service.people.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 对用户管理进行业务操作
 *
 * @Author czd
 * @Date: create in 2019/9/24
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/background/user")
public class UserController {

    @Reference
    private UserService userService;

    /**
     * 所有用户的信息的信息列表
     * userType -> 0:代表所有用户，包括回收站的用户 1:回收站的用户
     *
     * @param currentPage
     * @return
     */
    @GetMapping(value = "/list")
    public Map usersManagementAll(@RequestParam(value = "currentPage") int currentPage) {
        //用来保存返回给前端数据的hashMap
        Map resultMap = new HashMap(16);
        //用来保存传输给数据库参数的数据下标的值，limit
        Map<String, Integer> parameterMap = new HashMap<>(16);
        //判断数据是否符合要求
        if (currentPage <= 0) {
            resultMap.put("msg", "页数数据传输错误!");
            return resultMap;
        }

        int length = PageConsts.USER_MANAGEMENT_PAGE_NUMBER;
        //limit数据获取初始下标
        int head = (currentPage - 1) * length;
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.USER_MANAGEMENT_PAGE_NUMBER);

        //用户列表总页数
        int allPage = 0;

        //获取数据库中所有用户的数据信息
        parameterMap.put("judge", 0);
        List<UserDTO> userDTOList = userService.listUsers(parameterMap);
        resultMap.put("userList", userDTOList);

        //统计所有用户的数量(包括回收站)
        int userNumber = userService.countUser(parameterMap);
        if (userNumber < 0) {
            resultMap.put("msg", "数据库获取数据错误!");
            return resultMap;
        }

        //获取所有用户的数量的总页数
        allPage = (userNumber / PageConsts.USER_MANAGEMENT_PAGE_NUMBER) + 1;
        resultMap.put("allPage", allPage);

        return resultMap;
    }

    /**
     * 所有用户的信息列表，以及回收站用户的信息列表
     * userType -> 0:代表所有用户，包括回收站的用户 1:回收站的用户
     *
     * @param currentPage
     * @return
     */
    @GetMapping(value = "/listRecycle")
    public Map usersManagementRecycle(@RequestParam(value = "currentPage") int currentPage) {
        //用来保存返回给前端数据的hashMap
        Map resultMap = new HashMap(16);
        //判断数据是否符合要求
        if (currentPage <= 0) {
            resultMap.put("msg", "页数数据传输错误!");
            return resultMap;
        }

        //用来保存传输给数据库参数的数据下标的值，limit
        Map<String, Integer> parameterMap = new HashMap<>(16);
        //limit数据获取初始下标
        int head = (currentPage - 1) * PageConsts.USER_MANAGEMENT_PAGE_NUMBER;
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.USER_MANAGEMENT_PAGE_NUMBER);


        //获取数据库中回收站用户的数据信息
        parameterMap.put("judge", 1);
        //获取回收站用户
        parameterMap.put("userType", 1);
        List<UserDTO> userDTORecycleList = userService.listUsers(parameterMap);
        resultMap.put("userList", userDTORecycleList);

        //统计所有用户的数量(包括回收站)
        int userRecycleNumber = userService.countUser(parameterMap);
        if (userRecycleNumber < 0) {
            resultMap.put("msg", "数据库获取数据错误!");
            return resultMap;
        }

        //获取所有用户的数量的总页数
        int allPage = (userRecycleNumber / PageConsts.USER_MANAGEMENT_PAGE_NUMBER) + 1;
        if (allPage <= 0) {
            resultMap.put("msg", "数据获取出现错误!");
            return resultMap;
        }
        resultMap.put("allPage", allPage);

        return resultMap;
    }

    /**
     * 判断对用户信息进行哪种删除操作
     * type -> 0 -> 删除
     * type -> 1 -> 撤销删除
     * type -> 2 -> 彻底删除
     *
     * @param type
     * @param idList
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Map deleteUser(@RequestParam(value = "type") Integer type,
                          @RequestParam(value = "idList") List<Integer> idList) {
        //存放返回给前端数据的一个map
        Map resultMap = new HashMap(16);

        if (type < 0 || type > 2) {
            resultMap.put("msg", "删除类型错误！");
            return resultMap;
        }

        if (idList.size() <= 0) {
            resultMap.put("msg", "请选择要删除的反馈信息！");
            return resultMap;
        }

        //存放对数据库的操作方法的参数的值
        Map<String, Integer> parameterMap = new HashMap<>(16);
        Iterator iterator = null;
        //对删除的操作类型进下判断
        switch (type) {
            //删除操作，即将用户信息放进回收站
            case 0:
                //获取要删除的反馈信息的id列表，对其进行状态改变，即弄进回收站里面
                iterator = idList.iterator();
                while (iterator.hasNext()){
                    Integer studentId = (Integer)iterator.next();
                    parameterMap.put("studentId", studentId);
                    parameterMap.put("userType", 1);
                    int result = userService.updateUser(parameterMap);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
                break;
            //撤销删除操作
            case 1:
                //获取要撤销删除的用户信息的id列表，对其进行状态改变
                iterator = idList.iterator();
                while (iterator.hasNext()){
                    Integer studentId = (Integer)iterator.next();
                    parameterMap.put("studentId", studentId);
                    parameterMap.put("userType", 0);
                    int result = userService.updateUser(parameterMap);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
                break;
            //彻底删除
            case 2:
                //获取要撤销删除的反馈信息的id列表，对其进行状态改变
                iterator = idList.iterator();
                while (iterator.hasNext()){
                    Integer studentId = (Integer)iterator.next();
                    int result = userService.deleteUser(studentId);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
                break;
        }
        resultMap.put("msg", "删除成功!");
        return resultMap;
    }
}
