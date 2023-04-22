package com.org.controller;

import com.google.gson.Gson;
import com.org.entity.Sct;
import com.org.message.Msg;
import com.org.serviceImpl.SctServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
public class SctController {
    @Resource
    SctServiceImpl sctServiceImpl;
    Gson gson=new Gson();

    //每门课程多少学生修,用于导出excel
    @RequestMapping("/studyCourseFromStudentToExcel")
    public Msg studyCourseFromStudentToExcel(HttpServletRequest request,HttpServletResponse response){
        return sctServiceImpl.studyCourseFromStudentToExcel(request,response);
    }

    //每个学生修了多少门课，用于导出excel的数据
    @RequestMapping("/studentStudyCourseToExcel")
    public Msg studentStudyCourseToExcel(HttpServletRequest request,HttpServletResponse response){
        return sctServiceImpl.studentStudyCourseToExcel(request,response);
    }

    //根据学号分页条件查看我的选课信息,用于excel的导出
    @RequestMapping("/selectAllSctByNo")
    public Msg selectAllSctByNo(@RequestParam("sno")String sno, HttpServletRequest request,
                                HttpServletResponse response){
        return sctServiceImpl.selectAllSctByNo(sno,request,response);
    }

    //根据学号分页条件查看我的选课信息
    @RequestMapping("/selectSctByPageAndConditionAndSno")
    public Msg selectSctByPageAndConditionAndSno(@RequestParam("sno")String sno,@RequestParam("condition")String condition,
                                                     @RequestParam("currentPage")int currentPage,@RequestParam("pageSize")int pageSize,
                                                     HttpServletRequest request,HttpServletResponse response){
        return sctServiceImpl.selectSctByPageAndConditionAndSno(sno,condition,(currentPage-1)*pageSize,pageSize,request,response);
    }
    //每个学生修了多少门课
    @RequestMapping("/studentStudyCourse")
    public Msg studentStudyCourse(String condition,int currentPage,int pageSize,
                                      HttpServletRequest request,HttpServletResponse response){
        return sctServiceImpl.studentStudyCourse(condition,(currentPage-1)*pageSize,pageSize,request,response);
    }
    // 每门课程多少学生修
    @RequestMapping("/studyCourseFromStudent")
    public Msg studyCourseFromStudent(String condition,int currentPage,int pageSize,HttpServletRequest request,HttpServletResponse response){
        return sctServiceImpl.studyCourseFromStudent(condition,(currentPage-1)*pageSize,pageSize,request,response);
    }

    //修改选课信息的成绩
    @RequestMapping("/changeGradeByNos")
    public Msg changeGradeByNos(@RequestParam("grade")float grade,@RequestParam("sno")String sno,@RequestParam("cno")String cno,
                                    @RequestParam("tno")String tno,HttpServletRequest request,HttpServletResponse response){
        return sctServiceImpl.changeGradeByNos(grade,sno,cno,tno,request,response);
    }

    //根据学号、工号、课程号查看选课信息详情
    @RequestMapping("/selectSctByNos")
    public Msg selectSctByNos(@RequestParam("sno")String sno,@RequestParam("cno")String cno,@RequestParam("tno")String tno,
                                  HttpServletRequest request,HttpServletResponse response){
        return sctServiceImpl.selectSctByNos(sno,cno,tno,request,response);
    }

    //查看所有选课信息
    @RequestMapping("/selectAllSct")
    public Msg selectAllSct(HttpServletRequest request,HttpServletResponse response){
        return sctServiceImpl.selectAllSct(request,response);
    }

    //删除多个选课信息
    @RequestMapping("/deleteMultipleSct")
    public Msg deleteMultipleSct(@RequestBody List<Map<String,Object>> sctArrays, HttpServletRequest request, HttpServletResponse response){
        return sctServiceImpl.deleteMultipleSct(sctArrays,request,response);
    }

    //删除单个选课信息
    @RequestMapping("/deleteSctBySno")
    public Msg deleteSctBySno(@RequestParam("sno")String sno,@RequestParam("cno")String cno,
                                  @RequestParam("tno")String tno,HttpServletRequest request,HttpServletResponse response){
        return sctServiceImpl.deleteSctBySno(sno,cno,tno,request,response);
    }

    //分页条件查看所有选课信息
    @RequestMapping("/selectSctByPageAndCondition")
    public Msg selectSctByPageAndCondition(@RequestParam("condition")String condition,@RequestParam("currentPage")int currentPage,
                                               @RequestParam("pageSize")int pageSize,HttpServletRequest request,HttpServletResponse response){
        return sctServiceImpl.selectSctByPageAndCondition(condition,(currentPage-1)*pageSize,pageSize,request,response);
    }

    //添加选课信息
    @RequestMapping("/addSct")
    public Msg addSct(Sct sct, HttpServletRequest request,
                      HttpServletResponse response){
        return sctServiceImpl.addSct(sct,request,response);
    }
}
