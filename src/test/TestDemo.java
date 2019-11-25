package test;

import org.junit.Test;
import pojo.User;

public class TestDemo {

    @Test
    public void test1(){
        User user = new User();
        String username = user.getUsername();
        System.out.println(username);
    }
}
