package com.org.mapper;

import com.org.entity.Sct;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface SctMapper {


    //根据sno判断是否修了这门课程
    @Select("select * from sct where sno='${sno}' and tno='${tno}' and cno='${cno}'")
    Sct judgeIsStudyByNo(@Param("sno")String sno,@Param("tno")String tno,@Param("cno")String cno);


    //每个学生修了多少门课，用于导出excel的数据
    @Select("select\n" +
            "stu.sdept,\n" +
            "stu.sname,  \n" +
            "count(s.sno) as total_course,\n" +
            "sum(c.ccredit) total_ccredit\n" +
            "from sct s \n" +
            "INNER JOIN student stu on stu.sno=s.sno\n" +
            "INNER JOIN course c on c.cno=s.cno\n" +
            "group by s.sno \n")
    List<Map<String,Object>> studentStudyCourseToExcel();

    //每个学生修了多少门课
    @Select("select\n" +
            "stu.sdept,\n" +
            "stu.sname,  \n" +
            "count(s.sno) as total_course,\n" +
            "sum(c.ccredit) total_ccredit\n" +
            "from sct s \n" +
            "INNER JOIN student stu on stu.sno=s.sno\n" +
            "INNER JOIN course c on c.cno=s.cno\n" +
            "where stu.sname like '%${sname}%' group by s.sno limit ${currentPage},${pageSize}\n")
    List<Map<String,Object>> studentStudyCourse(@Param("sname")String sname,@Param("currentPage")int currentPage,@Param("pageSize")int pageSize);

    //每个学生修了多少门课总量
    @Select("select\n" +
            "stu.sdept,\n" +
                    "stu.sname,  \n" +
                    "count(s.sno) as total_course,\n" +
                    "sum(c.ccredit) total_ccredit\n" +
                    "from sct s \n" +
                    "INNER JOIN student stu on stu.sno=s.sno\n" +
                    "INNER JOIN course c on c.cno=s.cno\n" +
                    "where stu.sname like '%${sname}%' group by s.sno\n")
    List<Map<String,Object>> studentStudyCourseCounts(@Param("sname")String sname);

    //每门课程多少学生修,用于导出excel
    @Select("select t.tname,c.cname,c.ccredit,count(s.sno) total from sct s\n" +
            "INNER JOIN course c on c.cno=s.cno\n" +
            "INNER JOIN teacher t on t.cno1=s.cno or t.cno2=s.cno or t.cno3=s.cno\n" +
            "group by s.cno ")
    List<Map<String,Object>> studyCourseFromStudentToExcel();

    //每门课程多少学生修
    @Select("select c.cno,t.tname,c.cname,c.ccredit,count(s.sno) total from sct s\n" +
            "INNER JOIN course c on c.cno=s.cno\n" +
            "INNER JOIN teacher t on t.cno1=s.cno or t.cno2=s.cno or t.cno3=s.cno\n" +
            "where c.cname like '%${cname}%' group by s.cno order by total desc limit ${currentPage},${pageSize} ")
    List<Map<String,Object>> studyCourseFromStudent(@Param("cname")String cname,@Param("currentPage")int currentPage,@Param("pageSize")int pageSize);

    //每门课程多少学生修的总数
    @Select("select t.tname,c.cname,c.ccredit,count(s.sno) total from sct s\n" +
            "INNER JOIN course c on c.cno=s.cno\n" +
            "INNER JOIN teacher t on t.cno1=s.cno or t.cno2=s.cno or t.cno3=s.cno\n" +
            "where c.cname like '%${cname}%' group by s.cno ")
    List<Map<String,Object>> courseFromStudentCounts(@Param("cname")String cname);

    //修改选课成绩
    @Update("update sct set grade=${grade} where sno='${sno}' and cno='${cno}' and tno='${tno}'")
    void changeGradeByNos(@Param("grade")float grade,@Param("sno")String sno,@Param("cno")String cno,@Param("tno")String tno);

    //删除多个选课信息
    @Delete({
            "<script>",
            "delete from sct",
            "<where>",
            "sno in",
            "<foreach item='item' index='index' collection='sctArrays'  open= '('   separator= ',' close= ')'>",
                "#{item.sno}",
            "</foreach>",
            "and tno in",
            "<foreach item='item' index='index' collection='sctArrays'  open= '('   separator= ',' close= ')'>",
                "#{item.tno}",
            "</foreach>",
            "and cno in",
            "<foreach item='item' index='index' collection='sctArrays'  open= '('   separator= ',' close= ')'>",
                "#{item.cno}",
            "</foreach>",
            "</where>",
            "</script>"
    })
    void deleteMultipleSct(@Param("sctArrays")List<Map<String,Object>> sctArrays);

    //删除单个选课信息
    @Delete("delete from sct where sno='${sno}' and cno='${cno}' and tno='${tno}'")
    void deleteSctBySno(@Param("sno")String sno,@Param("cno")String cno,@Param("tno")String tno);

    //添加选课信息
    @Insert("insert into sct values('${sno}','${cno}','${tno}',default)")
    void addSct(Sct sct);

    //根据学号分页条件查看我的选课信息
    @Select("select s.sname,t.tname,c.cno,c.cname,c.ccredit,xuan.* from sct xuan \n" +
            "INNER join student s on s.sno=xuan.sno\n" +
            "INNER JOIN teacher t on t.tno=xuan.tno\n" +
            "INNER JOIN course c on c.cno=xuan.cno\n" +
            "where c.cname like '%${condition}%' and s.sno='${sno}' limit ${currentPage},${pageSize}")
    List<Map<String,Object>> selectSctByPageAndConditionAndSno(@Param("condition")String condition,@Param("sno")String sno,@Param("currentPage")int currentPage,@Param("pageSize")int pageSize);

    //学号分页条件查看我的选课信息总量
    @Select("select count(xuan.sno) from sct xuan \n" +
            "INNER join student s on s.sno=xuan.sno\n" +
            "INNER JOIN teacher t on t.tno=xuan.tno\n" +
            "INNER JOIN course c on c.cno=xuan.cno\n" +
            "where s.sno='${sno}' and c.cname like '%${condition}%'")
    int allSctCountsBySno(@Param("sno")String sno,@Param("condition")String condition);

    //根据学号分页条件查看我的选课信息,用于excel的导出
    @Select("select s.sname,t.tname,c.cno,c.cname,c.ccredit,xuan.* from sct xuan \n" +
            "INNER join student s on s.sno=xuan.sno\n" +
            "INNER JOIN teacher t on t.tno=xuan.tno\n" +
            "INNER JOIN course c on c.cno=xuan.cno\n" +
            "where s.sno='${sno}'")
    List<Map<String,Object>> selectAllSctByNo(@Param("sno")String sno);

    //分页条件查看所有选课信息
    @Select("select s.sname,t.tname,c.cno,c.cname,c.ccredit,xuan.* from sct xuan \n" +
            "INNER join student s on s.sno=xuan.sno\n" +
            "INNER JOIN teacher t on t.tno=xuan.tno\n" +
            "INNER JOIN course c on c.cno=xuan.cno\n" +
            "where s.sname like '%${sname}%' limit ${currentPage},${pageSize}")
    List<Map<String,Object>> selectSctByPageAndCondition(@Param("sname")String sname,@Param("currentPage")int currentPage,@Param("pageSize")int pageSize);

    //分页条件查看所有选课信息总量
    @Select("select count(xuan.sno) from sct xuan \n" +
            "INNER join student s on s.sno=xuan.sno\n" +
            "INNER JOIN teacher t on t.tno=xuan.tno\n" +
            "INNER JOIN course c on c.cno=xuan.cno\n" +
            "where s.sname like '%${sname}%'")
    int allSctCounts(@Param("sname")String sname);

    //查看所有选课信息，用于excel的导出
    @Select("select s.sname,t.tname,c.cno,c.cname,c.ccredit,xuan.* from sct xuan \n" +
            "INNER join student s on s.sno=xuan.sno\n" +
            "INNER JOIN teacher t on t.tno=xuan.tno\n" +
            "INNER JOIN course c on c.cno=xuan.cno\n")
    List<Map<String,Object>> selectAllSct();

    //根据学号、工号、课程号查看选课信息详情
    @Select("select s.sname,t.tname,c.cno,c.cname,c.ccredit,xuan.* from sct xuan \n" +
            "INNER join student s on s.sno=xuan.sno\n" +
            "INNER JOIN teacher t on t.tno=xuan.tno\n" +
            "INNER JOIN course c on c.cno=xuan.cno\n"+
            "where s.sno='${sno}' and c.cno='${cno}' and t.tno='${tno}'")
    Map<String,Object> selectSctByNos(@Param("sno")String sno,@Param("cno")String cno,@Param("tno")String tno);
}
