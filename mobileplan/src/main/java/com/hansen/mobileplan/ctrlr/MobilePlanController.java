package com.hansen.mobileplan.ctrlr;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hansen.mobileplan.dao.MobilePlanDao;
import com.hansen.mobileplan.model.Auditlog;
import com.hansen.mobileplan.model.MobilePlan;
import com.hansen.mobileplan.srvc.MobilePlanSrvc;

@RestController
@RequestMapping("/mp")
public class MobilePlanController {
	
//	RestTemplate variable.
	static final RestTemplate restTemplate = new RestTemplate();
	
//	URI variable.
	static final String URI = "http://localhost:8081/audit";
	
//	ID not exist variable.
	static final String ID_NOT_EXIST = "Given ID does not exist";
	   
	@Autowired
	MobilePlanSrvc mpSrvc;

	@Autowired
	MobilePlanDao mobilePlanDao;
	
//	create - This function is used to create/store a mobileplan.
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody MobilePlan inputentity) {
		ResponseEntity<Object> mpResponse;
		
		Date date = new Date();

		Object mobilePlan = mpSrvc.create(inputentity);
		if (mobilePlan != null) {
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new 
			Auditlog("CREATE",inputentity.toString(),date));
    		restTemplate.postForObject(URI, request, Auditlog.class);
    		
    		mpResponse = new ResponseEntity<Object>(inputentity, null, HttpStatus.CREATED);
		} else {
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new 
			Auditlog("CREATE","Given ID: " + inputentity.getId()+ " Already Exist.",date));
			restTemplate.postForObject(URI, request, Auditlog.class);
			
			String response = "Cant save as ID is already exist";
            mpResponse = new ResponseEntity<Object>(response, null, HttpStatus.BAD_REQUEST);  
		}
		
		return mpResponse;
	}
	
//	read - This function is used to get a mobileplan by its ID.
	@CrossOrigin
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> read(@PathVariable(value = "id") Long id) {
        ResponseEntity<Object> mpResponse = null;
        
        Date date = new Date();
        
        Object mobilePlanByID = mpSrvc.read(id);  
        if( mobilePlanByID != null) {
            mpResponse = new ResponseEntity<Object>(mobilePlanByID, null, HttpStatus.OK); 
            
            HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new 
            Auditlog("GET-BY-ID","Plan ID: " + id + " Displayed.",date));
    		restTemplate.postForObject(URI, request, Auditlog.class);
        }
        else {
            mpResponse = new ResponseEntity<Object>(ID_NOT_EXIST , null, HttpStatus.NOT_FOUND);  
            
            HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new 
            Auditlog("GET-BY-ID","Given ID: " + id +" Does Not Exist.",date));
    		restTemplate.postForObject(URI, request, Auditlog.class);
        }
        
		return mpResponse;
    }
	
//	readAll - This function is used to get the list of all mobileplan exists inside database.
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<MobilePlan>> readAll() {
        ResponseEntity<Iterable<MobilePlan>> mpResponse;
        
        Date date = new Date();
		 
        Iterable<MobilePlan> mobilePlanList = mpSrvc.readAll();
        
        mpResponse = new ResponseEntity<Iterable<MobilePlan>>(mobilePlanList, null, HttpStatus.OK);
        
        return mpResponse;
    }
	
//	update - This function is used to update a mobileplan for the respective ID.	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Object> update(@RequestBody MobilePlan tobemerged) {
        ResponseEntity<Object> planResponse;
        
        Date date = new Date();
 	   
        Object updatedMobilePlan = mpSrvc.update(tobemerged);
        if(updatedMobilePlan != null) {
        	planResponse = new ResponseEntity<Object>(URI, null, HttpStatus.OK);
        	
        	HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new 
        	Auditlog("UPDATE","Plan ID: " + tobemerged.getId() + " Updated.",date));
    		restTemplate.postForObject(URI, request, Auditlog.class);
        }else {
			planResponse =  new ResponseEntity<Object>(ID_NOT_EXIST, null, HttpStatus.BAD_REQUEST);
			
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new
			Auditlog("UPDATE","Given ID: " + tobemerged.getId()+ " Does Not Exist.",date));
			restTemplate.postForObject(URI, request, Auditlog.class);
        }
        
        return planResponse;
     }
	
//	delete - This function is used to delete a mobileplan for given ID.
	@CrossOrigin
	@RequestMapping(value = "{planid}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable(value = "planid") Long planid) {
		ResponseEntity<Object> planResponse = null;
		
		Date date = new Date();

		Object deletedMobilePlan = mpSrvc.delete(planid);
		if(deletedMobilePlan != null) {
			String response = "Data deleted successfully";
			
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new 
			Auditlog("DELETE","Plan ID: " + planid + " Deleted.",date));
			restTemplate.postForObject(URI, request, Auditlog.class);
			
			planResponse =  new ResponseEntity<Object>(response, null, HttpStatus.OK);
		}else {
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new
			Auditlog("DELETE","Given ID: " + planid + " Does Not Exist.",date));
			restTemplate.postForObject(URI, request, Auditlog.class);
			
			planResponse =  new ResponseEntity<Object>(ID_NOT_EXIST, null, HttpStatus.NOT_FOUND);
		}
		
		return planResponse;
	}
}