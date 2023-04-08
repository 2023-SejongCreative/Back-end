package com.example.Waffle.dto;

import com.example.Waffle.entity.GroupEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    private String groupName;

    public GroupEntity toEntity(){
        GroupEntity groupEntity = new GroupEntity().builder()
                .name(groupName)
                .build();
        return  groupEntity;
    }


}
