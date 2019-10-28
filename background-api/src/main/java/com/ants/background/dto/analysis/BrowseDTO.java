package com.ants.background.dto.analysis;

import lombok.Data;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class BrowseDTO {
    /** 日期 */
    private String date;
    /** 学生访问次数 */
    private Integer studentNumber;
    /** 教师访问次数 */
    private Integer teacherNumber;
    /** 游客访问次数 */
    private Integer visitorNumber;
}
