package com.hansen.mobileplan.ctrlr;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
		
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
		SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		

		Object mobilePlan = mpSrvc.create(inputentity);
		
		
		if (mobilePlan != null) {
			String output = "";
			output += "Plan ID: "  + inputentity.getId()+" CREATED";
			output += "<br>";
			output += "Body: " + "[Name:" + inputentity.getName() + ", " + "Description:" + inputentity.getDescription() +
					", " + "Validity:" + inputentity.getValidity() +"]";
					
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new 
			Auditlog("CREATE",output,f.format(new Date())));
    		restTemplate.postForObject(URI, request, Auditlog.class);
    		
    		mpResponse = new ResponseEntity<Object>(inputentity, null, HttpStatus.CREATED);
		} else {
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new 
			Auditlog("CREATE","Given ID: " + inputentity.getId()+ " Already Exist.",f.format(new Date())));
			restTemplate.postForObject(URI, request, Auditlog.class);
			
			String response = "Cant save as ID is already exist";
            mpResponse = new ResponseEntity<Object>(response, null, HttpStatus.BAD_REQUEST);  
		}
		
		return mpResponse;
	}
	
//	read - This function is used to get a mobileplan by its ID.
//	@CrossOrigin
//	@RequestMapping(value = "{id}", method = RequestMethod.GET)
//    public ResponseEntity<Object> read(@PathVariable(value = "id") Long id) {
//        ResponseEntity<Object> mpResponse = null;
//        
//        MobilePlan mp = new MobilePlan();
//        
//        Object mobilePlanByID = mpSrvc.read(id);  
//        if( mobilePlanByID != null) {
//            mpResponse = new ResponseEntity<Object>(mobilePlanByID, null, HttpStatus.OK); 
//            
//        }
//        else {
//            mpResponse = new ResponseEntity<Object>(mp , null, HttpStatus.NOT_FOUND);  
//            
//        }
//        
//		return mpResponse;
//    }
//	
	@CrossOrigin
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Object[]> reada(@PathVariable(value = "id") Long id) {
        
        
        Object mp1 = mobilePlanDao.findById(id);
        if(mp1 == null)
        {
        	Object []mp2 = {};
        	
        	ResponseEntity<Object[]> mpResponse1 = new ResponseEntity<Object[]>(mp2, null, HttpStatus.OK);
        	
        	return mpResponse1;
        }else {
        	Object []mp2 = {mp1};
        	
        	ResponseEntity<Object[]> mpResponse1 = new ResponseEntity<Object[]>(mp2, null, HttpStatus.OK);
        	
        	return mpResponse1;
        }        
    }
	
	@CrossOrigin
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<MobilePlan>> read(@PathVariable(value = "name") String name) {
        
        Iterable<MobilePlan> mobilePlanList = mpSrvc.findByName(name);
        
        ResponseEntity<Iterable<MobilePlan>> mpResponse = new ResponseEntity<Iterable<MobilePlan>>(mobilePlanList, null, HttpStatus.OK);
        
        return mpResponse;
        
    }
	
	@CrossOrigin
	@RequestMapping(value = "/description/{description}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<MobilePlan>> read1(@PathVariable(value = "description") String description) {
        
        Iterable<MobilePlan> mobilePlanList = mobilePlanDao.findByDescription(description);
        
        ResponseEntity<Iterable<MobilePlan>> mpResponse = new ResponseEntity<Iterable<MobilePlan>>(mobilePlanList, null, HttpStatus.OK);
        
        return mpResponse;
        
    }
	
	@CrossOrigin
	@RequestMapping(value = "/validity/{validity}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<MobilePlan>> read1(@PathVariable(value = "validity") int validity) {
        
        Iterable<MobilePlan> mobilePlanList = mobilePlanDao.findByValidity(validity);
        
        ResponseEntity<Iterable<MobilePlan>> mpResponse = new ResponseEntity<Iterable<MobilePlan>>(mobilePlanList, null, HttpStatus.OK);
        
        return mpResponse;
        
    }
	
