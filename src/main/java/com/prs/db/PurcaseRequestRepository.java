package com.prs.db;

import org.springframework.data.repository.CrudRepository;

import com.prs.business.PurchaseRequest;

public interface PurcaseRequestRepository extends CrudRepository<PurchaseRequest, Integer> {

}
