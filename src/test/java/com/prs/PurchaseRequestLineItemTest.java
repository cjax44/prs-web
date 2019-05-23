package com.prs;

import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.PurchaseRequestLineItem;
import com.prs.db.PurchaseRequestLineItemRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PurchaseRequestLineItemTest {
	
	@Autowired
	private PurchaseRequestLineItemRepository pRLIRepo;
	
	@Test
	public void testPRLIGetAll() {
		Iterable<PurchaseRequestLineItem> products = pRLIRepo.findAll();
		assertNotNull(products);
	}
	
	@Test
	public void testPRLIAdd() {
		PurchaseRequestLineItem p = new PurchaseRequestLineItem();
		assertNotNull(pRLIRepo.save(p));
	}

}
