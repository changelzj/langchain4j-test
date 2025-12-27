package org.example.ai.tool;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Courses;
import org.example.entity.StudentReservation;
import org.example.mapper.CoursesMapper;
import org.example.mapper.StudentReservationMapper;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CourseTools {

    @Resource
    private CoursesMapper coursesMapper;

    @Resource
    private StudentReservationMapper studentReservationMapper;

    @Tool( """
          查询课程，返回：
          name：学科名称，
          edu：，学历背景要求：0-无，1-初中，2-高中，3-大专，4-本科以上，
          type：课程类型：编程、设计、自媒体、其它，
          price：课程价格，
          duration：学习时长，单位：天""")
    List<Courses> getCourse(@P(required = false, value = "课程类型：编程、设计、自媒体、其它") String type,
                            @P(required = false, value = "学历背景要求：0-无，1-初中，2-高中，3-大专，4-本科以上") Integer edu) {

        QueryWrapper<Courses> wrapper = new QueryWrapper<>();

        if (StringUtils.hasText(type)) {
            wrapper.lambda().eq(Courses::getType, type);
        }

        if (!Objects.isNull(edu) ) {
            wrapper.lambda().eq(Courses::getEdu, edu);
        }

        log.info("大模型查询查询课程 type={} edu={}", type, edu);

        return coursesMapper.selectList(wrapper);

    }


    @Tool("查询所有的校区")
    List<String> getSchoolArea() {
        return Arrays.asList("北京", "上海", "沈阳", "深圳", "西安", "乌鲁木齐", "武汉");
    }


    @Tool("保存预约学员的基本信息")
    public void reservation(@P("姓名") String name,
                            @P("性别：1-男，2-女") Integer gender,
                            @P("学历 0-无，1-初中，2-高中，3-大专，4-本科以上") Integer education,
                            @P("电话") String phone,
                            @P("邮箱") String email,
                            @P("毕业院校") String graduateSchool,
                            @P("所在地") String location,
                            @P("课程名称") String course,
                            @P("学员备注") String remark) {

        StudentReservation reservation = new StudentReservation();
        reservation.setCourse(course);
        reservation.setEmail(email);
        reservation.setGender(gender);
        reservation.setLocation(location);
        reservation.setGraduateSchool(graduateSchool);
        reservation.setPhone(phone);
        reservation.setEducation(education);
        reservation.setName(name);
        reservation.setRemark(remark);

        log.info("大模型保存预约数据 {}", reservation);

        studentReservationMapper.insert(reservation);
    }

}
