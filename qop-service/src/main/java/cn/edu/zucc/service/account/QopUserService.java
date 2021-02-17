package cn.edu.zucc.service.account;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.LoginVo;
import cn.edu.zucc.account.vo.RegisterVo;

public interface QopUserService {
    QopUser addUser(QopUser qopUser);

    Long login(LoginVo loginVo);

    void register(RegisterVo registerVo);
}
