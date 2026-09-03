package com.esc.pro.users_app.Models;
import com.github.javafaker.Faker;
import lombok.*;

@Data
@AllArgsConstructor
public class User {
    private String userName;
    private String nickName;
    private String password;
}
