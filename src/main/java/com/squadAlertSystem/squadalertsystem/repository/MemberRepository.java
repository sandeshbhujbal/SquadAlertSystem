package com.squadAlertSystem.squadalertsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadAlertSystem.squadalertsystem.entity.Member;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberRepository extends JpaRepository<Member, String>,
        JpaSpecificationExecutor<Member> {

  Member findByName(String memberName);
}
