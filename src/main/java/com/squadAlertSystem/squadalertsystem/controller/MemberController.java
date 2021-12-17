package com.squadAlertSystem.squadalertsystem.controller;

import com.squadAlertSystem.squadalertsystem.service.member.MemberService;
import com.squadAlertSystem.squadalertsystem.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MemberController.BASE_URI)
public class MemberController {

  public static final String BASE_URI = "/member";

  @Autowired
  MemberService memberService;

  @GetMapping(path = "/listMembers")
  public Member getCacheCount() {

    memberService.printMessage();

    return Member.builder()
      .id("test")
      .email("test1")
      .name("name")
      .phoneNumber("123456789")
      .build();
  }

}
