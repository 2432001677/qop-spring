package cn.edu.zucc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @author Bruce
 * @since 03-11-2021
 */
@Configuration
public class MongoClassConverter {
    private final MongoDatabaseFactory mongoDatabaseFactory;
    private final MongoMappingContext mongoMappingContext;

    public MongoClassConverter(MongoDatabaseFactory mongoDatabaseFactory, MongoMappingContext mongoMappingContext) {
        this.mongoDatabaseFactory = mongoDatabaseFactory;
        this.mongoMappingContext = mongoMappingContext;
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter() {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
//  MongoDB搭建复制集时可开启事务
//    @Bean
//    MongoTransactionManager transactionManager() {
//        return new MongoTransactionManager(mongoDatabaseFactory);
//    }
}
