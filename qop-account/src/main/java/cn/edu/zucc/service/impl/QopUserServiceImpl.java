package cn.edu.zucc.service.impl;

import cn.edu.zucc.pojo.QopUser;
import cn.edu.zucc.repository.QopUserRepository;
import cn.edu.zucc.service.QopUserService;
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
