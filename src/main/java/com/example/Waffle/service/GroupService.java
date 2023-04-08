package com.example.Waffle.service;

import com.example.Waffle.dto.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {

    @Transactional
    public void save(GroupDto groupDto){


    }

}
