package com.org.mapper;

import com.org.entity.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface TeacherMapper {

    //教师职称男女比例
    @Select("select \n" +
            "tpt,\n" +
            "SUM(CASE WHEN tsex = '男' THEN 1 ELSE 0 END) AS man,\n" +
            "SUM(CASE WHEN tsex = '女' THEN 1 ELSE 0 END) AS woman\n" +
            "from teacher\n" +
            "group by tpt")
    List<Map<String,Object>> selectTeacherOrderByTpt();

    //教师学历男女比例
    @Select("select \n" +
            "teb,\n" +
            "SUM(CASE WHEN tsex = '男' THEN 1 ELSE 0 END) AS man,\n" +
            "SUM(CASE WHEN tsex = '女' THEN 1 ELSE 0 END) AS woman\n" +
            "from teacher\n" +
            "group by teb")
    List<Map<String,Object>> selectTeacherOrderByTeb();

    //教师总数显示以及男女教师人数显示
    @Select("select \n" +
            "SUM(CASE WHEN tsex = '男' THEN 1 ELSE 0 END) AS man,\n" +
            "SUM(CASE WHEN tsex = '女' THEN 1 ELSE 0 END) AS woman,\n" +
            "count(1) as total\n" +
            "from teacher\n")
    Map<String,Object> teacherCounts();

    //修改教师信息
    @Update("update teacher set tno='${tno}',tname='${tname}',tage=${tage},tsex='${tsex}',teb='${teb}',tpt='${tpt}',cno1=#{cno1},cno2=#{cno2},cno3=#{cno3} where tno='${teacherNo}'")
    void updateTeacherInfo(@Param("tno")String tno,@Param("tname")String tname,@Param("tage")int tage,
                           @Param("tsex")String tsex,@Param("teb")String teb,@Param("tpt")String tpt,
                           @Param("cno1")String cno1, @Param("cno2")String cno2, @Param("cno3")String cno3,
                           @Param("teacherNo")String teacherNo);
    //根据tno查询教师信息
    @Select("select t.*,c1.cname c1_name,c2.cname c2_name,c3.cname c3_name from teacher t\n" +
            "LEFT JOIN course c1 on c1.cno=t.cno1\n" +
            "LEFT JOIN course c2 on c2.cno=t.cno2\n" +
            "LEFT JOIN course c3 on c3.cno=t.cno3 where t.tno='${tno}'")
    Map<String,Object> selectTeacherBytno(@Param("tno")String tno);

    //批量删除教师信息
    @Delete({
            "<script>",
            "delete from teacher",
            "<where>",
            " tno in",
            "<foreach item='item' index='index' collection='tnoArrays'  open= '('   separator= ',' close= ')'>",
            "#{item}",
            "</foreach>",
            "</where>",
            "</script>"})
    void deleteMultipleTeacher(@Param("tnoArrays")String[] tnoArrays);

    //删除单个教师信息
    @Delete("delete from teacher where tno='${tno}'")
    void deleteTeacherByNo(@Param("tno")String tno);

    //添加教师信息
    @Insert("insert into teacher values('${tno}','${tname}','${tsex}',${tage},'${teb}','${tpt}',#{cno1},#{cno2},#{cno3})")
    void addTeacher(Teacher teacher);

    //分页条件查询
    @Select("select t.tno,t.tage,t.teb,t.tname,t.tpt,t.tsex,c1.cname c1_name,c2.cname c2_name,c3.cname c3_name from teacher t\n" +
            "LEFT JOIN course c1 on c1.cno=t.cno1\n" +
            "LEFT JOIN course c2 on c2.cno=t.cno2\n" +
            "LEFT JOIN course c3 on c3.cno=t.cno3 where t.tname like '%${tname}%' limit ${currentPage},${pageSize}")
    List<Map<String,Object>> selectTeacherByPageAndCondition(@Param("tname")String tname, @Param("currentPage")int currentPage, @Param("pageSize")int pageSize);

    //分页条件查询总量
    @Select("select count(t.tno) from teacher t\n" +
            "LEFT JOIN course c1 on c1.cno=t.cno1\n" +
            "LEFT JOIN course c2 on c2.cno=t.cno2\n" +
            "LEFT JOIN course c3 on c3.cno=t.cno3 where t.tname like '%${tname}%'")
    int allTeacherCounts(@Param("tname")String tname);

    //获取所有教师信息
    @Select("select t.tno,t.tage,t.teb,t.tname,t.tpt,t.tsex,c1.cname c1_name,c2.cname c2_name,c3.cname c3_name from teacher t\n" +
            "LEFT JOIN course c1 on c1.cno=t.cno1\n" +
            "LEFT JOIN course c2 on c2.cno=t.cno2\n" +
            "LEFT JOIN course c3 on c3.cno=t.cno3")
    List<Map<String,Object>> selectAllTeacher();
}
