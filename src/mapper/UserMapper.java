package mapper;


import pojo.User;

public interface UserMapper {

     User getUser(String username,String password);
}
