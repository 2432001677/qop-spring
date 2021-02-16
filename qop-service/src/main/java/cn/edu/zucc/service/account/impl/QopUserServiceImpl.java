package cn.edu.zucc.service.account.impl;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.LoginVo;
import cn.edu.zucc.account.vo.RegisterVo;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.repository.account.QopUserRepository;
import cn.edu.zucc.service.account.QopUserService;
import cn.edu.zucc.utils.CryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public QopUser queryByPhone(String phoneNum) {
        return qopUserRepository.getByPhoneNumber(phoneNum);
    }

    @Override
    public QopUser queryByEmail(String emailNum) {
        return qopUserRepository.getByEmail(emailNum);
    }

    @Override
    public QopUser RegistervoToQopUser(RegisterVo registerVo) {
        registerVo.setPassword(CryptUtils.cryptAccountPasswd(registerVo.getPassword()));
        QopUser qopUser = new QopUser();
        BeanUtils.copyProperties(registerVo,qopUser);
        return qopUser;
    }

    @Override
    public void loginErrIf(LoginVo loginVo) {
        if(loginVo.getUserName()==null){
            throw new FormInfoException("用户名为空");
        }

    }

    @Override
    public boolean emailIf(LoginVo loginVo) {
        if (loginVo.getUserName().matches("@")){
            return true;
        }
        return false;
    }


}
