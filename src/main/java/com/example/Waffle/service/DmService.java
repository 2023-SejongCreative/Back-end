package com.example.Waffle.service;

import com.example.Waffle.dto.DmDto;
import com.example.Waffle.dto.UserDmDto;
import com.example.Waffle.entity.DM.DmEntity;
import com.example.Waffle.entity.DM.UserDmEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.DmRepository;
import com.example.Waffle.repository.UserDmRepository;
import com.example.Waffle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DmService {

    private final DmRepository dmRepository;
    private final UserRepository userRepository;
    private final UserDmRepository userDmRepository;
    @Transactional
    public Long createDm(DmDto dmDto, List<String> emails){

        if(dmDto.getCount() > 6)
            new UserException(ErrorCode.TOO_MANY_PEOPLE);

        DmEntity dmEntity = dmDto.toEntity();

        this.dmRepository.save(dmEntity);

        for(String email : emails){
            UserEntity userEntity = this.userRepository.findByemail(email)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

            UserDmEntity userDmEntity = new UserDmEntity(userEntity, dmEntity);
            this.userDmRepository.save(userDmEntity);
        }


        return dmEntity.getId();
    }

    public void inviteDm(int dmId, String email){

        UserEntity userEntity = userRepository.findByemail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

        DmEntity dmEntity = dmRepository.findById(dmId)
                .orElseThrow(() -> new UserException(ErrorCode.NO_DM));

        Optional<UserDmEntity> userDmEntity = userDmRepository.findByUserIdAndDmId(userEntity.getId(), dmEntity.getId());
        if(userDmEntity.isPresent()){
            throw new UserException(ErrorCode.DUPLICATE_DM_USER);
        }
        else{
            UserDmDto userDmDto = new UserDmDto(userEntity, dmEntity);
            userDmRepository.save(userDmDto.toEntity());
        }


    }


}
