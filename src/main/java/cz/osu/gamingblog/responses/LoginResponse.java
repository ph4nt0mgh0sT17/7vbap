package cz.osu.gamingblog.responses;

import cz.osu.gamingblog.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String username;
    private UserRole userRole;
    private String jwtToken;
}
