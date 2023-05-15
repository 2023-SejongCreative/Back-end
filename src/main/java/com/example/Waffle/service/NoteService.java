package com.example.Waffle.service;


import com.example.Waffle.dto.NoteDto;
import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.NoteEntity;
import com.example.Waffle.entity.RoomEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.GroupRepository;
import com.example.Waffle.repository.NoteRepository;
import com.example.Waffle.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final GroupRepository groupRepository;
    private final RoomRepository roomRepository;
    private final NoteRepository noteRepository;

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


    @Transactional
    public void createNote(NoteDto noteDto, String type, int id){

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
