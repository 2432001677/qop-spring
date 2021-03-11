package cn.edu.zucc.repository.questionnaire;

import cn.edu.zucc.questionnaire.po.Foo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FooRepository extends MongoRepository<Foo,String > {
}
