package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //      创建SQL session factory Builder对象
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            //      创建核心配置文件的输入流
            InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
            //        通过输入流来创建SqlSessionFactory对象
            sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }
}
