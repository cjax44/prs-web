package com.prs;

import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.PurchaseRequest;
import com.prs.db.PurchaseRequestRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PurchaseRequestTest {
	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;
	
	@Test
	public void testUserGetAll() {
		Iterable<PurchaseRequest> products = purchaseRequestRepo.findAll();
		assertNotNull(products);
	}
	
	@Test
	public void testUserAdd() {
		PurchaseRequest p = new PurchaseRequest();
		assertNotNull(purchaseRequestRepo.save(p));
	}

}
