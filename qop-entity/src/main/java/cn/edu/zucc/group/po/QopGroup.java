package cn.edu.zucc.group.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "qop_group")
public class QopGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 200)
    private String introduction;

    @Column(length = 100)
    private String img;

    @Column
    private Date createDate;
}
