package plmatmaplus.matmapluspl.dto;

import javax.validation.constraints.NotEmpty;

public class UserRegisterDTO {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    @NotEmpty
    private String repeatedPassword;

    @NotEmpty
    private String email;

    public UserRegisterDTO(String username, String password, String repeatedPassword, String email) {
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.email = email;
    }

    public UserRegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
