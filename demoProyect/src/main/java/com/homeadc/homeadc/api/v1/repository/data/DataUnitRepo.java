package com.homeadc.homeadc.api.v1.repository.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.homeadc.homeadc.api.v1.model.data.DataUnit;


@Repository
public interface DataUnitRepo  extends JpaRepository<DataUnit, String>{
	Optional<DataUnit> findBySymbol(String symbol);
	Optional<DataUnit> findByName(String name);
	 Optional<DataUnit> findBySymbolOrName(String symbol, String name);
}
