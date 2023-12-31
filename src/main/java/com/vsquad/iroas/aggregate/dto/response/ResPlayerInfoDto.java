package com.vsquad.iroas.aggregate.dto.response;

import com.vsquad.iroas.aggregate.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResPlayerInfoDto {

    private Long playerId;

    private String playerSteamKey;

    private String playerNickName;

    private String playerType;

    private String playerRole;

    public static ResPlayerInfoDto convertToDto(Player player) {

        return new ResPlayerInfoDto(player.getPlayerId(), player.getKey(), player.getNickname().getPlayerNickname(),
                player.getType(), player.getPlayerRole());
    }
}
