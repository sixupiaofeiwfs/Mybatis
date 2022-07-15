import com.mysql.cj.Session;
import com.wfs.mapper.DogMapper;
import com.wfs.pojo.Dog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;

public class Test {

    SqlSession sqlSession;
    @Before
    public void brfore(){
        //从xml中获取sqlsessionfactory
        String resource="mybatis.xml";
        InputStream inputStream= null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);

        sqlSession = sqlSessionFactory.openSession();

    }



    /*
     * 第一种方式： 此方式仅需要mybatis.xml   XXXmapper.xml  pojo类   test方法
     * 1.首先导入mybatis依赖  mysql驱动依赖  junit测试依赖
     * 2.创建数据库 数据表
     * 3.创建pojo类
     * 4.创建mybatis全局配置xml  并配置好数据源信息
     * 5.创建pojo类对应的mapper.xml    注意：该xml中的namespace配置为pojo类+mapper  这里的pojo类与resultType需要返回的pojo类 必须使用全类名
     * 6.在全局配置mybatis.xml中的mappers中配置好pojo+mapper.xml
     * 7.test类中先获取到全局配置文件mybatis.xml的字节 然后建立sqlSessionFactory 再openSession
     * 8.执行selectOne方法，执行此方法时第一个参数一定是mapper.xml中的namespace+id
     * */
    @org.junit.Test
    public void test01() throws IOException {


        Dog dog = (Dog) sqlSession.selectOne("com.wfs.pojo.DogMapper.selectDog", 1);

        System.out.println(dog.getName());
    }



@org.junit.Test
/*
* 第二种方式：此方式需要mybatis.xml（需要修改） XXXmapper.xml（需要修改）   pojo类（不需要改动）  测试类   XXXMapper接口 各文件改动如下
* 1.mybatis.xml  在mappers中注释掉第一种方式（id方式）  采用第二种方式（接口方式）
* 2.XXXmapper.xml 注释掉第一种方式 采用第二种方式  两种方式的不同点在于namespace   第一种方式的namespace可以随便输入 第二种必须是XXXMapper的全类名
* 3.新增XXXMapper接口  此接口必须满足：a.方法名要跟XXXMapper.xml中的id值一致 b.返回的类型 参数个数 参数类型必须与XXXMapper中的相关属性一致
*4.特别注意：XXXMapper.xml文件必须与XXXMapper类在编译时必须同一文件夹下，解决此问题的方法时在resource文件夹中建立目录，该目录与XXXMapper的目录一致，该示例中的目录为:com/wfs/mapper

* */


    public void test02() throws IOException{

    DogMapper dogMapper=sqlSession.getMapper(DogMapper.class);
    Dog dog = dogMapper.selectDog(1);
    System.out.println(dog.getName());

}

@org.junit.Test
    public void test03() throws IOException{
        /*
        * 第三种方式需要修改的文件 XXXMapper.xml  XXXMapper类   修改方式如下
        * XXXMapper.xml 注释掉第二种方式，打开第三种方式  采用注解的方式进行数据库操作，就不能允许xml中还有id与XXXMapper类中的方法名对应
        * XXXMapper 在方法上添加@注解 写好SQL语句即可
        * */

        DogMapper dogMapper=sqlSession.getMapper(DogMapper.class);
        Dog dog = dogMapper.selectDog(1);
        System.out.println(dog.getName());

    }

@org.junit.Test
    public void insert(){
        DogMapper dogMapper=sqlSession.getMapper(DogMapper.class);
        Dog dog =new Dog();
        dog.setName("晶晶");
        int result = dogMapper.insertDog(dog);
        System.out.println(result);
        sqlSession.commit();
    }

    @org.junit.Test
    public void update(){
        DogMapper dogMapper=sqlSession.getMapper(DogMapper.class);
        Dog dog =new Dog();
        dog.setId(2);
        dog.setName("欢欢");
        int result=dogMapper.updateDog(dog);
        System.out.println(result);
        sqlSession.commit();
    }


    @org.junit.Test
    public void delete(){
        DogMapper dogMapper=sqlSession.getMapper(DogMapper.class);
        int result =dogMapper.deleteDog(2);
        System.out.println(result);
        sqlSession.commit();
    }

    }

