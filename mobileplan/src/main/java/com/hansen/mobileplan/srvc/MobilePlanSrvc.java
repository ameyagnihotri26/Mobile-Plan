package com.hansen.mobileplan.srvc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansen.mobileplan.dao.MobilePlanDao;
import com.hansen.mobileplan.model.MobilePlan;

@Service
public class MobilePlanSrvc {

	@Autowired
	MobilePlanDao mobilePlanDao;
	
//	create - this function creates a mobileplan and save it inside our database.
	public Object create(MobilePlan entity) {
		MobilePlan mobileplan = mobilePlanDao.save(entity);
		
		return mobileplan;
	}

//	read - this function reads a mobileplan by its ID from our database.
	public Object read(Long id) {
		Optional<MobilePlan> currentMobilePlan = mobilePlanDao.findById(id);
		if (currentMobilePlan.isPresent()) {
			return currentMobilePlan.get();
		}else {
			return null;
		}
	}

	public Iterable<MobilePlan> readAll() {
		Iterable<MobilePlan> mobilePlanList = mobilePlanDao.findAll();
		
		return mobilePlanList;
	}

//	update - this function updates an existent mobileplan in our database.
	public Object update(MobilePlan tobemerged) {
		Optional<MobilePlan> mobilePlanToBeUpdated  = mobilePlanDao.findById(tobemerged.getId());
		if(mobilePlanToBeUpdated.isPresent()) {
			MobilePlan mobileplan = mobilePlanDao.save(tobemerged);
			
			return mobileplan;
		}else {
			return null;
		}
	}

//	delete - this function deletes a mobileplan by its ID from our database.
	public Object delete(Long planid) {
		Optional<MobilePlan> currentMobilePlan = mobilePlanDao.findById(planid);
		if (currentMobilePlan.isPresent()) {
			mobilePlanDao.deleteById(planid);
			
			return currentMobilePlan;
		} else {
			return null;
		}
	}
}
