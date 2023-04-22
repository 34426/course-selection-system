package com.org.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Msg {
    int status;
    String message;
    Map<String,Object> data=new HashMap<String,Object>();

    public static Msg success() {
        Msg result = new Msg();
        result.setStatus(200);
        result.setMessage("处理成功！");
        return result;
    }

    public static Msg fail() {
        Msg result = new Msg();
        result.setStatus(400);
        result.setMessage("处理失败！");
        return result;
    }

    public static Msg error() {
        Msg result = new Msg();
        result.setStatus(500);
        result.setMessage("未知异常！");
        return result;
    }

    public Msg add(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
