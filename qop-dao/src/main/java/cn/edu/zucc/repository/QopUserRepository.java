package cn.edu.zucc.repository;

import cn.edu.zucc.account.po.QopUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QopUserRepository extends JpaRepository<QopUser,String> {
}
