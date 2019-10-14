package com.lms.LMSAdmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.LMSAdmin.pojo.Borrower;
import com.lms.LMSAdmin.service.BorrowerService;

@RestController
@RequestMapping(value = "/LMSAdmin/borrower",
	consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
	produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class BorrowerController {
	
	@Autowired
	BorrowerService borrService;
	
	//Create a record
	@RequestMapping(value = "/create/borrName/{name}/borrAddress/{address}/borrPhone/{phone}", 
			method = {RequestMethod.POST, RequestMethod.GET})
	
	public ResponseEntity<String> insertBorr(@PathVariable("name") String borrName, @PathVariable("address") String borrAddress, 
			@PathVariable("phone") String borrPhone) {
		
		borrService.insertBorr(borrName, borrAddress, borrPhone);
		return new ResponseEntity<String>("Borrower record created.", HttpStatus.CREATED);
	}
	
	//Update a record
	@RequestMapping(value = "/update/cardNo/{cardNo}/borrName/{name}/borrAddress/{address}/borrPhone/{phone}", 
			method = {RequestMethod.PUT, RequestMethod.GET})
	
	public ResponseEntity<String> updateBorr(@PathVariable("cardNo") int cardNo, @PathVariable("name") String borrName, 
			@PathVariable("address") String borrAddress, @PathVariable("phone") String borrPhone) {
		
		boolean checkId = borrService.ifExists(cardNo);
		
		if(checkId == true) {
			borrService.updateBorr(cardNo, borrName, borrAddress, borrPhone);
			return new ResponseEntity<String>("Borrower record updated.", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Invalid ID.", HttpStatus.NOT_FOUND);
		}
	}
	
	//Delete a record
	@RequestMapping(value = "/delete/cardNo/{cardNo}", method = {RequestMethod.DELETE, RequestMethod.GET})
	public ResponseEntity<String> deleteBorr(@PathVariable("cardNo") int cardNo) {
				
		boolean checkId = borrService.ifExists(cardNo);
		
		if(checkId == true) {
			borrService.deleteBorr(cardNo);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<String>("Invalid ID.", HttpStatus.NOT_FOUND);
		}
	}
	
	//View all records
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<Borrower> getAllBorrs() {
		return borrService.getAllBorrs();
	}
}
