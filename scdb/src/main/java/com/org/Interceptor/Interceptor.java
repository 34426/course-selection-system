package com.org.Interceptor;

import com.google.gson.Gson;
import com.org.message.Msg;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class Interceptor implements HandlerInterceptor{

    @Resource
    StringRedisTemplate stringRedisTemplate;
    //拦截器类
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")){
            //浏览器在发送请求时会默认先发送一次类型为’option’且不带任何参数的请求，请求成功后才会发送真正的Post或者get请求
            // /设置option请求通过
            return true;
        }

        String token=request.getHeader("token");
        if(token!=null){
            //有token
            String sno=stringRedisTemplate.opsForValue().get(token);
            if (sno!=null){
                //token值存在
                return true;
            }else {
                //token值不存在
                returnJson(response);
                return false;
            }
        }else {
            //无token
            returnJson(response);
            return false;
        }
    }

    public void returnJson(HttpServletResponse response){
        PrintWriter writer=null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer=response.getWriter();
            Msg msg=Msg.fail().add("detailMessage","没有token或token失效");
            Gson gson=new Gson();
            writer.print(gson.toJson(msg));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(writer!=null){
                writer.close();
            }
        }
    }
}
