package com.gmf.demencia_api.domain.user.dtos;

import com.gmf.demencia_api.domain.user.enums.UserRole;

public record RegisterDTO(String login, String password, String fullName, UserRole role) {
}
