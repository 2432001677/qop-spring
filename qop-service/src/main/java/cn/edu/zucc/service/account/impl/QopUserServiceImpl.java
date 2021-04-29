package cn.edu.zucc.service.account.impl;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.*;
import cn.edu.zucc.constant.CommonConstants;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.enums.GroupRole;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.exception.SourceNotFoundException;
import cn.edu.zucc.exception.UnAuthorizedException;
import cn.edu.zucc.exception.WrongPasswordException;
import cn.edu.zucc.group.po.QopGroupMember;
import cn.edu.zucc.repository.account.QopUserRepository;
import cn.edu.zucc.repository.group.QopGroupMemberRepository;
import cn.edu.zucc.repository.group.QopGroupRepository;
import cn.edu.zucc.repository.group.QopNotificationRepository;
import cn.edu.zucc.service.account.QopUserService;
import cn.edu.zucc.utils.CryptUtils;
import cn.edu.zucc.utils.FormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Bruce
 * @since 02-11-2021
 */
@Slf4j
@Service
public class QopUserServiceImpl implements QopUserService {
    @Resource
    private QopUserRepository qopUserRepository;
    @Resource
    private QopNotificationRepository qopNotificationRepository;
    @Resource
    private QopGroupRepository qopGroupRepository;
    @Resource
    private QopGroupMemberRepository qopGroupMemberRepository;

    @Override
    public QopUser addUser(QopUser qopUser) {
        return qopUserRepository.save(qopUser);
    }

    @Override
    public QopUser login(LoginVo loginVo) {
        var qopUser = findUserByUserName(loginVo.getUserName());
        if (!CryptUtils.matchAccountPasswd(qopUser.getPassword(), loginVo.getPassword())) {
            throw new WrongPasswordException();
        }
        return qopUser;
    }

    @Override
    public void register(RegisterVo registerVo) {
        var qopUser = new QopUser();
        BeanUtils.copyProperties(registerVo, qopUser);
        var userName = registerVo.getUserName();
        if (FormatUtils.isEmail(userName)) {
            qopUser.setEmail(userName);
        } else if (FormatUtils.isPhoneNumber(userName)) {
            qopUser.setPhoneNumber(userName);
        } else {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        qopUser.setCreateDate(new Date());
        qopUser.setPassword(CryptUtils.cryptAccountPasswd(registerVo.getPassword()));
        addUser(qopUser);
    }

    @Override
    public AccountProfilesVo getProfilesById(Long id) {
        var qopUser = qopUserRepository.getOne(id);
        var accountProfilesVo = new AccountProfilesVo();
        BeanUtils.copyProperties(qopUser, accountProfilesVo);
        return accountProfilesVo;
    }

    @Override
    public void updateProfilesById(AccountProfilesVo accountProfilesVo, Long id) {
        var qopUser = qopUserRepository.getOne(id);
        qopUser.setNickName(accountProfilesVo.getNickName());
        var phoneNumber = accountProfilesVo.getPhoneNumber();
        if (!StringUtils.isBlank(phoneNumber)) {
            if (FormatUtils.isPhoneNumber(phoneNumber)) {
                qopUser.setPhoneNumber(phoneNumber);
            } else {
                throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
            }
        }
        var email = accountProfilesVo.getEmail();
        if (!StringUtils.isBlank(email)) {
            if (FormatUtils.isEmail(email)) {
                qopUser.setEmail(email);
            } else {
                throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
            }
        }
        qopUserRepository.save(qopUser);
    }

    @Override
    public void changePassword(ChangePasswordVo changePasswordVo) {
        var qopUser = findUserByUserName(changePasswordVo.getUserName());
        if (CryptUtils.matchAccountPasswd(qopUser.getPassword(), changePasswordVo.getValidPassword())) {
            qopUser.setPassword(CryptUtils.cryptAccountPasswd(changePasswordVo.getPassword()));
            qopUserRepository.save(qopUser);
        } else {
            throw new WrongPasswordException();
        }
    }

    @Override
    public List<NotificationVo> getNotificationByUserId(Long id) {
        var qopNotifications = qopNotificationRepository.findAllByUid(id);
        if (!CollectionUtils.isEmpty(qopNotifications)) {
            return qopNotifications.stream().map(qopNotification -> {
                var notificationVo = new NotificationVo();
                BeanUtils.copyProperties(qopNotification, notificationVo);
                if (CommonConstants.INVITATION_NOTIFICATION.equals(qopNotification.getType())) {
                    var info = (Map<String, Object>) qopNotification.getInfo();
                    var inviterId = (Long) info.get("inviterId");
                    var groupId = (Long) info.get("groupId");
                    var invitationInfoVo = InvitationInfoVo
                            .builder()
                            .inviterId(inviterId)
                            .groupId(groupId)
                            .build();
                    invitationInfoVo.setGroupName(qopGroupRepository.getOne(groupId).getName());
                    invitationInfoVo.setInviterName(qopUserRepository.getOne(inviterId).getNickName());
                    notificationVo.setInfo(invitationInfoVo);
                }
                return notificationVo;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void responseInvitation(ResponseNotificationVo responseNotificationVo, Long uid) {
        var qopNotification = qopNotificationRepository.findById(responseNotificationVo.getId()).orElse(null);
        if (qopNotification == null) {
            throw new SourceNotFoundException(ResponseMsg.NOTIFICATION_EXPIRED);
        }
        if (!qopNotification.getUid().equals(uid)) {
            throw new UnAuthorizedException(ResponseMsg.NOTIFICATION_EXPIRED);
        }
        if (CommonConstants.N.equals(responseNotificationVo.getAnswer())) {
            qopNotificationRepository.deleteById(qopNotification.getId());
        }
        if (CommonConstants.Y.equals(responseNotificationVo.getAnswer())) {
            var info = (Map<String, Object>) qopNotification.getInfo();
            var qopGroupMember = new QopGroupMember();
            qopGroupMember.setGroupId((Long) info.get("groupId"));
            qopGroupMember.setUserRole(GroupRole.GROUP_MEMBER.getCode());
            qopGroupMember.setJoinDate(new Date());
            qopGroupMember.setUserId(uid);
            qopGroupMemberRepository.save(qopGroupMember);
            qopNotificationRepository.deleteById(qopNotification.getId());
        }
    }

    private QopUser findUserByUserName(String userName) {
        QopUser qopUser = null;
        if (FormatUtils.isEmail(userName)) {
            qopUser = qopUserRepository.getByEmail(userName);
        } else if (FormatUtils.isPhoneNumber(userName)) {
            qopUser = qopUserRepository.getByPhoneNumber(userName);
        }
        if (qopUser == null) {
            throw new SourceNotFoundException(ResponseMsg.NOT_FOUND_USER);
        }
        return qopUser;
    }
}
