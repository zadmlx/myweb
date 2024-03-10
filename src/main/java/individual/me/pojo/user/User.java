package individual.me.pojo.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String username;
    private String password;
    private String phoneNumber;
    private LocalDateTime birthday;
    private LocalDateTime registerDate;
    private int deleted;
    private String authority;


    public User(long id, String username, String password, LocalDateTime registerDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.registerDate = registerDate;
    }
}
