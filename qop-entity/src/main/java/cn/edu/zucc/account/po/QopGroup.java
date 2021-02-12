package cn.edu.zucc.account.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "qop_group")
public class QopGroup {
    @Id
    private long id;

    @Column(length = 32)
    private String gname;

    @Column(length = 100)
    private String introduction;

    @Column
    private String img;

}
