package com.cognibank.securityMicroservice.Repository;

import com.cognibank.securityMicroservice.Model.UserDetails;
import org.springframework.data.repository.CrudRepository;

public interface UserDetailsRepository  extends CrudRepository<UserDetails,Long> {
}
