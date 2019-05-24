package com.prs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.Product;
import com.prs.db.ProductRepository;




@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ProductRepoTests {
	
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private ProductRepository productRepo;
	
	@Test
	public void findByProductIDShouldReturnProduct() {
//		this.entityManager.persist(new Product());
		
		Optional<Product> u = productRepo.findById(3);
		
		assertThat(u.get().getPartNumber()).isEqualTo("105810");
	}
}