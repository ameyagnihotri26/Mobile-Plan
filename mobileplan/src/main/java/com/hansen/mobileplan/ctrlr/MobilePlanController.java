package com.hansen.mobileplan.ctrlr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hansen.mobileplan.dao.MobilePlanDao;
import com.hansen.mobileplan.model.MobilePlan;
import com.hansen.mobileplan.srvc.MobilePlanSrvc;

@RestController
@RequestMapping("/mp")
public class MobilePlanController {
	@Autowired
	MobilePlanSrvc mpSrvc;

	@Autowired
	MobilePlanDao mobilePlanDao;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody MobilePlan inputentity) {
		ResponseEntity<Object> mpResponse;
		
		Object mobilePlan = mpSrvc.create(inputentity);
		if (mobilePlan != null) {
			String createresponse = "New mobile plan added successfully";
			
			mpResponse = new ResponseEntity<Object>(createresponse, null, HttpStatus.CREATED);
			return mpResponse;
		} else {
			String response = "Cant save as ID is already exist";
        	
            mpResponse = new ResponseEntity<Object>(response , null, HttpStatus.BAD_REQUEST);  
			return mpResponse;
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> read(@PathVariable(value = "id") Long id) {
        ResponseEntity<Object> mpResponse = null;

        Object mobilePlan = mpSrvc.read(id);
        
        if( mobilePlan != null) {
            mpResponse = new ResponseEntity<Object>(mobilePlan, null, HttpStatus.OK);            
        }
        else {
        	String response = "Mobile plan does not exist for given ID";
        	
            mpResponse = new ResponseEntity<Object>(response , null, HttpStatus.NOT_FOUND);    
        }
        
		return mpResponse;
    }
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<MobilePlan>> readAll() {
        ResponseEntity<Iterable<MobilePlan>> mpResponse;
        
        Iterable<MobilePlan> mobilePlanList = mpSrvc.readAll();
        
        mpResponse = new ResponseEntity<Iterable<MobilePlan>>(mobilePlanList, null, HttpStatus.OK);
        return mpResponse;
    }
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Object> update(@RequestBody MobilePlan tobemerged) {
        ResponseEntity<Object> planResponse;
        
        Object plan = mpSrvc.update(tobemerged);
        
        if(plan != null) {
        	planResponse = new ResponseEntity<Object>("Data updated Successfully", null, HttpStatus.OK);
        	return planResponse;
        }else {
        	String response = "Given ID does not exist";
			
			planResponse =  new ResponseEntity<Object>(response, null, HttpStatus.BAD_REQUEST);
			return planResponse;
        }
     }	
	
	@CrossOrigin
	@RequestMapping(value = "{planid}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable(value = "planid") Long planid) {
		ResponseEntity<Object> planResponse = null;
		
		Object delete = mpSrvc.delete(planid);
		
		if(delete != null) {
			String response = "Data deleted successfully";
			
			planResponse =  new ResponseEntity<Object>(response, null, HttpStatus.OK);
			return planResponse;
		}else {
			String response = "ID not found";
			
			planResponse =  new ResponseEntity<Object>(response, null, HttpStatus.NOT_FOUND);
			return planResponse;
		}
	}

}