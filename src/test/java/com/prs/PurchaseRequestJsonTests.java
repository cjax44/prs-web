package com.prs;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.PurchaseRequest;

@RunWith(SpringRunner.class)
@JsonTest
public class PurchaseRequestJsonTests {

	@Autowired
	private JacksonTester<PurchaseRequest> json;
	
	@Test
	public void serializePurchaseRequestJsonTest() {
		PurchaseRequest purchaseRequest = new PurchaseRequest();
		
		try {
			assertThat(this.json.write(purchaseRequest)).extractingJsonPathStringValue("$.deliverytrype").isEqualTo(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}