package com.ants.background.entity.feedback;

import lombok.Data;

/**
 * 对应数据库中ants_management_feedback的表
 *
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class Feedback {
    /** 主键 */
    private Integer fbId;
    /** 反馈编号 */
    private String fbSerial;
    /** 用户编号 */
    private Integer userId;
    /** 反馈内容 */
    private String fbContent;
    /** 反馈时间 */
    private String fbTime;
    /** 满意度:0:非常满意,1:满意,2:一般,3:不满意,4:非常不满意 */
    private Integer fbSatisfaction;
    /** 反馈人电话 */
    private String userMobile;
    /** 反馈人姓名 */
    private String userName;
    /** 判断反馈信息状态: 0 正常,1回收站( 1 删除,2 撤销删除, 3 彻底删除)*/
    private Integer state;

}
