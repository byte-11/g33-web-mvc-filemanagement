package uz.pdp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDto {
    @NotBlank
    @Length(min = 3, max = 20)
    private String username;
    @Email
    private String email;
    @NotBlank
    @Length(min = 6, max = 50)
    private String password;
    @NotEmpty(message = "gender cannot be empty")
    private String gender;
}
