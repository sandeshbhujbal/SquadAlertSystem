package com.squadAlertSystem.squadalertsystem.controller;

import com.squadAlertSystem.squadalertsystem.command.member.MemberCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MemberController.BASE_URI)
public class MemberController {
  public static final String BASE_URI = "/member";

  @Autowired
  MemberCommand memberCommand;

  @GetMapping(path = "/listMembers")
  public String getCacheCount() {

    memberCommand.printMessage();
    return "hellooo";

  }

}
