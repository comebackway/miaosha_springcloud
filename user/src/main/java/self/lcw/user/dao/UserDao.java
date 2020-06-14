package self.lcw.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import self.lcw.user.entity.User;


@Mapper
public interface UserDao {
    public User getById(@Param("id") long id);

    public int insert(User user);

    public void update(User usernew);
}
