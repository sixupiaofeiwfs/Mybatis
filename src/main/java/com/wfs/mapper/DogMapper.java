package com.wfs.mapper;

import com.wfs.pojo.Dog;
import org.apache.ibatis.annotations.Select;

public interface DogMapper {

   // @Select("select * from dog where id=#{id}")
    Dog selectDog(Integer id);



    int insertDog(Dog dog);

    int updateDog(Dog dog);

    int deleteDog(Integer id);
}
