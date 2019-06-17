package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.Vendor;
import com.prs.db.VendorRepository;

@CrossOrigin
@RestController
@RequestMapping("/vendors")
public class VendorController {

	@Autowired
	private VendorRepository vendorRepo;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(vendorRepo.findAll());

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@GetMapping("/{id}")
	public JsonResponse getAll(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Vendor> p = vendorRepo.findById(id);
			if (p.isPresent())
				jr = JsonResponse.getInstance(p);
			else
				jr = JsonResponse.getInstance("No Vendor found for id: " + id);

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody Vendor p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			if (vendorRepo.existsById(p.getId())) {
				vendorRepo.delete(p);
				jr = JsonResponse.getInstance("Vendor deleted.");
			} else {
				jr = JsonResponse
						.getInstance("Vendor ID: " + p.getId() + " does not exist and you are attempting to delete it");
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody Vendor p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			if (vendorRepo.existsById(p.getId())) {
				jr = JsonResponse.getInstance(vendorRepo.save(p));
			} else {
				jr = JsonResponse
						.getInstance("Vendor ID: " + p.getId() + " does not exist and you are attempting to save it");
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@PostMapping("/")
	public JsonResponse add(@RequestBody Vendor p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			jr = JsonResponse.getInstance(vendorRepo.save(p));

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

}
