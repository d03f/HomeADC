package com.example.demoProyect.api.v1.repository.specifications;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.example.demoProyect.api.v1.model.data.SensorRecord;

public class SensorRecordSpecifications {

	public static Specification<SensorRecord> byName(String name) {
        return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("sensor").get("name"), name);
    }
	
	public static Specification<SensorRecord> byValueGreaterThanOrEqualTo(BigDecimal minValue) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("value"), minValue);
    }
	
	public static Specification<SensorRecord> byValueLessThanOrEqualTo(BigDecimal maxValue) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("value"), maxValue);
    }
	
	public static Specification<SensorRecord> byTimestampGreaterThanOrEqualTo(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), startDate);
    }
	
	public static Specification<SensorRecord> byTimestampLessThanOrEqualTo(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), startDate);
    }
	
	 public static Specification<SensorRecord> byMetadataContains(String metadataSubstring) {
		 return (root, query, criteriaBuilder) ->
         criteriaBuilder.like( root.get("metadata"),("%"+metadataSubstring+"%") );
	 }
}
