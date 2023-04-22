package com.org.serviceImpl;

import com.google.gson.Gson;
import com.org.entity.Student;
import com.org.mapper.StudentMapper;
import com.org.message.Msg;
import com.org.utils.ExcelUtil;
import com.org.utils.RedisUtil;
import com.org.utils.TokenUtil;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class StudentServiceImpl {

    @Resource
    StudentMapper studentMapper;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    RedisUtil redisUtil;

    Gson gson = new Gson();

    //根据学号修改身份证号
    public Msg changeSidBySno(String sid,String sno, HttpServletRequest request, HttpServletResponse response){
        TokenUtil.addToken(request, response);
        studentMapper.changeSidBySno(sid,sno);
        return Msg.success().add("detailMessage", "修改身份证成功");
    }

    //找回密码
    public Msg findPassword(String sno, String sid, HttpServletRequest request, HttpServletResponse response) {
        int result = studentMapper.findPassword(sno, sid);
        TokenUtil.addToken(request, response);
        if (result > 0) {
            //修改成功
            return Msg.success().add("detailMessage", "修改成功，初始密码为12345！！");
        } else {
            //修改失败
            return Msg.fail().add("detailMessage", "学号或身份证不正确");
        }
    }

    //根据学号查看个人详细信息
    public Msg selectStudentInfoBySno(String sno, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success().add("data", studentMapper.selectStudentInfoBySno(sno));
    }

    //根据旧密码修改新密码
    public Msg updatePasswordByPreviewPassword(String newPassword, String previewPassword, String sno, HttpServletRequest request, HttpServletResponse response) {

        TokenUtil.addToken(request, response);
        int result = studentMapper.updatePasswordByPreviewPassword(newPassword, previewPassword, sno);
        if (result > 0) {
            return Msg.success().add("detailMessage", "修改成功");
        } else {
            return Msg.fail().add("detailMessage", "旧密码不存在，请重新输入！！");
        }

    }

    //统计学生信息
    public Msg studentStatistic(HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        //return Msg.success().add("data", redisUtil.getStringValue("studentStatistic",studentMapper.studentStatistic(),60));
        return Msg.success().add("data", redisUtil.getStringValue("studentStatistic",gson.toJson(studentMapper.studentStatistic())));
    }

    //修改学生信息
    public Msg updateInfo(String sno, String spassword, String sname, String ssex, int sage, String sdept, String sid, String power, String studentSno, HttpServletRequest request, HttpServletResponse response) {

        //登录状态下
        TokenUtil.addToken(request, response);
        try {
            //修改成功
            studentMapper.updateInfo(sno, spassword, sname, ssex, sage, sdept, sid, power, studentSno);
            return Msg.success().add("detailMessage", "修改成功");
        } catch (Exception e) {
            //修改失败
            return Msg.fail().add("detailMessage", "学号已被注册过了，请更换学号");
        }
    }

    //根据学号查看学生信息
    public Msg selectStudentByNo(String sno, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success().add("data", studentMapper.selectStudentByNo(sno));
    }

    //查看所有信息
    public Msg selectAllStudents(HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        String keys="students";
        ListOperations<String,Student> listOperations=redisTemplate.opsForList();
        if(!redisTemplate.hasKey(keys)){
            //0  -1查询所有数据
            //无此key则向redis存储
            List<Student> students=studentMapper.selectAllStudents();
            listOperations.leftPushAll(keys,students);

            //设置缓存时间
            redisTemplate.expire(keys,60,TimeUnit.SECONDS);

        }
        return Msg.success().add("detailMessage","查询成功").add("data",listOperations.range(keys,0,-1));
        //return Msg.success().add("detailMessage","查询成功").add("data",redisUtil.getListValue("students",studentMapper.selectAllStudents()));
        //return Msg.success().add("detailMessage", "查询成功").add("data", studentMapper.selectAllStudents());
    }

    //批量添加学生
    public Msg addMultipleStudent(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        try {
            List<Student> students = ExcelUtil.getMultipleStudent(file.getInputStream(), file.getOriginalFilename());
            for (int i = 0; i < students.size(); i++) {
                if (studentMapper.JudgeStudentIsExistBySno(students.get(i).getSno()) != null) {
                    return Msg.fail().add("detailMessage", "某个学号已注册");
                } else if (studentMapper.JudgeStudentIsExistBySid(students.get(i).getSid()) != null) {
                    return Msg.fail().add("detailMessage", "某个身份证已注册");
                } else if (!Pattern.compile("^\\d{12}$").matcher(students.get(i).getSno()).matches()) {
                    return Msg.fail().add("detailMessage", "学号格式不正确");
                } else if (!Pattern.compile("\\d{15}|\\d{18}").matcher(students.get(i).getSid()).matches()) {
                    return Msg.fail().add("detailMessage", "身份证格式不正确");
                } else if (!Pattern.compile("^[\\u4e00-\\u9fa5]{0,}$").matcher(students.get(i).getSname()).matches()) {
                    return Msg.fail().add("detailMessage", "姓名只能是中文字符");
                } else if (!Pattern.compile("^\\d{2}$").matcher("" + students.get(i).getSage()).matches()) {
                    return Msg.fail().add("detailMessage", "年龄应该为2位数字");
                }
                int result=studentMapper.addMultipleStudent(students);
                if(result>0){
                    //添加成功
                    return Msg.success().add("detailMessage", "添加成功");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return Msg.fail().add("detailMessage", "数据类型不正确，请重新上传数据");
    }

    //登录
    public Msg login(String sno, String spassword, HttpServletRequest request, HttpServletResponse response) {
        Student student = studentMapper.login(sno, spassword);
        if (student != null) {
            //账号密码正确
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            stringRedisTemplate.opsForValue().set(token, sno,3600*24*3,TimeUnit.SECONDS);
            response.setHeader("Access-Control-Expose-Headers",
                    "Cache-Control,Content-Type,Expires,Pragma,Content-Language,Last-Modified,token");
            response.addHeader("token", token);
            return Msg.success().add("detailMessage", "登录成功").add("data", student);
        } else {
            //账号密码错误
            return Msg.fail().add("detailMessage", "账号密码错误，请重新输入！");
        }
    }

    //删除多个学生信息
    public Msg deleteMultipleStudent(String[] snoArrays, HttpServletRequest request, HttpServletResponse response) {
        studentMapper.deleteMultipleStudent(snoArrays);
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "删除成功");
    }

    //删除单个学生信息
    public Msg deleteStudentByNo(String sno, HttpServletRequest request, HttpServletResponse response) {
        studentMapper.deleteStudentByNo(sno);
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "删除成功");
    }

    //添加学生信息
    public Msg addStudent(HttpServletRequest request, HttpServletResponse response, Student student) {
        TokenUtil.addToken(request, response);
       /* if (studentMapper.JudgeStudentIsExistBySid(student.getSid()) != null) {
            //身份证已注册过了
            return Msg.fail().add("detailMessage", "身份证已注册过了");
        } else */
       if (studentMapper.JudgeStudentIsExistBySno(student.getSno()) != null) {
            //学号已注册过了
            return Msg.fail().add("detailMessage", "学号已注册过了");
        } else {
            studentMapper.addStudent(student);
            return Msg.success().add("detailMessage", "添加成功");
        }
    }

    //分页条件查看所有学生信息
    public Msg selectStudentByConditionAndPage(String condition, int currentPage, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.addToken(request, response);
        return Msg.success().add("detailMessage", "查看成功")
                .add("total", studentMapper.allStudentCounts(condition))
                .add("data", studentMapper.selectStudentByConditionAndPage(condition, currentPage, pageSize));
    }
}
