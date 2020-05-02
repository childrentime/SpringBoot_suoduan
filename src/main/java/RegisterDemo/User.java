package RegisterDemo;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data //注解在类上, 为类提供get和set, 此外还提供了 equals()、hashCode()、toString() 方法
public class User {

    @Getter  //自动设置get方法
    @Setter  //自动设置set方法
    @Id  //标注用于声明一个实体类的属性映射为数据库的主键列。该属性通常置于属性声明语句之前，可与声明语句同行，也可写在单独行上
    @GeneratedValue(strategy=GenerationType.AUTO)    //会在mysql数据库里面生成一张hibernate_sequence自增表
    private Integer id;
    private String url;
    private String st;
}