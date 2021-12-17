package com.squadAlertSystem.squadalertsystem.service.squad;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.squadAlertSystem.squadalertsystem.dto.request.AddMemberRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.squadAlertSystem.squadalertsystem.dto.request.CreateSquadRequest;
import com.squadAlertSystem.squadalertsystem.dto.request.SquadListingRequest;
import com.squadAlertSystem.squadalertsystem.dto.response.SquadDetailResponse;
import com.squadAlertSystem.squadalertsystem.dto.response.SquadListingResponse;
import com.squadAlertSystem.squadalertsystem.entity.AlertConfiguration;
import com.squadAlertSystem.squadalertsystem.entity.Member;
import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.repository.AlertConfigurationRepository;
import com.squadAlertSystem.squadalertsystem.repository.MemberRepository;
import com.squadAlertSystem.squadalertsystem.repository.ServiceRepository;
import com.squadAlertSystem.squadalertsystem.repository.SquadRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SquadService  {

  @Autowired
  private SquadRepository squadRepository;

  @Autowired
  private ServiceRepository serviceRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private AlertConfigurationRepository alertConfigurationRepository;

  public String createSquad(CreateSquadRequest request) {
    log.info("receive request to create squad {}", request);
    List<Member> memberList = validateServices(request);
    List<com.squadAlertSystem.squadalertsystem.entity.Service> serviceList = validateMembers(request);
    Squad squad = new Squad();
    BeanUtils.copyProperties(request, squad);
    List<AlertConfiguration> alertConfigurationList = request.getAlertConfigurations();
    if(StringUtils.isEmpty(request.getId())) {
      squad.setMembers(new HashSet<>(memberList));
      squad.setServices(new HashSet<>(serviceList));
      squad.setAlertConfigurations(new HashSet<>(alertConfigurationList));
      squad.setPageId(buildPageId(request.getEmailId()));
    } else {
      squad = squadRepository.findById(request.getId()).get();
      squad.getMembers().addAll(memberList);
      squad.getServices().addAll(serviceList);
      squad.getAlertConfigurations().addAll(new ArrayList<>(alertConfigurationList));
    }
    if(Objects.nonNull(request.getAlertConfigurations())) {
      alertConfigurationList = alertConfigurationRepository.saveAll(request.getAlertConfigurations());
    }
    squad = squadRepository.save(squad);
    return squad.getId();
  }

  public List<SquadListingResponse> listSquads(SquadListingRequest request) {
    log.info("listing squads");
    List<Squad> squadList = squadRepository.listSquads(request);
    return squadList.stream()
      .map(Squad::toSquadListingResponse)
      .collect(Collectors.toList());
  }

  public SquadDetailResponse getSquadDetail(String squadId) {
    log.info("squad details");
    Squad squad = squadRepository.findById(squadId).get();
    SquadDetailResponse squadDetailResponse = new SquadDetailResponse();
    BeanUtils.copyProperties(squad, squadDetailResponse);
    squadDetailResponse.setAlertConfigurations(squad.getAlertConfigurations());
    squadDetailResponse.setCalendars(squad.getCalendars());
    squadDetailResponse.setMembers(squad.getMembers());
    squadDetailResponse.setServices(squad.getServices());
    return squadDetailResponse;
  }

  private String buildPageId(String emailId) {
    return "page-".concat(emailId);
  }

  private List<Member> validateServices(CreateSquadRequest request) {
     if(Objects.nonNull(request.getMembers())) {
       return request.getMembers().stream()
        .map(memberName -> memberRepository.findByName(memberName))
        .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  private List<com.squadAlertSystem.squadalertsystem.entity.Service> validateMembers(CreateSquadRequest request) {
    if(Objects.nonNull(request.getServices()))  {
      return request.getServices().stream()
        .map(serviceName -> serviceRepository.findByName(serviceName))
        .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  public String addMember(AddMemberRequest request) {
    Optional<Squad> squadOptional = squadRepository.findById(request.getSquadId());
    Optional<Member> memberOptional = memberRepository.findById(request.getMemberId());
    if(squadOptional.isPresent() && memberOptional.isPresent()) {
      Squad squad = squadOptional.get();
      Set<Member> memberSet = squad.getMembers();
      memberSet.add(memberOptional.get());
      squad.setMembers(memberSet);
      squad = squadRepository.save(squad);
      return squad.getId();
    }
    return null;
  }

}
