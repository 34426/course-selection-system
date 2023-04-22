package com.org.controller;

import com.org.entity.Course;
import com.org.message.Msg;
import com.org.serviceImpl.CourseServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
public class CourseController {
    @Resource
    CourseServiceImpl courseServiceImpl;

    @RequestMapping("/test")
    public String test(){
        return "测试成功";
    }

    //根据学分划分课程
    @RequestMapping("/selectCourseGroupByCcredit")
    public Msg selectCourseGroupByCcredit(HttpServletRequest request, HttpServletResponse response){
        return courseServiceImpl.selectCourseGroupByCcredit(request,response);
    }

    //查看课程总量
    @RequestMapping("/courseCounts")
    public Msg courseCounts(HttpServletRequest request,HttpServletResponse response){
        return courseServiceImpl.courseCounts(request,response);
    }

    //修改课程信息
    @RequestMapping("/updateCourseInfo")
    public Msg updateCourseInfo(@RequestParam("cno")String cno,@RequestParam("cname")String cname,
                                    @RequestParam("ccredit")float ccredit,@RequestParam(value="pre_cno",required = false)String pre_cno,
                                    @RequestParam("courseNo")String courseNo,HttpServletRequest request,HttpServletResponse response){
        return courseServiceImpl.updateCourseInfo(cno,cname,ccredit,pre_cno,courseNo,request,response);
    }

    //根据课程号获取课程信息
    @RequestMapping("/selectCourseByCno")
    public Msg selectCourseByCno(@RequestParam("cno")String cno,HttpServletRequest request,HttpServletResponse response){
        return courseServiceImpl.selectCourseByCno(cno,request,response);
    }

    //获取所有课程信息用于excel的导出
    @RequestMapping("/selectAllCourses")
    public Msg selectAllCourses(HttpServletRequest request,HttpServletResponse response){
        return courseServiceImpl.selectAllCourses(request,response);
    }
    //获取所有课程信息
    @RequestMapping("/selectAllCourse")
    public Msg selectAllCourse(HttpServletRequest request,HttpServletResponse response){
        return courseServiceImpl.selectAllCourse(request,response);
    }

    //批量删除多个课程信息
    @RequestMapping("/deleteMultipleCourse")
    public Msg deleteMultipleCourse(@RequestParam("cnoArrays")String[] cnoArrays,HttpServletRequest request,HttpServletResponse response){
        return courseServiceImpl.deleteMultipleCourse(cnoArrays,request,response);
    }
    //删除单个课程信息
    @RequestMapping("/deleteCourseByNo")
    public Msg deleteCourseByNo(@RequestParam("cno")String cno,HttpServletRequest request,HttpServletResponse response){
        return courseServiceImpl.deleteCourseByNo(cno,request,response);
    }
    //分页条件查询所有课程信息
    @RequestMapping("/selectCourseByPageAndCondition")
    public Msg selectCourseByPageAndCondition(@RequestParam("condition")String condition,@RequestParam("currentPage")int currentPage,
                                                  @RequestParam("pageSize")int pageSize,HttpServletRequest request,HttpServletResponse response){
        return courseServiceImpl.selectCourseByPageAndCondition(condition,(currentPage-1)*pageSize,pageSize,request,response);
    }

    //添加课程信息
    @RequestMapping("/addCourse")
    public Msg addCourse(Course course, HttpServletRequest request, HttpServletResponse response){

        return courseServiceImpl.addCourse(course,request,response);
    }
}
