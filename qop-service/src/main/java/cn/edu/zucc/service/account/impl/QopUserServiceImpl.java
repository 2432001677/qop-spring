package cn.edu.zucc.service.account.impl;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.LoginVo;
import cn.edu.zucc.account.vo.RegisterVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.exception.SourceNotFoundException;
import cn.edu.zucc.exception.WrongPasswordException;
import cn.edu.zucc.repository.account.QopUserRepository;
import cn.edu.zucc.service.account.QopUserService;
import cn.edu.zucc.utils.CryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public List<QopUser> queryAll() {
        return qopUserRepository.findAll();
    }

    @Override
    public QopUser addUser(QopUser qopUser) {
        return qopUserRepository.save(qopUser);
    }

    @Override
    public Long login(LoginVo loginVo) {
        String userName = loginVo.getUserName();
        QopUser qopUser = userName.matches("@") ? qopUserRepository.getByEmail(userName) : qopUserRepository.getByPhoneNumber(userName);
        if (qopUser == null) {
            throw new SourceNotFoundException(ResponseMsg.NOT_FOUND_USER);
        }
        if (!CryptUtils.matchAccountPasswd(qopUser.getPassword(), loginVo.getPassword())) {
            throw new WrongPasswordException();
        }
        return qopUser.getId();
    }

    @Override
    public void register(RegisterVo registerVo) {
        QopUser qopUser = new QopUser();
        BeanUtils.copyProperties(registerVo, qopUser);
        if (registerVo.getUserName().matches("@")) {
            qopUser.setEmail(registerVo.getUserName());
        } else {
            qopUser.setPhoneNumber(registerVo.getUserName());
        }
        qopUser.setCreateDate(new Date());
        qopUser.setPassword(CryptUtils.cryptAccountPasswd(registerVo.getPassword()));
        addUser(qopUser);
    }
}
