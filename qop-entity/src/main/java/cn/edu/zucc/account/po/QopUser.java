package cn.edu.zucc.account.po;

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
    private Long id;

    @Column(length = 20, nullable = false)
    private String nickName;

    @Column(length = 50, unique = true)
    private String phoneNumber;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 5)
    private String userStatus;

    @Column
    private String img;

    @Column(length = 4)
    private String authority;
}
