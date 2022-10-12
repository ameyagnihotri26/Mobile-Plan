package com.hansen.mobileplan.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hansen.mobileplan.model.LoginPage;


public interface LoginPageDao extends PagingAndSortingRepository<LoginPage, Long>{
	
	
	
}
