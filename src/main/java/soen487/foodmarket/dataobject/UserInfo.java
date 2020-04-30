package soen487.foodmarket.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String tel;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition="TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    @Column(columnDefinition="TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on update current_timestamp")
    private Date updateTime;

}
