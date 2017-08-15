package org.mose.springboot.springmvc.controller;

import org.apache.http.HttpRequest;
import org.mose.springboot.sidebar.dao.AbcRepository;
import org.mose.springboot.sidebar.dao.AbcStreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */
@Controller
public class SpringMvcController {
    /**
     * 利用MappingJackson2HttpMessageConverter实现Json转换
     *
     * @return
     */
    @RequestMapping("/testJson")
    @ResponseBody
    public Map<String, Object> testJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("action", "test json");
        return map;
    }

    @RequestMapping("/exception")
    public void testException() throws Exception {
        throw new Exception("Controller exception.");
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/lock")
    public String lockPage() {
        return "lock";
    }

    @RequestMapping("/index")
    public String indexPage() {
        return "index";
    }

    @Autowired
    AbcStreamRepository abcStreamRepository;
    @Autowired
    AbcRepository abcRepository;

    @ModelAttribute
    public String name(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    @RequestMapping("/test")
    public String tablePage(Model model, @RequestParam(required = false) String color) {
        model.addAttribute("targetPage", "test");
        model.addAttribute("color", color);
        return "forward:/step1.htm?page=test";
    }
//    public ModelAndView tablePage(@RequestParam(required = false) String color) {
//        Abc abc = abcStreamRepository.queryOneById("1");
//        Abc abc2 = abcRepository.queryOneById("1");
//        List<Abc> list = abcRepository.queryManyByName("test");
//
//        abc.setName("1In");
//        abcRepository.updateOne(abc);
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("color", color);
//        modelAndView.setViewName("test");
//        return modelAndView;
//    }

    @RequestMapping("/step1")
    public ModelAndView step1(@RequestParam String page) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("step1", "step1");
        modelAndView.setViewName(page);
        return modelAndView;
    }
}
