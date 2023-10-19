package com.vsquad.iroas.service.auth;

import com.vsquad.iroas.aggregate.entity.Player;
import com.vsquad.iroas.config.token.PlayerPrincipal;
import com.vsquad.iroas.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final PlayerRepository playerRepository;


    @Override
    public UserDetails loadUserByUsername(String steamKey) throws UsernameNotFoundException {

        Player player = playerRepository.findByPlayerSteamKey(steamKey);

        if(player == null) {
            throw new UsernameNotFoundException("플레이어 정보를 찾을 수 없습니다.");
        }

        return PlayerPrincipal.create(player);
    }

    @Transactional
    public UserDetails loadPlayerById(Long id) throws Exception {
        Optional<Player> player = playerRepository.findById(id);
        if(player.isPresent()) {
            return PlayerPrincipal.create(player.get());
        } else {
            throw new Exception("회원이 없음");
        }
    }
}