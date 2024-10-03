package com.gmf.demencia_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmf.demencia_api.domain.test.Test;

public interface TestRepository extends JpaRepository<Test, String>{
}
