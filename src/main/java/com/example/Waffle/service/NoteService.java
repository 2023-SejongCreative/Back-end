package com.example.Waffle.service;


import com.example.Waffle.dto.NoteDto;
import com.example.Waffle.entity.Group.GroupEntity;
import com.example.Waffle.entity.Group.UserGroupEntity;
import com.example.Waffle.entity.NoteEntity;
import com.example.Waffle.entity.Room.RoomEntity;
import com.example.Waffle.entity.Room.UserRoomEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.*;
import com.example.Waffle.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final GroupRepository groupRepository;
    private final RoomRepository roomRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserGroupRepository userGroupRepository;
    private final UserRoomRepository userRoomRepository;


    @Transactional
    public Long createNote(NoteDto noteDto, String type, Long id, String atk){

        String email = jwtTokenProvider.getEmail(atk);
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

        noteDto.setUser(user);

        if(type.equals("group")){

            GroupEntity group = groupRepository.findById(id)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_GROUP));

            if(noteDto.getNotice() == 0){
                System.out.println(user.getId());
                System.out.println(group.getId());
                UserGroupEntity userGroupEntity = userGroupRepository.findByUserIdAndGroupId(user.getId(), group.getId())
                        .orElseThrow(() -> new UserException(ErrorCode.BAD_REQUEST));

                if(userGroupEntity.getManager() == 0){
                    throw new UserException(ErrorCode.NO_MANAGER);
                }
            }

            noteDto.setGroup(group);
        }
        else if(type.equals("room")){

            RoomEntity room = roomRepository.findById(id)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_ROOM));

            if(noteDto.getNotice() == 0){
                UserRoomEntity userRoomEntity = userRoomRepository.findByUserIdAndRoomId(user.getId(), room.getId())
                        .orElseThrow(() -> new UserException(ErrorCode.BAD_REQUEST));

                if(userRoomEntity.getManager() == 0){
                    throw new UserException(ErrorCode.NO_MANAGER);
                }
            }

            noteDto.setRoom(room);
        }
        else{
            throw new UserException(ErrorCode.BAD_REQUEST);
        }

        NoteEntity noteEntity = noteDto.toEntity();
        noteRepository.save(noteEntity);

        return noteEntity.getId();
    }

    @Transactional
    public String getNotes(String type, Long id){

        JSONObject noteList = new JSONObject();
        List<NoteEntity> noteEntities = new ArrayList<>();
        try {
            if (type.equals("group")) {
                noteEntities = noteRepository.findAllByGroupId(id);
            }
            else if (type.equals("room")) {
                noteEntities = noteRepository.findAllByRoomId(id);
            }
            else{
                throw new UserException(ErrorCode.BAD_REQUEST);
            }

            JSONArray noticeArr = new JSONArray();
            JSONArray noteArr = new JSONArray();

            for(NoteEntity noteEntity : noteEntities){
                JSONObject note = new JSONObject();

                note.put("id", noteEntity.getId());
                note.put("title", noteEntity.getTitle());
                note.put("content", noteEntity.getContent());
                note.put("date", noteEntity.getDate());

                if(noteEntity.getNotice() == 0){
                    noticeArr.put(note);
                }
                else if(noteEntity.getNotice() == 1){
                    noteArr.put(note);
                }
            }

            noteList.put("notices", noticeArr);
            noteList.put("notes", noteArr);

        }catch(Exception e){
            throw new UserException(ErrorCode.CANT_FIND_NOTE);
        }

        return noteList.toString();
    }

    @Transactional
    public String getNote(int id){

        JSONObject note = new JSONObject();

        try {
            NoteEntity noteEntity = noteRepository.findById(id)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_NOTE));

            note.put("title", noteEntity.getTitle());
            note.put("content", noteEntity.getContent());
            note.put("date", noteEntity.getDate());

            UserEntity userEntity = noteEntity.getUser();
            note.put("writer", userEntity.getName());
            note.put("id", userEntity.getId());

        }catch (Exception e){
            throw new UserException(ErrorCode.CANT_FIND_NOTE);
        }

        return note.toString();
    }

    @Transactional
    public void updateNote(NoteDto noteDto, int id, String atk, int manager){

        NoteEntity noteEntity = noteRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.NO_NOTE));

        if(manager == 0){

            if(noteDto.getNotice() == 0){
                throw new UserException(ErrorCode.NO_MANAGER);
            }

            String email = jwtTokenProvider.getEmail(atk);

            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

            if(user.getId() != noteEntity.getUser().getId()){
                throw new UserException(ErrorCode.NO_AUTHORITY);
            }
        }

        try{
            if(!noteDto.getTitle().equals(noteEntity.getTitle())){
                noteEntity.changeTitle(noteDto.getTitle());
            }
            if(!noteDto.getContent().equals(noteEntity.getContent()) || noteDto.getContent() == null){
                noteEntity.changeContent(noteDto.getContent());
            }
            if(noteDto.getDate() != noteEntity.getDate()){
                noteEntity.changeDate(noteDto.getDate());
            }
            if(noteDto.getNotice() != noteEntity.getNotice()){
                noteEntity.changeNotice(noteDto.getNotice());
            }

            noteRepository.save(noteEntity);

        }catch(Exception e){
            throw new UserException(ErrorCode.INTER_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteNote(int id, String atk, int manager){

        NoteEntity noteEntity = noteRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.NO_NOTE));

        if(manager == 0){
            String email = jwtTokenProvider.getEmail(atk);

            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

            if(user.getId() != noteEntity.getUser().getId()){
                throw new UserException(ErrorCode.NO_AUTHORITY);
            }
        }

        try{

            noteRepository.deleteById(noteEntity.getId());

        }catch(Exception e){
            throw new UserException(ErrorCode.INTER_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteAllNote(String type, Long id){

        List<NoteEntity> noteEntities = new ArrayList<>();
        try {
            if (type.equals("group")) {
                noteEntities = noteRepository.findAllByGroupId(id);

            } else if (type.equals("room")) {

                noteEntities = noteRepository.findAllByRoomId(id);
            }

            for (NoteEntity noteEntity : noteEntities) {
                noteRepository.deleteById(noteEntity.getId());
            }
        }catch(Exception e){
            throw new UserException(ErrorCode.INTER_SERVER_ERROR);
        }
    }
}
