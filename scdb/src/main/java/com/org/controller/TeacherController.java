package com.org.controller;

import com.org.entity.Teacher;
import com.org.message.Msg;
import com.org.serviceImpl.TeacherServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
public class TeacherController {
    @Resource
    TeacherServiceImpl teacherServiceImpl;

    //教师职称男女比例
    @RequestMapping("/selectTeacherOrderByTpt")
    public Msg selectTeacherOrderByTpt(HttpServletRequest request, HttpServletResponse response){
        return teacherServiceImpl.selectTeacherOrderByTpt(request,response);
    }

    //教师学历男女比例
    @RequestMapping("/selectTeacherOrderByTeb")
    public Msg selectTeacherOrderByTeb(HttpServletRequest request,HttpServletResponse response){
        return teacherServiceImpl.selectTeacherOrderByTeb(request,response);
    }
    //查看教师数量
    @RequestMapping("/teacherCounts")
    public Msg teacherCounts(HttpServletRequest request,HttpServletResponse response){
        return teacherServiceImpl.teacherCounts(request,response);
    }

    //修改教师信息
    @RequestMapping("/updateTeacherInfo")
    public Msg updateTeacherInfo(@RequestParam("tno")String tno,@RequestParam("tname")String tname,
                                     @RequestParam("tsex")String tsex,@RequestParam("tage")int tage,
                                     @RequestParam("teb")String teb,@RequestParam("tpt")String tpt,
                                     @RequestParam(value="cno1",required = false)String cno1, @RequestParam(value = "cno2",required = false)String cno2,
                                     @RequestParam(value = "cno3",required = false)String cno3,@RequestParam("teacherNo")String teacherNo,
                                     HttpServletRequest request,HttpServletResponse response){
        return teacherServiceImpl.updateTeacherInfo(tno,tname,tage,tsex,teb,tpt,cno1,cno2,cno3,teacherNo,request,response);
    }

    //根据tno查询教师信息
    @RequestMapping("/selectTeacherBytno")
    public Msg selectTeacherBytno(@RequestParam("tno")String tno,HttpServletRequest request,
                                      HttpServletResponse response){
        return teacherServiceImpl.selectTeacherBytno(tno,request,response);
    }

    //获取所有教师信息
    @RequestMapping("/selectAllTeacher")
    public Msg selectAllTeacher(HttpServletRequest request,HttpServletResponse response){
        return teacherServiceImpl.selectAllTeacher(request,response);
    }

    //批量删除多个教师信息
    @RequestMapping("/deleteMultipleTeacher")
    public Msg deleteMultipleTeacher(@RequestParam("tnoArrays")String[] tnoArrays,HttpServletRequest request,
                                         HttpServletResponse response){
        return teacherServiceImpl.deleteMultipleTeacher(tnoArrays,request,response);
    }
    //删除单个教师信息
    @RequestMapping("/deleteTeacherByNo")
    public Msg deleteTeacherByNo(@RequestParam("tno")String tno,HttpServletRequest request,HttpServletResponse response){
        return teacherServiceImpl.deleteTeacherByNo(tno,request,response);
    }

    //条件分页查询
    @RequestMapping("/selectTeacherByPageAndCondition")
    public Msg selectTeacherByPageAndCondition(@RequestParam("condition")String condition,@RequestParam("currentPage")int currentPage,
                                                   @RequestParam("pageSize")int pageSize,HttpServletRequest request,HttpServletResponse response){
        return teacherServiceImpl.selectTeacherByPageAndCondition(condition,(currentPage-1)*pageSize,pageSize,request,response);
    }

    //添加教师信息
    @RequestMapping("/addTeacher")
    public Msg addTeacher(@RequestParam("tno")String tno, @RequestParam("tname")String tname,
                          @RequestParam("tsex")String tsex, @RequestParam("tage")int tage,
                          @RequestParam("teb")String teb, @RequestParam("tpt")String tpt,
                          @RequestParam(required = false,value="cno1")String cno1, @RequestParam(required = false,value="cno2")String cno2,
                          @RequestParam(required = false,value="cno3")String cno3, HttpServletRequest request, HttpServletResponse response){
        return teacherServiceImpl.addTeacher(tno,tname,tsex,tage,teb,tpt,cno1,cno2,cno3,request,response);
    }
}
