package com.example.Waffle.service;


import com.example.Waffle.dto.NoteDto;
import com.example.Waffle.entity.Group.GroupEntity;
import com.example.Waffle.entity.NoteEntity;
import com.example.Waffle.entity.Room.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.GroupRepository;
import com.example.Waffle.repository.NoteRepository;
import com.example.Waffle.repository.RoomRepository;
import com.example.Waffle.repository.UserRepository;
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

    public GroupEntity getGroupById(Long id){
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.NO_GROUP));

        return groupEntity;
    }

    public RoomEntity getRoomById(Long id){
        RoomEntity roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.NO_ROOM));

        return roomEntity;
    }

    public UserEntity getUserByEmail(String email){
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

        return userEntity;
    }

    public NoteEntity getNoteById(int id){
        NoteEntity noteEntity = noteRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.NO_NOTE));

        return noteEntity;
    }


    @Transactional
    public void createNote(NoteDto noteDto, String type, Long id, String atk){

        String email = jwtTokenProvider.getEmail(atk);
        UserEntity user = getUserByEmail(email);

        noteDto.setUser(user);

        if(type.equals("group")){

            GroupEntity group = getGroupById(id);

            noteDto.setGroup(group);
        }
        else if(type.equals("room")){

            RoomEntity room = getRoomById(id);

            noteDto.setRoom(room);
        }
        else{
            throw new UserException(ErrorCode.BAD_REQUEST);
        }

        NoteEntity noteEntity = noteDto.toEntity();
        noteRepository.save(noteEntity);
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
            NoteEntity noteEntity = getNoteById(id);

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
    public void updateNote(NoteDto noteDto, int id){

        NoteEntity noteEntity = getNoteById(id);

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
    public void deleteNote(int id){

        try{

            NoteEntity noteEntity = getNoteById(id);

            noteRepository.deleteById(id);

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
