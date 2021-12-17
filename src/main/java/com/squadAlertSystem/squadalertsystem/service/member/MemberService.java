package com.squadAlertSystem.squadalertsystem.service.member;

import com.squadAlertSystem.squadalertsystem.dto.request.CreateMemberRequest;
import com.squadAlertSystem.squadalertsystem.dto.response.GetMemberResponse;
import com.squadAlertSystem.squadalertsystem.entity.Member;
import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.repository.MemberRepository;
import com.squadAlertSystem.squadalertsystem.repository.SquadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private SquadRepository squadRepository;

  public void printMessage() {
    System.out.println("hellooooo priting message");
  }

  public String createMember(CreateMemberRequest request) {
    Member newMember = Member.builder()
            .id(request.getId())
            .name(request.getName())
            .email(request.getEmail())
            .phoneNumber(request.getPhone())
            .build();
    Member memberSaved = memberRepository.save(newMember);
    return memberSaved.getId();
  }

  public List<GetMemberResponse> getMemberList(String squadId, String memberName){
    if(!StringUtils.isEmpty(squadId)) {
      Optional<Squad> squadOptional = squadRepository.findById(squadId);
      if(squadOptional.isPresent()) {
        return squadOptional.get().getMembers().stream()
                .map(member -> GetMemberResponse.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .email(member.getEmail())
                        .phone(member.getPhoneNumber())
                        .build())
                .collect(Collectors.toList());
      }
      return Collections.emptyList();
    }
    return memberRepository.findAll(constructSpec(memberName)).stream()
            .map(member -> GetMemberResponse.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .email(member.getEmail())
                    .phone(member.getPhoneNumber())
                    .build()).collect(Collectors.toList());
  }

  private Specification<Member> constructSpec(String memberName) {
    return Member.nameLike(memberName);
  }

}
