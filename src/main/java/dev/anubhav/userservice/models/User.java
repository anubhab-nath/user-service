package dev.anubhav.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends BaseModel {
    private String email;
    private String hashedPassword;

    @OneToMany(mappedBy = "user")
    private List<Session> session;
}
