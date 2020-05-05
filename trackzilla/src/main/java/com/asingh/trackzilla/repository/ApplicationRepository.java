package com.asingh.trackzilla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asingh.trackzilla.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
