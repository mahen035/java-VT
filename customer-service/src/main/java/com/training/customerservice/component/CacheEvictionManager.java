package com.training.customerservice.component;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class CacheEvictionManager {

    private final Queue<UUID> failedEvicts = new ConcurrentLinkedDeque<>();

    public void addFailedEvicts(UUID id){
        failedEvicts.add(id);
    }

    public List<UUID> purgeEvicts(){
        List<UUID> ids = new ArrayList<>();
        UUID id;
        while((id = failedEvicts.poll()) != null){
            ids.add(id);
        }
        return ids;
    }

}
