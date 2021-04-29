package cn.edu.zucc.service.account;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.*;

import java.util.List;

public interface QopUserService {
    QopUser addUser(QopUser qopUser);

    QopUser login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    AccountProfilesVo getProfilesById(Long id);

    void updateProfilesById(AccountProfilesVo accountProfilesVo, Long id);

    void changePassword(ChangePasswordVo changePasswordVo);

    List<NotificationVo> getNotificationByUserId(Long id);

    void responseInvitation(ResponseNotificationVo responseNotificationVo, Long uid);
}
