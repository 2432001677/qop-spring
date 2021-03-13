package cn.edu.zucc.repository.account;

import cn.edu.zucc.account.po.QopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QopUserRepository extends JpaRepository<QopUser, Long> {
    QopUser getByEmail(String email);

    QopUser getByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = "update QopUser set phoneNumber=:phoneNumber where id=:id")
    void updatePhoneById(@Param("phoneNumber") String phoneNumber, @Param("id") Long id);

    @Modifying
    @Query(value = "update QopUser set email=:email where id=:id")
    void updateEmailById(@Param("email") String email, @Param("id") Long id);
}
