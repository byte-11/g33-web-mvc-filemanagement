package uz.pdp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class User {
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String username;
    private String email;
    private String password;
    private String gender;
}
