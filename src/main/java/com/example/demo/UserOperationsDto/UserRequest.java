package com.example.demo.UserOperationsDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {

    @NotBlank
    @Size(min = 3, max = 35)
    private String username;

    @NotBlank
    @Size(min = 3, max = 40)
    private String password;

}
