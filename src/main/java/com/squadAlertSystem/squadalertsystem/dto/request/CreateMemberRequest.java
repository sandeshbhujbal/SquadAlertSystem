package com.squadAlertSystem.squadalertsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberRequest {
    private String id;
    private String name;
    private String email;
    private String phone;
}
