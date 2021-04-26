package cn.edu.zucc.account.po;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "qop_questionnaire")
public class QopQuestionnaire {
    @Id
    private long id;

    @Column(length = 50)
    private String qname;

    @Column
    private Long groupId;

    @Column(length = 32)
    private String groupName;
}
