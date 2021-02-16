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
    private Long id;

    @Column(nullable = false,length = 20)
    private Long groupId;

    @Column(nullable = false,length = 20)
    private Long userId;

    @Column(nullable = false,length = 20)
    private String userRole;

    @Column
    private Date joinDate;
}
