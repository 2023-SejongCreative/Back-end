package com.example.Waffle.controller;

import com.example.Waffle.dto.PlanDto;
import com.example.Waffle.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;


    @PostMapping("/plan/{type}/{type_id}/create")
    public ResponseEntity<Object> createPlan(@RequestBody Map<String, String> param,
                                             @PathVariable("type") String type,
                                             @PathVariable("type_id") Long typeId){


        PlanDto planDto = new PlanDto(param.get("title"), param.get("content"));

        planDto.setStartDate(param.get("start_date"));
        planDto.setEndDate(param.get("end_date"));
        planDto.stringToIntState(param.get("state"));
        planService.createPlan(planDto,type,typeId);


        return new ResponseEntity<>("일정이 생성되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/plan/{type}/{type_id}/list")
    @ResponseBody
    public ResponseEntity<Object> planList(@PathVariable("type") String type,
                                           @PathVariable("type_id") Long typeId){

        String planList = planService.planList(type,typeId);

        return new ResponseEntity<>(planList, HttpStatus.OK);
    }


    @PostMapping("plan/{plan_id}/update")
    public ResponseEntity<Object> updatePlan(@RequestBody Map<String, String> param,
                                             @PathVariable("plan_id") Long planId){



        PlanDto planDto = new PlanDto(param.get("title"), param.get("content"));
        planDto.setStartDate(param.get("start_date"));
        planDto.setEndDate(param.get("end_date"));
        planDto.stringToIntState(param.get("state"));

        planService.updatePlan(planDto,planId);

        return new ResponseEntity<>("일정이 수정되었습니다." ,HttpStatus.OK);
    }

    @DeleteMapping("plan/{plan_id}/delete")
    public ResponseEntity<Object> deletePlan(@PathVariable("plan_id") Long planId){

        planService.deletePlan(planId);

        return new ResponseEntity<>("일정이 삭제되었습니다.", HttpStatus.OK);
    }

}
