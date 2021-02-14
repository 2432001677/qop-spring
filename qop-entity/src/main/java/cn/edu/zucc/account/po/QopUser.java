package cn.edu.zucc.account.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "qop_user")
public class QopUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String nickName;

    @Column(length = 50, unique = true)
    private String phoneNumber;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100)
    private String img;

    @Column(length = 5)
    private String accountStatus;

    @Column
    private Date createDate;
}
