package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class loginDto {
    private String email;
    private String password;

    public loginDto(String email, String password){
        this.email = email;
        this.password = password;
    }
}
