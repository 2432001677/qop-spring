package cn.edu.zucc.service.account;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.*;

import java.util.List;

public interface QopUserService {
    QopUser addUser(QopUser qopUser);

    QopUser login(QopUser qopUser, LoginVo loginVo);

    void register(RegisterVo registerVo);

    AccountProfilesVo getProfilesById(Long id);

    void updateProfilesById(AccountProfilesVo accountProfilesVo, Long userId);

    void changePassword(QopUser qopUser, ChangePasswordVo changePasswordVo);

    List<NotificationVo> getNotificationByUserId(Long userId);

    void responseInvitation(ResponseNotificationVo responseNotificationVo, Long userId);

    QopUser findUserByUserName(String userName);
}
