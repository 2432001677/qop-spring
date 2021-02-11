package cn.edu.zucc.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "qop_user")
public class QopUser {
    @Id
    private String id;
    @Column(length = 20)
    private String nickName;
    @Column(length = 50)
    private String phoneNumber;
    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String password;
    @Column(length = 5)
    private String userStatus;
}
