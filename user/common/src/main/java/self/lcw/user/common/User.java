package self.lcw.user.common;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private String salt;
}
