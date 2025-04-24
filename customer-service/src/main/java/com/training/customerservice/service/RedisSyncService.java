package com.training.customerservice.service;

import com.training.customerservice.component.CacheEvictionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class RedisSyncService {

    @Autowired
    private RedisHealthMonitorService redisHealthMonitorService;

    @Autowired
    private CacheEvictionManager cacheEvictionManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(fixedDelay = 10000) //Every 10 seconds
    public void removeStaleRecords(){
        System.out.println("Checking for Redis Availability::");
        if(redisHealthMonitorService.isRedisAvailable()){
            List<UUID> idsToRemove = cacheEvictionManager.purgeEvicts();
            for(UUID id : idsToRemove){
                String key = "customers::"+id;
                redisTemplate.delete(key);
                log.info("Removed stale key after recovery..");
            }
        }
        else{
            System.out.println("Trying to reach Redis server::");
        }
    }
}
