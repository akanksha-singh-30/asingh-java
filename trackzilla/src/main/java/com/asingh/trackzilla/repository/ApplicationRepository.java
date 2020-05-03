package com.asingh.trackzilla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asingh.trackzilla.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
