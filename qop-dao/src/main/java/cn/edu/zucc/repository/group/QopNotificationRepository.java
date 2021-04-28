package cn.edu.zucc.repository.group;

import cn.edu.zucc.account.po.QopNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bruce
 * @since 04-27-2021
 **/
@Repository
public interface QopNotificationRepository extends MongoRepository<QopNotification,Long> {
    List<QopNotification> findAllByUid(Long uid);
}
