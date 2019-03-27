package com.pkk.backend.controller;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: peikunkun
 * @create: 2019-03-27 14:38
 **/
@RestController
public class TestController {


  @RequestMapping("/test")
  public String test(HttpServletRequest request) {
    System.out.println("----------------header----------------");
    Enumeration headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      System.out.println(key + ": " + request.getHeader(key));
    }
    System.out.println("----------------header----------------");
    return "hellooooooooooooooo!";
  }

}
