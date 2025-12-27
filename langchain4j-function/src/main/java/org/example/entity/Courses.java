package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("courses")
@Data
public class Courses {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学科名称
     */
    private String name;

    /**
     * 学历背景要求：0-无，1-初中，2-高中，3-大专，4-本科以上
     */
    private Integer edu;

    /**
     * 课程类型：编程、设计、自媒体、其它
     */
    private String type;

    /**
     * 课程价格
     */
    private Long price;

    /**
     * 学习时长，单位：天
     */
    private Integer duration;
}
