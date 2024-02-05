package uz.pdp.dto;

public record UserSignupDto(
        String username,
        String email,
        String password,
        String gender
) {
}
