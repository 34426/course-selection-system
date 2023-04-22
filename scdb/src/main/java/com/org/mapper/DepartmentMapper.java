package com.org.mapper;

import com.org.entity.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface DepartmentMapper {
    //根据dname查看院系详细信息
    @Select("select * from department where dname='${dname}'")
    Department selectDepartmentByDname(@Param("dname")String dname);

    //修改院系信息
    @Update("update department set dno='${dno}',dname='${dname}',dmanager='${dmanager}' where dno='${departmentNo}'")
    void updateDepartmentInfo(@Param("dno")String dno,@Param("dname")String dname,@Param("dmanager")String dmanager,@Param("departmentNo")String departmentNo);

    //根据dno查看院系详细信息
    @Select("select * from department where dno='${dno}'")
    Department selectDepartmentByNo(@Param("dno")String dno);

    //查看学院总人数和男女比例
    @Select("SELECT\n" +
            "\tsdept as department,\n" +
            "\tSUM(CASE WHEN ssex = '男' THEN 1 ELSE 0 END) AS boy,\n" +
            "\tSUM(CASE WHEN ssex ='女' THEN 1 ELSE 0 END) AS girl,\n" +
            "\tcount(1) as total\n" +
            "FROM\n" +
            "\tstudent\n" +
            "GROUP BY\n" +
            "sdept\n" +
            "\n")
    List<Map<String,Object>> selectDepartmentRate();

    //学院总数
    @Select("select count(dno) from department")
    int departmentCount();

    //删除单个学院信息
    @Delete("delete from department where dno='${dno}'")
    void deleteDepartment(@Param("dno")String dno);

    //批量删除学院信息
    @Delete({
            "<script>",
            "delete from department",
            "<where>",
            " dno in",
            "<foreach item='item' index='index' collection='dnoArrays'  open= '('   separator= ',' close= ')'>",
            "#{item}",
            "</foreach>",
            "</where>",
            "</script>"})
    void deleteMultipleDepartment(@Param("dnoArrays")String[] dnoArrays);

    //添加学院
    @Insert("insert into department values('${dno}','${dname}','${dmanager}')")
    void addDepartment(Department department);

    //分页条件查看学院信息
    @Select("select * from department where dname like '%${dname}%' limit ${currentPage},${pageSize} ")
    List<Department> selectDepartmentByPageAndCondition(@Param("dname")String dname,@Param("currentPage")int currentPage,@Param("pageSize")int pageSize);

    //分页条件查看学院信息总数
    @Select("select count(dname) from department where dname like '%${dname}%'")
    int allDepartmentCounts(@Param("dname")String dname);

    //查看所有学院信息
    @Select("select * from department")
    List<Department> selectAllDepartment();
}
