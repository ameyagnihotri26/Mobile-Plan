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
	
	RestTemplate restTemplate = new RestTemplate();



	
	   
	@Autowired
	MobilePlanSrvc mpSrvc;

	@Autowired
	MobilePlanDao mobilePlanDao;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody MobilePlan inputentity) {
		ResponseEntity<Object> mpResponse;
		Date date = new Date();


		Object mobilePlan = mpSrvc.create(inputentity);
		if (mobilePlan != null) {
			
			mpResponse = new ResponseEntity<Object>(inputentity, null, HttpStatus.CREATED);
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("POST",inputentity.toString(),date));
    		restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
			return mpResponse;
			
		} else {
			String response = "Cant save as ID is already exist";
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("POST","Given ID: " + inputentity.getId()+ " already exist!",date));
			restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
            mpResponse = new ResponseEntity<Object>(response , null, HttpStatus.BAD_REQUEST);  
			return mpResponse;
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> read(@PathVariable(value = "id") Long id) {
        ResponseEntity<Object> mpResponse = null;
        
        Date date = new Date();
 	   
        
        Object mobilePlan = mpSrvc.read(id);
        
        
        if( mobilePlan != null) {
            mpResponse = new ResponseEntity<Object>(mobilePlan, null, HttpStatus.OK); 
            HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("GET-BY-ID",mpResponse.getBody().toString(),date));
    		restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
        }
        else {
        	String response = "Mobile plan does not exist for given ID";
        	
            mpResponse = new ResponseEntity<Object>(response , null, HttpStatus.NOT_FOUND);  
            HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("GET-BY-ID","Given ID: " + id +" does not exist!",date));
    		restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
        }
        
		return mpResponse;
    }
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<MobilePlan>> readAll() {
        ResponseEntity<Iterable<MobilePlan>> mpResponse;
        Date date = new Date();
 	   
		 HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new
		 Auditlog("GET","All plans displayed successfully",date));
		 restTemplate.postForObject("http://localhost:8081/audit", request,
		 Auditlog.class);
		 
        Iterable<MobilePlan> mobilePlanList = mpSrvc.readAll();
        
        mpResponse = new ResponseEntity<Iterable<MobilePlan>>(mobilePlanList, null, HttpStatus.OK);
        return mpResponse;
    }
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Object> update(@RequestBody MobilePlan tobemerged) {
        ResponseEntity<Object> planResponse;
        Date date = new Date();
 	   

        
        Object plan = mpSrvc.update(tobemerged);
        
        if(plan != null) {
        	planResponse = new ResponseEntity<Object>("Data updated Successfully", null, HttpStatus.OK);
        	HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("PATCH",tobemerged.toString(),date));
    		restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
        	return planResponse;
        }else {
        	String response = "Given ID does not exist";
			
			planResponse =  new ResponseEntity<Object>(response, null, HttpStatus.BAD_REQUEST);
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("PATCH","Given ID: " + tobemerged.getId()+ " does not exist!",date));
			restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
			return planResponse;
        }
     }	
	
	@CrossOrigin
	@RequestMapping(value = "{planid}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable(value = "planid") Long planid) {
		ResponseEntity<Object> planResponse = null;
		Date date = new Date();
		   
		
        
		Object delete = mpSrvc.delete(planid);
		
		if(delete != null) {
			String response = "Data deleted successfully";
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("DELETE","DELETED Successfully for given ID: " + planid,date));
			restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
			planResponse =  new ResponseEntity<Object>(response, null, HttpStatus.OK);
			return planResponse;
		}else {
			String response = "ID not found";
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("DELETE","Given ID: " + planid + " does not exist!",date));
			restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
			planResponse =  new ResponseEntity<Object>(response, null, HttpStatus.NOT_FOUND);
			return planResponse;
		}
	}

}