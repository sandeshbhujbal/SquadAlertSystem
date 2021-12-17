package com.squadAlertSystem.squadalertsystem.controller;

import com.squadAlertSystem.squadalertsystem.command.member.MemberCommand;
import com.squadAlertSystem.squadalertsystem.dto.request.CreateMemberRequest;
import com.squadAlertSystem.squadalertsystem.dto.response.GetMemberResponse;
import com.squadAlertSystem.squadalertsystem.service.member.MemberService;
import com.squadAlertSystem.squadalertsystem.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(MemberController.BASE_URI)
public class MemberController {

  public static final String BASE_URI = "/member";

  @Autowired
  private MemberCommand memberCommand;

  @PostMapping(path = "/create")
  public String createMember(@RequestBody CreateMemberRequest request) {
    return memberCommand.createMember(request);
  }

  @GetMapping(path = "/list")
  public List<GetMemberResponse> getMemberList(
          @RequestParam(required = false) String squad_id,
          @RequestParam(required = false) String name){
    return memberCommand.getMemberList(squad_id, name);
  }
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
