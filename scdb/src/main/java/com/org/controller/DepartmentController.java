package com.org.controller;

import com.org.entity.Department;
import com.org.message.Msg;
import com.org.serviceImpl.DepartmentServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
public class DepartmentController {
    @Resource
    DepartmentServiceImpl departmentServiceImpl;

    //修改院系信息
    @RequestMapping("/updateDepartmentInfo")
    public Msg updateDepartmentInfo(@RequestParam("dno")String dno, @RequestParam("dname")String dname, @RequestParam("dmanager")String dmanager,
                                    @RequestParam("departmentNo")String departmentNo, HttpServletRequest request, HttpServletResponse response){
        return departmentServiceImpl.updateDepartmentInfo(dno,dname,dmanager,departmentNo,request,response);
    }

    //根据dno查看院系详细信息
    @RequestMapping("/selectDepartmentByNo")
    public Msg selectDepartmentByNo(@RequestParam("dno")String dno,HttpServletRequest request,HttpServletResponse response){
        return departmentServiceImpl.selectDepartmentByNo(dno,request,response);
    }

    //查看学院总人数和男女比例
    @RequestMapping("/selectDepartmentRate")
    public Msg selectDepartmentRate(HttpServletRequest request,HttpServletResponse response){
        return departmentServiceImpl.selectDepartmentRate(request,response);
    }

    //学院总数
    @RequestMapping("/departmentCount")
    public Msg departmentCount(HttpServletRequest request,HttpServletResponse response){
        return departmentServiceImpl.departmentCount(request,response);
    }

    //查看所有学院信息
    @RequestMapping("/selectAllDepartment")
    public Msg selectAllDepartment(HttpServletRequest request,HttpServletResponse response){
        return departmentServiceImpl.selectAllDepartment(request,response);
    }
    //删除单个学院信息
    @RequestMapping("/deleteDepartment")
    public Msg deleteDepartment(@RequestParam("dno")String dno,HttpServletRequest request,
                                    HttpServletResponse response){
        return departmentServiceImpl.deleteDepartment(dno,request,response);
    }

    //删除多个学院信息
    @RequestMapping("/deleteMultipleDepartment")
    public Msg deleteMultipleDepartment(@RequestParam("dnoArrays")String[] dnoArrays,HttpServletRequest request,
                                            HttpServletResponse response){
        return departmentServiceImpl.deleteMultipleDepartment(dnoArrays,request,response);
    }
    //分页条件查询学院信息
    @RequestMapping("/selectDepartmentByPageAndCondition")
    public Msg selectDepartmentByPageAndCondition(@RequestParam("condition")String condition,@RequestParam("currentPage")int currentPage,
                                                      @RequestParam("pageSize")int pageSize,HttpServletRequest request,HttpServletResponse response){
        return departmentServiceImpl.selectDepartmentByPageAndCondition(condition,(currentPage-1)*pageSize,pageSize,request,response);
    }

    //添加学院信息
    @RequestMapping("/addDepartment")
    public Msg addDepartment(Department department, HttpServletRequest request, HttpServletResponse response){
        return departmentServiceImpl.addDepartment(department,request,response);
    }
}
