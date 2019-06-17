package com.prs.web;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
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
import com.prs.business.User;
import com.prs.business.PurchaseRequest;
import com.prs.db.PurchaseRequestRepository;
import com.prs.db.UserRepository;

@RestController
@RequestMapping("/purchase-requests")
public class PurchaseRequestController {

	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;
	
	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(purchaseRequestRepo.findAll());

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
	
	@GetMapping("/list-review")
	public JsonResponse listReview(@PathVariable int id) {
		JsonResponse jr = null;		
		
		try {
			jr = JsonResponse.getInstance(purchaseRequestRepo.findAllByStatusAndUserNot("Review", userRepo.findById(id)));

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	

	}

	@GetMapping("/{id}")
	public JsonResponse getAll(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequest> p = purchaseRequestRepo.findById(id);
			if (p.isPresent())
				jr = JsonResponse.getInstance(p);
			else
				jr = JsonResponse.getInstance("No Purchase Request found for id: " + id);

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			if (purchaseRequestRepo.existsById(p.getId())) {
				purchaseRequestRepo.delete(p);
				jr = JsonResponse.getInstance("Purchase request deleted.");
			} else {
				jr = JsonResponse
						.getInstance("Purchase Request ID: " + p.getId() + " does not exist and you are attempting to delete it");
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			if (purchaseRequestRepo.existsById(p.getId())) {
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(p));
			} else {
				jr = JsonResponse
						.getInstance("Purchase Request ID: " + p.getId() + " does not exist and you are attempting to save it");
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
	
	@PutMapping("/submit-review")
	public JsonResponse submitForReview(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0); // same for minutes and seconds
		// NOTE: May want to enhance exception handling
		try {
			if (purchaseRequestRepo.existsById(p.getId()) && p.getTotal() <= 50) {
				
				p.setSubmittedDate(today.getTime());
				p.setStatus("Approved");
				jr = JsonResponse.getInstance(purchaseRequestRepo.findById(p.getId()));
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(p));
				
			}	else if (purchaseRequestRepo.existsById(p.getId()) && p.getTotal() > 50) {
				
				p.setSubmittedDate(today.getTime());
				p.setStatus("Review");
				jr = JsonResponse.getInstance(purchaseRequestRepo.findById(p.getId()));
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(p));
				
			}	else {
				
				jr = JsonResponse
						.getInstance("Purchase Request ID: " + p.getId() + " already exists");
						
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
	
	@PutMapping("/approve")
	public JsonResponse approve(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			if (purchaseRequestRepo.existsById(p.getId())) {
				
				p.setStatus("Approved");
				jr = JsonResponse.getInstance(purchaseRequestRepo.findById(p.getId()));
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(p));
				
			}	else {
				
				jr = JsonResponse
						.getInstance("Purchase Request ID: " + p.getId() + " doesn't exist");
						
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
	

	@PutMapping("/reject")
	public JsonResponse reject(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			if (purchaseRequestRepo.existsById(p.getId())) {
				
				p.setStatus("Rejected");
				jr = JsonResponse.getInstance(purchaseRequestRepo.findById(p.getId()));
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(p));
				
			}	else {
				
				jr = JsonResponse
						.getInstance("Purchase Request ID: " + p.getId() + " doesn't exist");
						
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
	
	
	
	@PostMapping("/submit-new")
	public JsonResponse submitNew(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		Calendar today = Calendar.getInstance();
//		today.set(Calendar.HOUR_OF_DAY, 0); // same for minutes and seconds
		// NOTE: May want to enhance exception handling
		try {
			if (purchaseRequestRepo.existsById(p.getId())) {
				jr = JsonResponse
						.getInstance("Purchase Request ID: " + p.getId() + " already exists");
			} else {
				p.setSubmittedDate(today.getTime());
				p.setStatus("New");
				jr = JsonResponse.getInstance(purchaseRequestRepo.findById(p.getId()));
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(p));
						
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@PostMapping("/")
	public JsonResponse add(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			jr = JsonResponse.getInstance(purchaseRequestRepo.save(p));

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

}
