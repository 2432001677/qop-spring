package cn.edu.zucc.service.group.impl;

import cn.edu.zucc.group.po.QopGroup;
import cn.edu.zucc.repository.group.QopGroupRepository;
import cn.edu.zucc.service.group.QopGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Bruce
 * @since 02-13-2021
 */
@Service
public class QopGroupServiceImpl implements QopGroupService {
    @Resource
    private QopGroupRepository qopGroupRepository;


    @Override
    public List<QopGroup> queryAll() {
        return qopGroupRepository.findAll();
    }
}
