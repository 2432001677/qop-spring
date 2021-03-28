package cn.edu.zucc.service.account;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.AccountProfilesVo;
import cn.edu.zucc.account.vo.ChangePasswordVo;
import cn.edu.zucc.account.vo.LoginVo;
import cn.edu.zucc.account.vo.RegisterVo;

public interface QopUserService {
    QopUser addUser(QopUser qopUser);

    QopUser login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    AccountProfilesVo getProfilesById(Long id);

    void updateProfilesById(AccountProfilesVo accountProfilesVo, Long id);

    void changePassword(ChangePasswordVo changePasswordVo);
}
