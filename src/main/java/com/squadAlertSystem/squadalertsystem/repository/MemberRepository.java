package com.squadAlertSystem.squadalertsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadAlertSystem.squadalertsystem.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}
