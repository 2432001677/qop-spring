package cn.edu.zucc.group.po;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "qop_group_questionnaire")
public class QopGroupQuestionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private Long groupId;

    @Column(length = 20, nullable = false)
    private String questionnaireId;

    @Column
    private Date publishDate;
}
