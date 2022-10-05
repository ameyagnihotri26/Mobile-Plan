package com.hansen.mobileplan.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hansen.mobileplan.model.MobilePlan;

public interface MobilePlanDao extends PagingAndSortingRepository<MobilePlan, Long> {
	@Query(value = "SELECT * FROM mobile_plan Where name LIKE %?% ", nativeQuery = true)
	Iterable<MobilePlan> findByName(String keyword);
	
	
	@Query(value = "SELECT * FROM mobile_plan Where description LIKE %?% ", nativeQuery = true)
	Iterable<MobilePlan> findByDescription(String keyword);
	
	@Query(value = "SELECT * FROM mobile_plan Where validity LIKE %?% ", nativeQuery = true)
	Iterable<MobilePlan> findByValidity(int keyword);

}