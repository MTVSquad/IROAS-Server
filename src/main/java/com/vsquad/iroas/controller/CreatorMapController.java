package com.vsquad.iroas.controller;

import com.vsquad.iroas.aggregate.dto.response.ResCreatorMapDto;
import com.vsquad.iroas.aggregate.dto.request.ReqCreatorMapDto;
import com.vsquad.iroas.aggregate.dto.response.*;
import com.vsquad.iroas.service.CreatorMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/maps")
@Slf4j
@Tag(name = "맵 API")
public class CreatorMapController {

    private final CreatorMapService creatorMapService;

    @PostMapping
    @Operation(summary = "크리에이터 맵 추가", description = "크리에이터 맵을 추가합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "맵 추가 성공", content = @Content(schema = @Schema(implementation = ResMessageDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "맵 추가 실패", content = @Content(schema = @Schema(implementation = ResErrorMessage.class), mediaType = "application/json"))
    })
    public ResponseEntity<ResMessageDto> addCreatorMap(@RequestBody ReqCreatorMapDto reqDto) {
        try {

            log.info("크리에이터 맵 추가");
            creatorMapService.addCreatorMap(reqDto);
            ResMessageDto resDto = new ResMessageDto("새로운 맵 추가 완료");

            log.info("새로운 맵 추가 완료");

            return new ResponseEntity<>(resDto, HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn("맵 추가 실패");

            return ResponseEntity.badRequest().body(new ResMessageDto("맵 추가 실패"));
        }
    }

    @GetMapping("/{creatorMapId}")
    @Operation(summary = "크리에이터 맵 조회", description = "크리에이터 맵을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "맵 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "맵 조회 실패", content = @Content(schema = @Schema(implementation = ResponseError.class), mediaType = "application/json"))
    })
    public ResponseEntity<ResponseDto<ResCreatorMapDto>> getCreatorMap(@PathVariable Long creatorMapId) {
        try {
            log.info("맵 조회 ::{}", creatorMapId + "의 맵");

            ResCreatorMapDto resDto = creatorMapService.getCreatorMap(creatorMapId);

            ResponseDto res = new ResponseDto(resDto, "맵 조회 성공");

            log.info("맵 조회 성공");

            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("맵 조회 실패");
            log.warn(e.getMessage());

            ResponseDto res = new ResponseDto(null, "맵 조회 실패");
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping
    @Operation(summary = "크리에이터 맵 목록 조회", description = "정렬 값, 정렬 순서, 페이지 당 요소 수, 페이지 번호를 입력, 조건에 맞는 요소 목록 조회", responses = {
            @ApiResponse(responseCode = "200", description = "맵 목록 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "맵 목록 조회 실패", content = @Content(schema = @Schema(implementation = ResponseError.class), mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "size", description = "화면에 보여줄 요소의 숫자를 결정합니다.", example = "10"),
            @Parameter(name = "offset", description = "해당 페이지 첫 번째 원소의 수", example = "1"),
            @Parameter(name = "sort", description = "정렬 기준", schema = @Schema(allowableValues = {"createTime", "creatorMapId", "creatorMapName", "creatorId"}),
                    example = "createTime", in = ParameterIn.QUERY),
            @Parameter(name = "direction", description = "정렬 방향", schema = @Schema(allowableValues = {"asc", "desc"}), example = "asc")
    })
    public ResponseEntity<ResponseDto<Page<ResCreatorMapDto>>> getCreatorMapList(@PageableDefault(sort = {"createTime"}, direction = Sort.Direction.ASC) @Parameter(hidden = true) Pageable pageable) {
        try {
            log.info("맵 목록 조회");

            Page<ResCreatorMapDto> resDto = creatorMapService.readPlayerCreatorMapList(pageable);
            ResponseDto res = new ResponseDto(resDto, "맵 목록 조회 성공");

            log.info("맵 목록 조회 성공");

            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("맵 목록 조회 실패");

            ResponseDto res = new ResponseDto(null, "맵 목록 조회 실패");
            return ResponseEntity.badRequest().body(res);
        }
    }

    @DeleteMapping("/{creatorMapId}")
    @Operation(summary = "크리에이터 맵 제거", description = "맵을 삭제 합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "맵 제거 성공", content = @Content(schema = @Schema(implementation = ResMessageDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "맵 제거 실패", content = @Content(schema = @Schema(implementation = ResErrorMessage.class), mediaType = "application/json"))
    })
    public ResponseEntity<ResMessageDto> deleteCreatorMap(@PathVariable Long creatorMapId) {
        try {
            log.info("맵 삭제 ::{}", creatorMapId + "의 맵");

            creatorMapService.deleteCreatorMap(creatorMapId);

            log.info("맵 삭제 성공");

            ResMessageDto res = new ResMessageDto("맵 삭제 성공");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            String reason = "크리에이터 본인만 제거 가능";
            log.warn(reason);

            ResMessageDto res = new ResMessageDto("맵 삭제 실패, 파라미터 확인 필요");
            return ResponseEntity.badRequest().body(res);
        } catch (Exception e) {
            log.warn("맵 삭제 실패");

            ResMessageDto res = new ResMessageDto("맵 삭제 실패");
            return ResponseEntity.badRequest().body(res);
        }
    }
}