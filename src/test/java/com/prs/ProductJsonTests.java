package com.prs;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.Product;

@RunWith(SpringRunner.class)
@JsonTest
public class ProductJsonTests {

	@Autowired
	private JacksonTester<Product> json;
	
	@Test
	public void serializeProductJsonTest() {
		Product product = new Product();
		
		try {
			assertThat(this.json.write(product)).extractingJsonPathStringValue("$.partNumber").isEqualTo(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}