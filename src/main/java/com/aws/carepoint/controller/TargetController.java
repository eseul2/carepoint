package com.aws.carepoint.controller;

import com.aws.carepoint.dto.TargetDto;
import com.aws.carepoint.service.ExerciseService;
import com.aws.carepoint.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Target;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/target")
public class TargetController {

    private final TargetService targetService;

    @Autowired
    public TargetController(TargetService targetService) { // ✅ 생성자로 주입
        this.targetService = targetService;
    }

    @ResponseBody
    @PostMapping("/saveTarget")
    public ResponseEntity<Map<String, String>> saveTarget(@RequestBody TargetDto targetDto) {
        targetService.saveTarget(targetDto);

        // 응답을 JSON 형태로 반환
        Map<String, String> response = new HashMap<>();
        response.put("message", "이번 주 목표가 저장되었습니다.");

        return ResponseEntity.ok(response);
    }

    // 이번주 목표 데이터 가져오기
    @GetMapping("/current-week")
    public ResponseEntity<TargetDto> getCurrentWeekTarget(@RequestParam("userPk") int userPk) {
        TargetDto targetDto = targetService.getCurrentWeekTarget(userPk);
        if (targetDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(targetDto);
    }

    // ✅ target_count 증가
    @ResponseBody
    @PostMapping("/update-target-count")
    public ResponseEntity<?> updateTargetCount(@RequestParam("userPk") int userPk, @RequestParam("action") String action) {

        int change = action.equals("increase") ? 1 : -1;
        boolean updated = targetService.updateTargetCount(userPk, change);

        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "User not found or target update not required"));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Target count updated successfully"));
    }

    // 이번 주 target_count 업데이트 여부 체크
    @GetMapping("/has-updated-this-week")
    public ResponseEntity<Boolean> hasUpdatedThisWeek(@RequestParam("userPk") int userPk) {
        boolean hasUpdated = targetService.hasUpdatedThisWeek(userPk);
        System.out.println("hasUpdated ================================== " + hasUpdated);
        return ResponseEntity.ok(hasUpdated);
    }

    // 해당 회원이 운동 기록한 횟수 가져오기
    @ResponseBody
    @GetMapping("/count")
    public int getTargetCount(@RequestParam("userPk") int userPk) {
        return targetService.getTargetCount(userPk);
    }

}
