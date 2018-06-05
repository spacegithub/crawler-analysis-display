package com.github.anddd7.boot.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认Controller, 测试用
 */
@Api
@RestController
public class HelloController {

  @Value("${server.port}")
  private String port;

  @GetMapping("/port")
  public String port() {
    return "I am from port:" + port;
  }
}
