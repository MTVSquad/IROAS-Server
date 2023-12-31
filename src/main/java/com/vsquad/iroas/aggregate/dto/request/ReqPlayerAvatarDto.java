package com.vsquad.iroas.aggregate.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqPlayerAvatarDto {

    private Long playerId;

    private String maskColor;
}
