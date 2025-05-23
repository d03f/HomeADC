package com.homeadc.homeadc.api.v1.service.data;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.homeadc.homeadc.api.v1.model.authentication.ApiUser;
import com.homeadc.homeadc.api.v1.model.data.DataUnit;
import com.homeadc.homeadc.api.v1.model.data.dto.DataUnitDTO;
import com.homeadc.homeadc.api.v1.model.exceptions.AccessDeniedCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.DuplicatedEntryCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidDataCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.homeadc.homeadc.api.v1.repository.authentication.ApiUserRepo;
import com.homeadc.homeadc.api.v1.repository.data.DataUnitRepo;
import com.homeadc.homeadc.api.v1.service.authentication.UserRoleManager;

@Service
public class DataUnitService {

	private DataUnitRepo dataUnitRepo;
	private ApiUserRepo userRepo;
	private UserRoleManager accessService;
	
	public DataUnitService(DataUnitRepo dataUnitRepo, ApiUserRepo userRepo, UserRoleManager accessService) {
		this.dataUnitRepo = dataUnitRepo;
		this.userRepo = userRepo;
		this.accessService = accessService;
	}
	
	public DataUnitDTO createNewDataUnit(String authorizationToken,  Map<String, String> requestData) throws InvalidUserAccountKeyCustEx, InvalidDataCustEx, DuplicatedEntryCustEx, AccessDeniedCustEx {
		ApiUser usedUser = this.userRepo.findById(authorizationToken).orElseThrow(InvalidUserAccountKeyCustEx::new);
		accessService.canUserWrite(usedUser);
		
		DataUnit created = new DataUnit();
		
		String symbol = requestData.get("symbol"); 	created.setSymbol(symbol); 	if (symbol == null ) { throw new InvalidDataCustEx(); }
		String name = requestData.get("name");		created.setName(name);		if (name == null) { throw new InvalidDataCustEx(); }
		
		if (this.dataUnitRepo.findByName(created.getName()).isPresent() || 
				this.dataUnitRepo.findBySymbol(created.getSymbol()).isPresent()) { throw new DuplicatedEntryCustEx(); }
		
		return new DataUnitDTO( this.dataUnitRepo.save(created) );			
	}

	public List<DataUnitDTO> getAllDataUnits() {
		return this.dataUnitRepo.findAll().stream().map(DataUnitDTO::new).toList();
	}
	
}
