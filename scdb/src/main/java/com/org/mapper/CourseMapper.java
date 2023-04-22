package com.org.mapper;

import com.org.entity.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface CourseMapper {

    @Select("select * from course where cname='${cname}'")
    Course selectCourseByCourseName(@Param("cname")String cname);

    //根据学分划分课程
    @Select("select \n" +
            "ccredit,\n" +
            "count(cno) total\n" +
            "from course\n" +
            "group by ccredit")
    List<Map<String,Object>> selectCourseGroupByCcredit();

    //查看课程总数
    @Select("select count(cno) from course")
    int courseCounts();

    //修改课程信息
    @Update("update course set cno='${cno}',cname='${cname}',ccredit=${ccredit},cpno=#{cpno} where cno='${courseNo}'")
    int updateCourseInfo(@Param("cno")String cno, @Param("cname")String cname,
                          @Param("ccredit")float ccredit,@Param("cpno")String cpno,
                          @Param("courseNo")String courseNo);

    //获取所有课程信息
    @Select("select * from course")
    List<Course> selectAllCourse();

    //添加课程信息
    @Insert("insert into course values('${cno}','${cname}',#{cpno},${ccredit})")
    int addCourse(Course course);

    //分页条件查询所有课程信息
    @Select("select t.tno,t.tname,c.cno,c.cname,c.ccredit,pre.cno pre_cno,pre.cname pre_cname,pre.ccredit pre_ccredit from course c\n" +
            "LEFT JOIN course pre on c.cpno=pre.cno\n" +
            "LEFT JOIN teacher t on t.cno1=c.cno or t.cno2=c.cno or t.cno3=c.cno where c.cname like '%${cname}%' limit ${currentPage},${pageSize}")

    List<Map<String,Object>> selectCourseByPageAndCondition(@Param("cname")String cname,@Param("currentPage")int currentPage,@Param("pageSize")int pageSize);

    //分页条件查询所有课程信息总量
    @Select("select count(c.cname) from course c\n" +
            "LEFT JOIN course pre on c.cpno=pre.cno\n" +
            "LEFT JOIN teacher t on t.cno1=c.cno or t.cno2=c.cno or t.cno3=c.cno where c.cname like '%${cname}%'")
    int allCourseCounts(@Param("cname")String cname);

    //获取所有课程信息用于excel导出
    @Select(" select t.tno,t.tname,c.cno,c.cname,c.ccredit,pre.cno pre_cno,pre.cname pre_cname,pre.ccredit pre_ccredit from course c\n" +
            "LEFT JOIN course pre on c.cpno=pre.cno\n" +
            "LEFT JOIN teacher t on t.cno1=c.cno or t.cno2=c.cno or t.cno3=c.cno")
    List<Map<String,Object>> selectAllCourses();

    //根据课程号获取课程信息
    @Select("select t.tno,t.tname,c.cno,c.cname,c.ccredit,pre.cno pre_cno,pre.cname pre_cname,pre.ccredit pre_ccredit from course c\n" +
            "LEFT JOIN course pre on c.cpno=pre.cno\n" +
            "LEFT JOIN teacher t on t.cno1=c.cno or t.cno2=c.cno or t.cno3=c.cno where c.cno='${cno}'")
    Map<String,Object> selectCourseByCno(@Param("cno")String cno);

    //删除单个课程信息
    @Delete("delete from course where cno='${cno}'")
    void deleteCourseByNo(@Param("cno")String cno);

    //批量删除课程信息
    @Delete({
            "<script>",
            "delete from course",
            "<where>",
            " cno in",
            "<foreach item='item' index='index' collection='cnoArrays'  open= '('   separator= ',' close= ')'>",
            "#{item}",
            "</foreach>",
            "</where>",
            "</script>"})
    void deleteMultipleCourse(@Param("cnoArrays")String[] cnoArrays);
}
