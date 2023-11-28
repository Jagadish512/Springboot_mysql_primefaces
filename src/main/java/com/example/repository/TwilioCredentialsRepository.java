package com.example.repository;

import com.example.model.Role;
import com.example.model.TwilioCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TwilioCredentialsRepository extends JpaRepository<TwilioCredentials, Long> {


}
