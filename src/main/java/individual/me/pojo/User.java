package individual.me.pojo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private long id;
    private String username;
    private String password;
    private String phoneNumber;
    private LocalDateTime birthday;
    private LocalDateTime registerDate;


    public User(long id, String username, String password, LocalDateTime registerDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.registerDate = registerDate;
    }
}
