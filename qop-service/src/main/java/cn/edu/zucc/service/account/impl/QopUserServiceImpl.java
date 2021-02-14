package cn.edu.zucc.service.account.impl;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.repository.account.QopUserRepository;
import cn.edu.zucc.service.account.QopUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Bruce
 * @since 02-11-2021
 */
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
}