//	readAll - This function is used to get the list of all mobileplan exists inside database.
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<MobilePlan>> readAll() {
        ResponseEntity<Iterable<MobilePlan>> mpResponse;
        
        TimeZone.setDefault(TimeZone.getTimeZone("IST"));
    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
        Iterable<MobilePlan> mobilePlanList = mpSrvc.readAll();
        
        mpResponse = new ResponseEntity<Iterable<MobilePlan>>(mobilePlanList, null, HttpStatus.OK);
        
        return mpResponse;
    }
	
//	update - This function is used to update a mobileplan for the respective ID.	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Object> update(@RequestBody MobilePlan tobemerged) {
        ResponseEntity<Object> planResponse;
        
        
        TimeZone.setDefault(TimeZone.getTimeZone("IST"));
    	SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
 	   
    	MobilePlan curr_mp = mpSrvc.read1(tobemerged.getId());
    	
    	String output = "Plan ID: " + curr_mp.getId() + " UPDATED";
    	output += "<br>";
    	output += "Previous Fields: "  + "[";
		output += "Name:"  + curr_mp.getName()+", ";
		output += "Validity:"  + curr_mp.getValidity()+", ";
		output += "Description:"  + curr_mp.getDescription()+ "]" +"<br>";
    	String newMessage = "Updated Fields: [", newValidity, newName, newDescription;
    	
    	if(!(curr_mp.getName().equals(tobemerged.getName()))) {
    		newName = tobemerged.getName();
    		
    		newMessage += "Name:" + newName + ", ";
    	}
    	
    	if(curr_mp.getValidity() != tobemerged.getValidity() ) {
    		newValidity = String.valueOf(tobemerged.getValidity());
    		
    		newMessage += "Validity : " + newValidity + ", " ;
    	}

    	if( !(curr_mp.getDescription().equals(tobemerged.getDescription()))) {
    		newDescription = tobemerged.getDescription();
    		
    		newMessage += "Description:" + newDescription;
    	}
    	newMessage += "]";
    	output += newMessage;
        Object updatedMobilePlan = mpSrvc.update(tobemerged);
        if(updatedMobilePlan != null) {
        	planResponse = new ResponseEntity<Object>(URI, null, HttpStatus.OK);
        	
        	HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new 
        	Auditlog("UPDATE",output,f.format(new Date())));
    		restTemplate.postForObject(URI, request, Auditlog.class);
        }else {
			planResponse =  new ResponseEntity<Object>(ID_NOT_EXIST, null, HttpStatus.BAD_REQUEST);
			
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new
			Auditlog("UPDATE",newMessage,f.format(new Date())));
			restTemplate.postForObject(URI, request, Auditlog.class);
        }
        
        return planResponse;
     }
	
//	delete - This function is used to delete a mobileplan for given ID.
	@CrossOrigin
	@RequestMapping(value = "{planid}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable(value = "planid") Long planid) {
		ResponseEntity<Object> planResponse = null;
		
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
		SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Object deletedMobilePlan = mpSrvc.delete(planid);
		if(deletedMobilePlan != null) {
			String response = "Data deleted successfully";
			
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new 
			Auditlog("DELETE","Plan ID: " + planid + " DELETED",f.format(new Date())));
			restTemplate.postForObject(URI, request, Auditlog.class);
			
			planResponse =  new ResponseEntity<Object>(response, null, HttpStatus.OK);
		}else {
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new
			Auditlog("DELETE","Given ID: " + planid + " Does Not Exist.",f.format(new Date())));
			restTemplate.postForObject(URI, request, Auditlog.class);
			
			planResponse =  new ResponseEntity<Object>(ID_NOT_EXIST, null, HttpStatus.NOT_FOUND);
		}
		
		return planResponse;
	}
}