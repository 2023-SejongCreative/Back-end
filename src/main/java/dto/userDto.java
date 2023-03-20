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
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.name = userEntity.getName();
    }
}
