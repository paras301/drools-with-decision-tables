package com.company.controller;

import com.company.service.RulesProcessingService;
import com.company.vo.Customer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/spring-rules-processor")
public class RulesProcessorController {

	@Autowired
	private RulesProcessingService rulesProcessingService;

	@PostMapping(value = "/applyrules")
	public Customer applyMapping(@RequestBody Customer req) throws Exception {
		log.info("Request data == " + req);
		req = rulesProcessingService.applyRules(req);
		return req;
	}

}
