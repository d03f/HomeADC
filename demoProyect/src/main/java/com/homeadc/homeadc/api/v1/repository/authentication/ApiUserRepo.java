package com.homeadc.homeadc.api.v1.repository.authentication;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.homeadc.homeadc.api.v1.model.authentication.ApiUser;

@Repository
public interface ApiUserRepo  extends JpaRepository<ApiUser, String>{ 
	@Query("SELECT COUNT(u) > 0 FROM ApiUser u WHERE u.userAccountKey = :key AND u.accountEnabled = TRUE")
    boolean existsByUserAccountKeyAndAccountEnabledTrue(@Param("key") String userAccountKey);
	
	 @Query("SELECT u FROM ApiUser u WHERE u.userName = :username")
    Optional<ApiUser> findUserByUsernameWithQuery(@Param("username") String username);




}
