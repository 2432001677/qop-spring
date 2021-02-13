package cn.edu.zucc.group.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "qop_group_member")
public class QopGroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,length = 20)
    private long groupId;

    @Column(nullable = false,length = 20)
    private long userId;

    @Column(nullable = false,length = 20)
    private String userRole;

    @Column
    private Date joinDate;
}
