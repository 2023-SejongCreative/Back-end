package com.example.Waffle.service;

import com.example.Waffle.dto.PlanDto;
import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.PlanEntity;
import com.example.Waffle.entity.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.GroupRepository;
import com.example.Waffle.repository.PlanRepository;
import com.example.Waffle.repository.RoomRepository;
import com.example.Waffle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoomRepository roomRepository;
    private final PlanRepository planRepository;

    @Transactional
    public void createPlan(PlanDto planDto, String type, Long typeId) {


        if (type.equals("home")) {
            UserEntity user = userRepository.findById(typeId)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

            planDto.setUser(user);
            planDto.setGroup(null);
            planDto.setRoom(null);

            PlanEntity planEntity = planDto.toEntity();
            planRepository.save(planEntity);
        } else if (type.equals("group")) {
            GroupEntity group = groupRepository.findById(typeId)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_GROUP));

            planDto.setUser(null);
            planDto.setGroup(group);
            planDto.setRoom(null);

            PlanEntity planEntity = planDto.toEntity();
            planRepository.save(planEntity);
        } else if (type.equals("room")) {
            RoomEntity room = roomRepository.findById(typeId)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_ROOM));

            planDto.setUser(null);
            planDto.setGroup(null);
            planDto.setRoom(room);

            PlanEntity planEntity = planDto.toEntity();
            planRepository.save(planEntity);
        }

    }


    @Transactional
    public String planList(String type, Long typeId) {

        JSONObject extended = new JSONObject();
        extended.put("department", "");

        if (type.equals("home")) {
            UserEntity user = userRepository.findById(typeId)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_USER));
            JSONObject planList = new JSONObject();
            try{
                List<PlanEntity> planEntities = planRepository.findAllByUserId(typeId);
                JSONArray planArr = new JSONArray();
                for(PlanEntity planEntity : planEntities){
                    JSONObject plan = new JSONObject();
                    plan.put("id", planEntity.getId());
                    plan.put("title", planEntity.getTitle());
                    plan.put("content", planEntity.getContent());
                    plan.put("start", planEntity.getStart_date());
                    plan.put("end",planEntity.getEnd_date());
                    String state;
                    if (planEntity.getState() == null)
                        state = null;
                    else if(planEntity.getState() == 0)
                        state = "미완료";
                    else if(planEntity.getState() == 1)
                        state = "완료";
                    else if(planEntity.getState() == 2)
                        state = "진행중";
                    else state = null;
                    plan.put("state",state);

                    plan.put("extendedProps", extended);
                    planArr.put(plan);
                }
                planList.put("plans", planArr);
            }catch (Exception e){
                throw new UserException(ErrorCode.CANT_FIND_PLAN);
            }
            return planList.toString();
        } else if (type.equals("group")) {
            GroupEntity group = groupRepository.findById(typeId)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_GROUP));
            JSONObject planList = new JSONObject();
            try{
                List<PlanEntity> planEntities = planRepository.findAllByGroupId(typeId);
                JSONArray planArr = new JSONArray();
                for(PlanEntity planEntity : planEntities){
                    JSONObject plan = new JSONObject();
                    plan.put("id", planEntity.getId());
                    plan.put("title", planEntity.getTitle());
                    plan.put("content", planEntity.getContent());
                    plan.put("start", planEntity.getStart_date());
                    plan.put("end",planEntity.getEnd_date());
                    String state;
                    if (planEntity.getState() == null)
                        state = null;
                    else if(planEntity.getState() == 0)
                        state = "미완료";
                    else if(planEntity.getState() == 1)
                        state = "완료";
                    else if(planEntity.getState() == 2)
                        state = "진행중";
                    else state = null;
                    plan.put("state",state);

                    plan.put("extendedProps", extended);
                    planArr.put(plan);
                }
                planList.put("plans", planArr);
            }catch (Exception e){
                throw new UserException(ErrorCode.CANT_FIND_PLAN);
            }
            return planList.toString();
        } else if (type.equals("room")) {
            RoomEntity room = roomRepository.findById(typeId)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_ROOM));

            JSONObject planList = new JSONObject();
            try{
                List<PlanEntity> planEntities = planRepository.findAllByRoomId(typeId);
                JSONArray planArr = new JSONArray();
                for(PlanEntity planEntity : planEntities){
                    JSONObject plan = new JSONObject();
                    plan.put("id", planEntity.getId());
                    plan.put("title", planEntity.getTitle());
                    plan.put("content", planEntity.getContent());
                    plan.put("start", planEntity.getStart_date());
                    plan.put("end",planEntity.getEnd_date());
                    String state;
                    if (planEntity.getState() == null)
                        state = null;
                    else if(planEntity.getState() == 0)
                        state = "미완료";
                    else if(planEntity.getState() == 1)
                        state = "완료";
                    else if(planEntity.getState() == 2)
                        state = "진행중";
                    else state = null;
                    plan.put("state",state);

                    plan.put("extendedProps", extended);
                    planArr.put(plan);
                }
                planList.put("plans", planArr);
            }catch (Exception e){
                throw new UserException(ErrorCode.CANT_FIND_PLAN);
            }
            return planList.toString();
        }

        return "일정 목록 반환 실패";
    }


    @Transactional
    public void updatePlan(PlanDto planDto, Long planId){

        PlanEntity planEntity = planRepository.findById(planId)
                .orElseThrow(() -> new UserException(ErrorCode.NO_PLAN));


        if(!planDto.getTitle().equals(planEntity.getTitle()) || planDto.getTitle() == null){
            planEntity.changeTitle(planDto.getTitle());
        }
        if(!planDto.getContent().equals(planEntity.getContent()) || planDto.getContent() == null){
            planEntity.changeContent(planDto.getContent());
        }
        if(planDto.getStartDate() != planEntity.getStart_date() || planDto.getStartDate() == null){
            planEntity.changeStartDate(planDto.getStartDate());
        }
        if(planDto.getEndDate() != planEntity.getEnd_date() || planDto.getEndDate() == null){
            planEntity.changeEndDate(planDto.getEndDate());
        }
        if(planDto.getState() != planEntity.getState() || planDto.getState() == null){
            planEntity.changeState(planDto.getState());
        }

        planRepository.save(planEntity);
    }


    @Transactional
    public void deletePlan(Long planId){

        PlanEntity planEntity = planRepository.findById(planId)
                .orElseThrow(() -> new UserException(ErrorCode.NO_PLAN));

        planRepository.deleteById(planId);


    }

    @Transactional
    public void allDeletePlan(String type, Long id){

        if(type.equals("group")){
            try {
                List<PlanEntity> planEntities = planRepository.findAllByGroupId(id);
                for(PlanEntity planEntity : planEntities){
                    planRepository.deleteById(planEntity.getId());
                }
            }catch(Exception e){
                throw new UserException(ErrorCode.INTER_SERVER_ERROR);
            }
        }

        if(type.equals("room")){
            try {
                List<PlanEntity> planEntities = planRepository.findAllByRoomId(id);
                for(PlanEntity planEntity : planEntities){
                    planRepository.deleteById(planEntity.getId());
                }
            }catch(Exception e){
                throw new UserException(ErrorCode.INTER_SERVER_ERROR);
            }
        }

    }



}
