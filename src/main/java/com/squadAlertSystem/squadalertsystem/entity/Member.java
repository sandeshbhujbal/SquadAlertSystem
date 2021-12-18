package com.squadAlertSystem.squadalertsystem.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.Predicate;

@Builder
@Entity
@Getter
@Setter
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Member member = (Member) o;
    return id != null && Objects.equals(id, member.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public static Specification<Member> nameLike(final String name) {
    return (Specification<Member>) (root, query, cb) -> {
      Predicate predicate = null;
      if(!StringUtils.isEmpty(name)) {
        predicate = cb.like(cb.lower(root.<String>get("name")), "%" + name + "%");
      }
      return predicate;
    };
  }
}
