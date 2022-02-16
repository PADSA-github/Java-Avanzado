package com.adl.database;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.adl.model.student;

import java.sql.SQLException;
import java.util.List;


public interface Database {

    void shutdown();
    void insertMessageToDB(List<ConsumerRecord<String, student>> records) throws Exception;
    void saveOffset(String topic, int partition, long offset) throws Exception;
    long getOffset(String topic, int partition) throws SQLException;
}
