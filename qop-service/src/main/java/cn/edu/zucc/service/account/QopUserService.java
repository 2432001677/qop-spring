package cn.edu.zucc.service.account;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.LoginVo;
import cn.edu.zucc.account.vo.RegisterVo;

import java.util.List;

public interface QopUserService {
    List<QopUser> queryAll();

    QopUser queryByPhone(String phoneNum);

    QopUser queryByEmail(String emailNum);

    QopUser addUser(QopUser qopUser);

    QopUser RegistervoToQopUser(RegisterVo registerVo);

    void loginErrIf(LoginVo loginVo);

    boolean emailIf(LoginVo loginVo);


}
