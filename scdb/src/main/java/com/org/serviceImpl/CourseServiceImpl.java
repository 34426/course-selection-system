package com.org.serviceImpl;

import com.google.gson.Gson;
import com.org.entity.Course;
import com.org.mapper.CourseMapper;
import com.org.mapper.StudentMapper;
import com.org.message.Msg;
import com.org.utils.RedisUtil;
import com.org.utils.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CourseServiceImpl {
    @Resource
    CourseMapper courseMapper;

    @Resource
    RedisUtil redisUtil;

    Gson gson=new Gson();
    //根据学分划分课程
    public Msg selectCourseGroupByCcredit(HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);//courseMapper.selectCourseGroupByCcredit()
        return Msg.success().add("detailMessage", "查看成功").add("data",redisUtil.getStringValue("selectCourseGroupByCcredit",gson.toJson(courseMapper.selectCourseGroupByCcredit())));
    }

    //查看课程总量
    public Msg courseCounts(HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);//courseMapper.courseCounts()
       // return Msg.success().add("detailMessage", "查看成功").add("total",redisUtil.getStringValue("courseCounts",courseMapper.courseCounts(),60));
         return Msg.success().add("detailMessage", "查看成功").add("total",redisUtil.getStringValue("courseCounts",gson.toJson(courseMapper.courseCounts())));
    }

    //修改课程信息
    public Msg updateCourseInfo(String cno, String cname, float ccredit, String cpno, String courseNo, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        try {
            //修改成功
            courseMapper.updateCourseInfo(cno, cname, ccredit, cpno, courseNo);
            return Msg.success().add("detailMessage", "修改成功");
        } catch (Exception e) {
            //修改失败
            return Msg.fail().add("detailMessage", "课程号已存在");
        }
    }

    //根据课程号获取课程信息
    public Msg selectCourseByCno(String cno, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "查询成功").add("data", courseMapper.selectCourseByCno(cno));
    }

    //获取所有课程信息用于excel的导出
    public Msg selectAllCourses(HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "查询成功").add("data", courseMapper.selectAllCourses());
    }

    //获取所有课程信息
    public Msg selectAllCourse(HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "查询成功").add("data", courseMapper.selectAllCourse());
    }

    //添加课程信息
    public Msg addCourse(Course course, HttpServletRequest request, HttpServletResponse response) {

        TokenUtil.addToken(request, response);
        try {
            courseMapper.addCourse(course);
            return Msg.success().add("detailMessage", "录入课程信息成功");
        } catch (Exception e) {
            //添加失败
            return Msg.fail().add("detailMessage", "课程号已存在，请更换课程号");
        }
    }

    //分页条件查询所有课程信息
    public Msg selectCourseByPageAndCondition(String condition, int currentPage, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "查询成功")
                .add("data", courseMapper.selectCourseByPageAndCondition(condition, currentPage, pageSize))
                .add("total", courseMapper.allCourseCounts(condition));
    }

    //删除单个课程信息
    public Msg deleteCourseByNo(String cno, HttpServletRequest request, HttpServletResponse response) {
        courseMapper.deleteCourseByNo(cno);
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "删除成功");
    }

    //批量删除多个课程信息
    public Msg deleteMultipleCourse(String[] cnoArrays, HttpServletRequest request, HttpServletResponse response) {
        courseMapper.deleteMultipleCourse(cnoArrays);
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "删除成功");
    }
}
