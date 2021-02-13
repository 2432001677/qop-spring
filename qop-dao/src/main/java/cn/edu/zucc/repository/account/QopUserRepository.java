package cn.edu.zucc.repository.account;

import cn.edu.zucc.account.po.QopUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QopUserRepository extends JpaRepository<QopUser,Long> {
    QopUser getByEmail(String email);

    QopUser getByPhoneNumber(String phoneNumber);
}
