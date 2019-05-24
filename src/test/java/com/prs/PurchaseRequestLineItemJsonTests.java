package com.prs;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.PurchaseRequestLineItem;

@RunWith(SpringRunner.class)
@JsonTest
public class PurchaseRequestLineItemJsonTests {

	@Autowired
	private JacksonTester<PurchaseRequestLineItem> json;
	
	@Test
	public void serializePurchaseRequestLineItemJsonTest() {
		PurchaseRequestLineItem purchaseRequestLineItem = new PurchaseRequestLineItem();
		
		try {
			assertThat(this.json.write(purchaseRequestLineItem)).extractingJsonPathStringValue("$.status").isEqualTo(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}