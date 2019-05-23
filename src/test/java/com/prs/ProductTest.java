package com.prs;

import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.Product;
import com.prs.db.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductTest {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Test
	public void testProductGetAll() {
		Iterable<Product> products = productRepo.findAll();
		assertNotNull(products);
	}
	
	@Test
	public void testProductAdd() {
		Product p = new Product();
		assertNotNull(productRepo.save(p));
	}

}
