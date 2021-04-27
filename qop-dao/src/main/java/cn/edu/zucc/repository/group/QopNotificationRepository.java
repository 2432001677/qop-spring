package cn.edu.zucc.repository.group;

import cn.edu.zucc.group.po.QopNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bruce
 * @since 04-27-2021
 **/
@Repository
public interface QopNotificationRepository extends MongoRepository<QopNotification,Long> {
}
