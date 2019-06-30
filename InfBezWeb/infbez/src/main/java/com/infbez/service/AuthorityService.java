package com.infbez.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infbez.model.Authority;
import com.infbez.repository.AuthorityRepository;



@Service
public class AuthorityService implements AuthorityServiceInterface {
	
	@Autowired
	AuthorityRepository authorityRepository;

	@Override
	public Authority findByName(String name) {
		return authorityRepository.findByName(name);
	}
	
	

	
}

