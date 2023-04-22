package com.org.serviceImpl;

import com.google.gson.Gson;
import com.org.entity.Sct;
import com.org.mapper.SctMapper;
import com.org.message.Msg;
import com.org.utils.RedisUtil;
import com.org.utils.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class SctServiceImpl {

    @Resource
    SctMapper sctMapper;

    @Resource
    RedisUtil redisUtil;

    //根据学号分页条件查看我的选课信息,用于excel的导出
    public Msg selectAllSctByNo(String sno, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "查看成功").add("data", sctMapper.selectAllSctByNo(sno));
    }

    //根据学号分页条件查看我的选课信息
    public Msg selectSctByPageAndConditionAndSno(String sno, String condition, int currentPage, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success()
                .add("detailMessage", "查看成功")
                .add("data", sctMapper.selectSctByPageAndConditionAndSno(condition, sno, currentPage, pageSize))
                .add("total", sctMapper.allSctCountsBySno(sno, condition));
    }

    //每门课程多少学生修,用于导出excel
    public Msg studyCourseFromStudentToExcel(HttpServletRequest request, HttpServletResponse response){
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "查看成功").add("data", sctMapper.studyCourseFromStudentToExcel());
    }

    //每个学生修了多少门课，用于导出excel的数据
    public Msg studentStudyCourseToExcel(HttpServletRequest request, HttpServletResponse response){
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "查看成功").add("data", sctMapper.studentStudyCourseToExcel());
    }

    //每个学生修了多少门课
    public Msg studentStudyCourse(String condition,int currentPage, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success()
                .add("detailMessage", "查询成功")//sctMapper.studentStudyCourseCounts().size()
                .add("total",sctMapper.studentStudyCourseCounts(condition).size())
                .add("data", sctMapper.studentStudyCourse(condition,currentPage, pageSize));
    }

    //每门课程多少学生修
    public Msg studyCourseFromStudent(String condition,int currentPage, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success()
                .add("detailMessage", "查询成功")
                .add("total",sctMapper.courseFromStudentCounts(condition).size())//sctMapper.courseFromStudentCounts().size()
                .add("data", sctMapper.studyCourseFromStudent(condition,currentPage, pageSize));//sctMapper.studyCourseFromStudent(currentPage, pageSize)
    }

    //修改选课信息的成绩
    public Msg changeGradeByNos(float grade, String sno, String cno, String tno, HttpServletRequest request, HttpServletResponse response) {
        sctMapper.changeGradeByNos(grade, sno, cno, tno);
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "修改成功");
    }

    //根据学号、工号、课程号查看选课信息详情
    public Msg selectSctByNos(String sno, String cno, String tno, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success().add("data", sctMapper.selectSctByNos(sno, cno, tno));
    }

    //查看所有选课信息
    public Msg selectAllSct(HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success().add("data", sctMapper.selectAllSct());
    }

    //删除多个选课信息
    public Msg deleteMultipleSct(List<Map<String, Object>> sctArrays, HttpServletRequest request, HttpServletResponse response) {
        sctMapper.deleteMultipleSct(sctArrays);
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "删除成功");
    }

    //删除单个选课信息
    public Msg deleteSctBySno(String sno, String cno, String tno, HttpServletRequest request, HttpServletResponse response) {
        sctMapper.deleteSctBySno(sno, cno, tno);
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "删除成功");
    }

    //添加选课信息
    public Msg addSct(Sct sct, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        if (sctMapper.judgeIsStudyByNo(sct.getSno(), sct.getTno(), sct.getCno()) != null) {
            //该门课已选
            return Msg.fail().add("detailMessage", "你已选该门课");
        } else {
            //该门课未选择
            sctMapper.addSct(sct);
            return Msg.success().add("detailMessage","选课成功");
        }
    }

    //分页条件查看所有选课信息
    public Msg selectSctByPageAndCondition(String condition, int currentPage, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success()
                .add("data",sctMapper.selectSctByPageAndCondition(condition, currentPage, pageSize))
                .add("total",sctMapper.allSctCounts(condition));
    }
}
