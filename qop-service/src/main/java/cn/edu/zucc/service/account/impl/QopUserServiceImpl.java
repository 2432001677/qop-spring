package cn.edu.zucc.service.account.impl;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.repository.QopUserRepository;
import cn.edu.zucc.service.account.QopUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QopUserServiceImpl implements QopUserService {
    @Resource
    private QopUserRepository qopUserRepository;

    @Override
    public List<QopUser> queryAll() {
        return qopUserRepository.findAll();
    }
}
