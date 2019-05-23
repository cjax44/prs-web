package com.prs;

import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.Vendor;
import com.prs.db.VendorRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VendorTest {
	
	@Autowired
	private VendorRepository vendorRepo;
	
	@Test
	public void testVendorGetAll() {
		Iterable<Vendor> Vendors = vendorRepo.findAll();
		assertNotNull(Vendors);
	}
	
	@Test
	public void testVendorAdd() {
		Vendor p = new Vendor();
		assertNotNull(vendorRepo.save(p));
	}

}
