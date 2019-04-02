package com.cognibank.securityMicroservice.Repository;

import com.cognibank.securityMicroservice.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends CrudRepository<User,String> {
}
