package com.homeadc.homeadc.api.v1.service.authentication;

import org.springframework.stereotype.Service;

import com.homeadc.homeadc.api.v1.model.authentication.ApiKey;
import com.homeadc.homeadc.api.v1.model.authentication.ApiUser;
import com.homeadc.homeadc.api.v1.model.authentication.UserRole;
import com.homeadc.homeadc.api.v1.model.exceptions.AccessDeniedCustEx;

@Service
public class UserRoleManager {

	
	public boolean canUserWrite(ApiUser user) throws AccessDeniedCustEx {
		if (user.getRole() == UserRole.READER) {
			throw new AccessDeniedCustEx();
		}
		
		return true;
		
	}
	
	public boolean canUserRead(ApiUser user) throws AccessDeniedCustEx {
		if (user.getRole() == UserRole.WRITER) {
			throw new AccessDeniedCustEx();
		}
		
		return true;
		
	}
	
	public boolean canKeyRead(ApiKey apikey) throws AccessDeniedCustEx {
		if (apikey.getAccess() == UserRole.WRITER) {
			throw new AccessDeniedCustEx();
		}
		
		return true;
	}
	
	public boolean canKeyWrite(ApiKey apikey) throws AccessDeniedCustEx {
		if (apikey.getAccess() == UserRole.READER) {
			throw new AccessDeniedCustEx();
		}
		return true;
	}
}
