package com.org.utils;

import com.org.entity.Student;
import com.org.mapper.StudentMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenUtil {
    //验证token是否失效
   /* public static Student testToken(HttpServletRequest request, StudentMapper studentMapper) {
        String token = request.getHeader("token");
        Student selectStudentByToken=studentMapper.selectStudentByToken(token);
        if(token!=null&& !token.equals("")&& !token.equals("0") && selectStudentByToken!=null ){
            return selectStudentByToken;
        }else{
            return null;
        }
    }*/
    //在响应头添加token
    public static void addToken(HttpServletRequest request,HttpServletResponse response) {

        response.setHeader("Access-Control-Expose-Headers",
                "Cache-Control,Content-Type,Expires,Pragma,Content-Language,Last-Modified,token");
        response.addHeader("token", request.getHeader("token"));
    }
}
