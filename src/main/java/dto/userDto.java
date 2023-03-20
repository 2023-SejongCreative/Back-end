package dto;

import entity.userEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class userDto {
    private Long id;
    private String email;
    private String password;
    private String name;

    public userDto(userEntity userEntity){
        this.id = userEntity.getId();
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
    }

    public userEntity toEntity(){
        return userEntity.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build();
    }

}
