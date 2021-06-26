package com.company.controller;

import com.company.service.RulesProcessingService;
import com.company.vo.ApiRequest;
import com.company.vo.Customer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/spring-rules-processor")
public class RulesProcessorController {

	@Autowired
	private RulesProcessingService rulesProcessingService;

	@PostMapping(value = "/applyrules")
	public List<Customer> applyMapping(@RequestBody ApiRequest req) throws Exception {
		List<Customer> c = rulesProcessingService.applyRules(req);
		return c;
	}

}
