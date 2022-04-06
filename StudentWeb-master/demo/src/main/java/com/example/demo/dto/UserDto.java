package com.example.demo.dto;
import com.example.demo.model.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private UserType userType;
    @NotNull(message = "User type invalid")
    private Long userTypeId;
    @NotNull(message = "User id invalid")
    private Long userId;
    private Long chatId;
    @NotEmpty(message = "Username invalid") @Length(min = 5, max = 25, message = "Username so short or long")
    private String username;
    @NotBlank(message = "Phone invalid")
    private String phone;
    @NotBlank(message = "First name invalid")
    private String firstName;
    private String lastName;
    private String password;
    private String token;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}