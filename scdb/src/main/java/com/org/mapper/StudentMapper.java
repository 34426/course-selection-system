package com.org.mapper;

import com.org.entity.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface StudentMapper {
    //根据学号修改身份证号
    @Update("update student set sid='${sid}' where sno='${sno}'")
    int changeSidBySno(@Param("sid")String sid,@Param("sno")String sno);

    //根据身份证判断用户存不存在
    @Select("select * from student where sid='${sid}'")
    Student JudgeStudentIsExistBySid(@Param("sid")String sid);

    //根据学号判断用户存不存在
    @Select("select * from student where sno='${sno}'")
    Map<String,Object> JudgeStudentIsExistBySno(@Param("sno")String sno);

    //找回密码
    @Update("update student set spassword='${12345}' where sno='${sno}' and sid='${sid}'")
    int findPassword(@Param("sno")String sno,@Param("sid")String sid);

    //根据学号查看个人详细信息
    @Select("select * from student where sno='${sno}'")
    Map<String,Object> selectStudentInfoBySno(@Param("sno")String sno);

    //根据旧密码修改新密码
    @Update("update student set spassword='${newPassword}' where spassword='${previewPassword}' and sno='${sno}' ")
    int updatePasswordByPreviewPassword(@Param("newPassword")String newPassword,@Param("previewPassword")String previewPassword,@Param("sno")String sno);

     //统计学生信息
    @Select("SELECT count(sname) as total,SUM(CASE WHEN ssex = '男' THEN 1 ELSE 0 END) AS boy,SUM(CASE WHEN ssex = '女' THEN 1 ELSE 0 END) AS girl FROM student ")
    Map<String,Integer> studentStatistic();

    //修改学生信息
    @Update("update student set sno='${sno}',spassword='${spassword}',sname='${sname}',ssex='${ssex}',sage=${sage},sdept='${sdept}',sid='${sid}',power='${power}' where sno='${studentNo}'")
    int updateInfo(@Param("sno")String sno,@Param("spassword")String spassword,@Param("sname")String sname,
                    @Param("ssex")String ssex,@Param("sage")int sage,@Param("sdept")String sdept,@Param("sid")String sid,@Param("power")String power,@Param("studentNo")String studentNo);
    //根据学号查询学生
    @Select("select * from student where sno='${sno}'")
    Map<String,Object> selectStudentByNo(@Param("sno")String sno);

    //根据token查询学生
    @Select("select * from student where token='${token}'")
    Map<String,Object> selectStudentByToken(@Param("token")String token);

    //根据学号更新学生token
    @Update("update student set token='${token}' where sno='${sno}'")
    void updateTokenByNo(@Param("token")String token,@Param("sno")String sno);

    //根据学号判断用户是否初次登录
    @Select("select token from student where sno='${sno}'")
    String tokenIsExist(@Param("sno")String sno);

    //登录
    @Select("select * from student where sno='${sno}' and spassword='${spassword}'")
    Student login(@Param("sno")String sno,@Param("spassword")String spassword);

    //修改学生信息
    @Update("update student set sno='${sno}',sname='${sname}',ssex='${ssex}',sage=${sage},sdept='${sdept}' where sno='${sno}'")
    void updateStudent();

    //添加学生
    @Insert("insert into student values('${sno}','${spassword}','${sname}','${ssex}',${sage},'${sdept}','${sid}','${power}')")
    void addStudent(Student student);

    //批量添加学生
    @Insert({
            "<script>",
            "insert into student values",
            "<foreach item='item' index='index' collection='students' separator= ',' >",
            "(#{item.sno},#{item.spassword},#{item.sname},#{item.ssex},#{item.sage},#{item.sdept},#{item.sid},#{item.power})",
            "</foreach>",
            "</script>"})
    int addMultipleStudent(@Param("students")List<Student> students);

    //分页条件查看所有学生信息
    @Select("select * from student where sname like '%${sname}%' limit ${currentPage},${pageSize}")
    List<Student> selectStudentByConditionAndPage(@Param("sname")String sname, @Param("currentPage")int currentPage, @Param("pageSize")int pageSize);

    //分页条件查看所有学生信息的总量
    @Select("select count(*) from student where sname like '%${sname}%'")
    int allStudentCounts(@Param("sname")String sname);

    //查看所有学生信息
    @Select("select * from student")
    List<Student> selectAllStudents();

    //删除单个学生信息
    @Delete("delete from student where sno='${sno}'")
    void deleteStudentByNo(@Param("sno")String sno);

    //批量删除学生信息
    @Delete({
            "<script>",
            "delete from student",
            "<where>",
            " sno in",
            "<foreach item='item' index='index' collection='snoArrays'  open= '('   separator= ',' close= ')'>",
            "#{item}",
            "</foreach>",
            "</where>",
            "</script>"})
    void deleteMultipleStudent(@Param("snoArrays")String[] snoArrays);
}
