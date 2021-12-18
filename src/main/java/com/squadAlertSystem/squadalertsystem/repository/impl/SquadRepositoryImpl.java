package com.squadAlertSystem.squadalertsystem.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.squadAlertSystem.squadalertsystem.dto.request.SquadListingRequest;
import com.squadAlertSystem.squadalertsystem.entity.Squad;
import com.squadAlertSystem.squadalertsystem.repository.CustomRepository.CustomSquadRepository;
import com.squadAlertSystem.squadalertsystem.repository.SquadRepository;


public class SquadRepositoryImpl implements CustomSquadRepository {

  @Autowired
  private EntityManager entityManager;

  @Override
  public List<Squad> listSquads(SquadListingRequest request) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Squad> query = criteriaBuilder.createQuery(Squad.class);
    Root<Squad> root = query.from(Squad.class);
    Predicate queryPredicate = criteriaBuilder.conjunction();
    queryPredicate = Optional.of(queryPredicate)
      .map(predicate -> applySearchFilter(criteriaBuilder, predicate, root, request.getFilters()))
      .orElse(queryPredicate);
    query.where(queryPredicate);
    TypedQuery<Squad> squadTypedQuery = entityManager.createQuery(query);
    return squadTypedQuery.getResultList();
  }

  private  <T> Predicate applySearchFilter(CriteriaBuilder builder, Predicate predicate, Root<T> root,
    List<SquadListingRequest.Filter> filters) {
    List<Predicate> predicates = new ArrayList<>();
    if (!CollectionUtils.isEmpty(filters)) {
      for (SquadListingRequest.Filter filter : filters) {
        if (!StringUtils.isEmpty(filter.getValue())) {
          predicates
            .add(builder.like(builder.lower(root.get(filter.getField())), "%" + filter.getValue().toLowerCase() + "%"));
        }
      }
    }
    return builder.and(predicate, builder.and(predicates.toArray(new Predicate[] {})));
  }
}
