package com.vsquad.iroas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquad.iroas.aggregate.dto.EnemyDto;
import com.vsquad.iroas.aggregate.dto.EnemySpawnerDto;
import com.vsquad.iroas.aggregate.dto.PropDto;
import com.vsquad.iroas.aggregate.dto.ReqCreatorMapDto;
import com.vsquad.iroas.aggregate.entity.CreatorMap;
import com.vsquad.iroas.repository.CreatorMapRepository;
import com.vsquad.iroas.repository.EnemyRepository;
import com.vsquad.iroas.repository.EnemySpawnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CreatorMapServiceTest {

    @Autowired
    private CreatorMapRepository creatorMapRepository;

    @Autowired
    private EnemySpawnerRepository enemySpawnerRepository;

    @Mock
    private CreatorMap creatorMap;

    @BeforeTransaction
    void beforeTransaction() throws JsonProcessingException {

        // given
        String uuid = UUID.randomUUID().toString();

        EnemyDto enemyDto = new EnemyDto("close_range_0", "근거리1", "Melee", 100L, 10L);

        List<Float> enemyStartPoint1 = new ArrayList<>();
        enemyStartPoint1.addAll(List.of(100.00F, 160.00F, 90.00F));

        List<EnemySpawnerDto> enemySpawnerList = new ArrayList<>();
        enemySpawnerList.addAll(List.of(
                new EnemySpawnerDto("근접 에네미 스포너", enemyStartPoint1, 100, 10F, 10F, enemyDto)
        ));

        List<PropDto> propList = new ArrayList<>();
        propList.addAll(List.of(
                new PropDto("prop1", "prop", List.of(100.00F, 160.00F, 90.00F)),
                new PropDto("prop2", "prop", List.of(100.00F, 160.00F, 90.00F)),
                new PropDto("prop3", "prop", List.of(100.00F, 160.00F, 90.00F))
        ));

        List<Float> startPoint = new ArrayList<>();
        startPoint.addAll(List.of(100.00F, 160.00F, 90.00F));

        ReqCreatorMapDto mapDto = new ReqCreatorMapDto(uuid, "testMap", "MELEE", 1L, LocalDateTime.now(),
                startPoint, "Morning", enemySpawnerList, propList);

        CreatorMap map = mapDto.convertToEntity(mapDto);

        creatorMap = creatorMapRepository.save(map);

        addCreatorMapSuccessTest();
    }

    @AfterTransaction
    void afterTransaction() {
        creatorMapRepository.deleteById(creatorMap.getCreatorMapId());
    }

    @Test
    @DisplayName("크리에이터 맵 추가 성공")
    void addCreatorMapSuccessTest() throws JsonProcessingException {

        // given
        String uuid = UUID.randomUUID().toString();

        EnemyDto enemyDto = new EnemyDto("close_range_1", "근거리1", "Melee", 100L, 10L);

        List<Float> enemyStartPoint1 = new ArrayList<>();
        enemyStartPoint1.addAll(List.of(100.00F, 160.00F, 90.00F));

        List<EnemySpawnerDto> enemySpawnerList = new ArrayList<>();
        enemySpawnerList.addAll(List.of(
                new EnemySpawnerDto("근접 에네미 스포너", enemyStartPoint1, 100, 10F, 10F, enemyDto)
        ));

        List<PropDto> propList = new ArrayList<>();
        propList.addAll(List.of(
                new PropDto("prop1", "prop", List.of(100.00F, 160.00F, 90.00F)),
                new PropDto("prop2", "prop", List.of(100.00F, 160.00F, 90.00F)),
                new PropDto("prop3", "prop", List.of(100.00F, 160.00F, 90.00F))
        ));

        List<Float> startPoint = new ArrayList<>();
        startPoint.addAll(List.of(100.00F, 160.00F, 90.00F));

        ReqCreatorMapDto mapDto = new ReqCreatorMapDto(uuid, "myAwesomeMap", "MELEE", 1L, LocalDateTime.now(),
                startPoint, "Morning", enemySpawnerList, propList);

        CreatorMap map = mapDto.convertToEntity(mapDto);

        // when
        creatorMapRepository.save(map);

        // then
        CreatorMap foundMap = creatorMapRepository.findById(map.getCreatorMapId())
                .orElseThrow(() -> new IllegalArgumentException("맵을 찾을 수 없습니다."));

        assertNotNull(foundMap);
    }

    @Test
    @DisplayName("크리에이터 맵 조회 성공")
    void readCreatorMapSuccessTest() {

        // given
        String id = creatorMap.getCreatorMapId();

        // when
        CreatorMap foundMap = creatorMapRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("맵을 찾을 수 없습니다."));

        // then
        assertNotNull(foundMap);
    }
}