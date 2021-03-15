package cn.edu.zucc.service.account.impl;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.AccountProfilesVo;
import cn.edu.zucc.account.vo.ChangePasswordVo;
import cn.edu.zucc.account.vo.LoginVo;
import cn.edu.zucc.account.vo.RegisterVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.exception.SourceNotFoundException;
import cn.edu.zucc.exception.WrongPasswordException;
import cn.edu.zucc.repository.account.QopUserRepository;
import cn.edu.zucc.service.account.QopUserService;
import cn.edu.zucc.utils.CryptUtils;
import cn.edu.zucc.utils.FormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Bruce
 * @since 02-11-2021
 */
@Slf4j
@Service
public class QopUserServiceImpl implements QopUserService {
    @Resource
    private QopUserRepository qopUserRepository;

    @Override
    public QopUser addUser(QopUser qopUser) {
        return qopUserRepository.save(qopUser);
    }

    @Override
    public Long login(LoginVo loginVo) {
        QopUser qopUser = findUserByUserName(loginVo.getUserName());
        if (!CryptUtils.matchAccountPasswd(qopUser.getPassword(), loginVo.getPassword())) {
            throw new WrongPasswordException();
        }
        return qopUser.getId();
    }

    @Override
    public void register(RegisterVo registerVo) {
        QopUser qopUser = new QopUser();
        BeanUtils.copyProperties(registerVo, qopUser);
        String userName = registerVo.getUserName();
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
        QopUser qopUser = qopUserRepository.getOne(id);
        AccountProfilesVo accountProfilesVo = new AccountProfilesVo();
        BeanUtils.copyProperties(qopUser, accountProfilesVo);
        return accountProfilesVo;
    }

    @Override
    public void updateProfilesById(AccountProfilesVo accountProfilesVo, Long id) {
        QopUser qopUser = qopUserRepository.getOne(id);
        qopUser.setNickName(accountProfilesVo.getNickName());
        qopUserRepository.save(qopUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePhoneNumberById(String phoneNumber, Long id) {
        qopUserRepository.updatePhoneById(phoneNumber, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmailById(String email, Long id) {
        qopUserRepository.updateEmailById(email, id);
    }

    @Override
    public void changePassword(ChangePasswordVo changePasswordVo) {
        QopUser qopUser = findUserByUserName(changePasswordVo.getUserName());
        if (CryptUtils.matchAccountPasswd(qopUser.getPassword(), changePasswordVo.getValidPassword())) {
            qopUser.setPassword(CryptUtils.cryptAccountPasswd(changePasswordVo.getPassword()));
            qopUserRepository.save(qopUser);
        } else {
            throw new WrongPasswordException();
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
