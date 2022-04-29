package com.baglie.VirtualQueue.repository;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.util.Date;

@Data
@RedisHash(value = "student")
public class DTOStudent implements Comparable<DTOStudent>{

    @Id
    @Indexed
    private long id;

    private String name;
    private boolean inQueue;
    private Date inQueueTime;
    private long positionInQueue;

    @Override
    public int compareTo(DTOStudent o) {
        return getInQueueTime().compareTo(o.getInQueueTime());
    }
}
