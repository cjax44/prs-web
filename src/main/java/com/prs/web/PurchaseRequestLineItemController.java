package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequest;
import com.prs.business.PurchaseRequestLineItem;
import com.prs.db.PurchaseRequestLineItemRepository;
import com.prs.db.PurchaseRequestRepository;

@RestController 
@RequestMapping("/purchase-request-line-items")
public class PurchaseRequestLineItemController {

	@Autowired
	private PurchaseRequestLineItemRepository pRLIRepo;
//	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;
	
	private double recalculateTotal(PurchaseRequest pr) {
		
		Iterable<PurchaseRequestLineItem> prliList = pRLIRepo.findAllBypurchaseRequest(pr);
		double total = 0;
		for (PurchaseRequestLineItem prli: prliList) {
			
			total += (prli.getProduct().getPrice() * prli.getQuantity());
			
		}

		
		return total;
		
	}

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(pRLIRepo.findAll());

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@GetMapping("/{id}")
	public JsonResponse getAll(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequestLineItem> p = pRLIRepo.findById(id);
			if (p.isPresent())
				jr = JsonResponse.getInstance(p);
			else
				jr = JsonResponse.getInstance("No Line Item found for id: " + id);

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody PurchaseRequestLineItem p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			if (pRLIRepo.existsById(p.getId())) {
				
				// delete prli
				pRLIRepo.delete(p);
				// recalculate total
				PurchaseRequest pr = purchaseRequestRepo.findById(p.getPurchaseRequest().getId()).get();
				double total = recalculateTotal(p.getPurchaseRequest());
				pr.setTotal(total);
				purchaseRequestRepo.save(pr);
			
				jr = JsonResponse.getInstance("Line item deleted.");
			} else {
				jr = JsonResponse
						.getInstance("Line item ID: " + p.getId() + " does not exist and you are attempting to delete it");
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody PurchaseRequestLineItem p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			if (pRLIRepo.existsById(p.getId())) {
				// update prli
				jr = JsonResponse.getInstance(pRLIRepo.save(p));
				// recalculate total
				PurchaseRequest pr = purchaseRequestRepo.findById(p.getPurchaseRequest().getId()).get();
				double total = recalculateTotal(p.getPurchaseRequest());
				pr.setTotal(total);
				purchaseRequestRepo.save(pr);
				
				
			} else {
				jr = JsonResponse
						.getInstance("Line item ID: " + p.getId() + " does not exist and you are attempting to save it");
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@PostMapping("/")
	public JsonResponse add(@RequestBody PurchaseRequestLineItem p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			// add prli
			jr = JsonResponse.getInstance(pRLIRepo.save(p));
			// recalculate total
			PurchaseRequest pr = purchaseRequestRepo.findById(p.getPurchaseRequest().getId()).get();
			double total = recalculateTotal(p.getPurchaseRequest());
			pr.setTotal(total);
			purchaseRequestRepo.save(pr);
			
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

}
