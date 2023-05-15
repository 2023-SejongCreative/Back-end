package com.example.Waffle.service;


import com.example.Waffle.dto.NoteDto;
import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.NoteEntity;
import com.example.Waffle.entity.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.GroupRepository;
import com.example.Waffle.repository.NoteRepository;
import com.example.Waffle.repository.RoomRepository;
import com.example.Waffle.repository.UserRepository;
import com.example.Waffle.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final GroupRepository groupRepository;
    private final RoomRepository roomRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public GroupEntity getGroup(int id){
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.NO_GROUP));

        return groupEntity;
    }

    public RoomEntity getRoom(int id){
        RoomEntity roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.NO_ROOM));

        return roomEntity;
    }

    public UserEntity getUser(String email){
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

        return userEntity;
    }


    @Transactional
    public void createNote(NoteDto noteDto, String type, int id, String atk){

        String email = jwtTokenProvider.getEmail(atk);
        UserEntity user = getUser(email);

        noteDto.setUser(user);

        if(type.equals("group")){

            GroupEntity group = getGroup(id);

            noteDto.setGroup(group);
        }
        else if(type.equals("room")){

            RoomEntity room = getRoom(id);

            noteDto.setRoom(room);
        }

        NoteEntity noteEntity = noteDto.toEntity();

        noteRepository.save(noteEntity);

    }
}
