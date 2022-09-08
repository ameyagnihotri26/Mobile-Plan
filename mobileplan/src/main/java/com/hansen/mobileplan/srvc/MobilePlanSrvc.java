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

	public Object create(MobilePlan entity) {
		Long curr_id = entity.getId();
		
		if(curr_id == null)
		{
			return null;
		}else {
			Optional<MobilePlan> mobilePlanToBeUpdated  = mobilePlanDao.findById(entity.getId());
			
			if(mobilePlanToBeUpdated.isPresent()) {
				return null ;
			}else {
				MobilePlan mobileplan = mobilePlanDao.save(entity);
				
				return mobileplan;
			}
		}
	}

	public Object read(Long id) {
		Optional<MobilePlan> person = mobilePlanDao.findById(id);
		if (person.isPresent()) {
			return person.get();
		}else {
			return null;
		}
	}

	public Iterable<MobilePlan> readAll() {
		Iterable<MobilePlan> mobilePlanList = mobilePlanDao.findAll();
		return mobilePlanList;
	}

	public Object update(MobilePlan tobemerged) {
		Optional<MobilePlan> mobilePlanToBeUpdated  = mobilePlanDao.findById(tobemerged.getId());
		
		if(mobilePlanToBeUpdated.isPresent()) {
			MobilePlan mobileplan = mobilePlanDao.save(tobemerged);
			
			return mobileplan;
		}else {
			return null;
		}
	}

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
