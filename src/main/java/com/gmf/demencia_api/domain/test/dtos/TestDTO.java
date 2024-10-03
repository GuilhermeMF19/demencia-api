package com.gmf.demencia_api.domain.test.dtos;

import com.gmf.demencia_api.domain.test.Test;
import com.gmf.demencia_api.domain.test.enums.TestType;

import jakarta.validation.constraints.NotBlank;

public class TestDTO {
	
    @NotBlank
    private TestType type;
    @NotBlank
    private String result;

    public TestDTO() {}
    
    public TestDTO(Test test) {
        this.type = test.getType();
        this.result = test.getResult();
    }

    public TestDTO(TestType type, String result) {
        this.type = type;
        this.result = result;
    }

    // Getters and setters
    public TestType getType() {
        return type;
    }

    public void setType(TestType type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
