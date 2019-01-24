package com.xuecheng.test.freemarker.controller;

import com.xuecheng.test.freemarker.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 执行流程(自己):springboot启动之后加载配置文件,
     * 加载到freemark,自动扫描并把当前类使用freemark全局化,只需要把数据保存到参数中的map集合中,
     * 并返回模板的文件名,自动进行静态化,不生成文件,在内存中生成html文件
     * @param map
     * @return
     */
    @RequestMapping("/test1")
    public String freemark(Map<String,Object> map){
        //添加字符串
        map.put("name","张三");
        //添加对象
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());
        map.put("stu1",stu1);
        //添加list对象
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);
        stu2.setBirthday(new Date());
        List<Student> list = new ArrayList<>();
        list.add(stu1);
        list.add(stu2);
        map.put("list",list);
        //添加map对象
        Map<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);
        map.put("stuMap",stuMap);
        return "test1";
    }
    @RequestMapping("/banner")
    public String index_banner(Map<String, Object> map){
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map body = forEntity.getBody();
        map.putAll(body);
        return "index_banner";

    }

}
