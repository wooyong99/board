package com.bordserver.jwy.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class UpdatePasswordRequest {
    @NonNull
    private String beforePassword;
    @NonNull
    private String newPassword;
}
