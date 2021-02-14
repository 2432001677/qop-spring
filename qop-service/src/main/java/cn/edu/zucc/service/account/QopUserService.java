package cn.edu.zucc.service.account;

import cn.edu.zucc.account.po.QopUser;

import java.util.List;

public interface QopUserService {
    List<QopUser> queryAll();

    QopUser queryByPhone(String phoneNum);

    QopUser queryByEmail(String emailNum);

    QopUser addUser(QopUser qopUser);
}
