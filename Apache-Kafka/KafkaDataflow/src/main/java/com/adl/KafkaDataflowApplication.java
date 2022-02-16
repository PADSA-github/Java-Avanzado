package com.adl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

import com.adl.consumer.Consumer;
import com.adl.consumer.ConsumerGroup;
import com.adl.consumer.ConsumerThread;
import com.adl.database.MySQLDatabase;
import com.adl.model.student;
import com.adl.reader.ReadCSV;
import com.adl.reader.serializer.JsonSerializer;




@SpringBootApplication
public class KafkaDataflowApplication {
	
	static String nombreTopic= "nuevosEstudiantes";

	@Bean
	    public NewTopic topic() {
	        return TopicBuilder.name(nombreTopic)
	                .partitions(5)
	                .replicas(1)
	                .build();
	        }

	public static void main(String[] args) throws FileNotFoundException, IOException {
	 final Logger logger = LogManager.getLogger();
		SpringApplication.run(KafkaDataflowApplication.class, args);
		
		 Properties props = new Properties();
	        props.put(ProducerConfig.CLIENT_ID_CONFIG,"my-app-readcsv");
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

	       
	        
	        logger.info("Producer has been created...Start sending Student Record ");

	        KafkaProducer<String,student> producer = new KafkaProducer<String,student>(props);

	        ReadCSV readCSV = new ReadCSV();
	        List studentList = readCSV.ReadCSVFile(); 
	        for (Object studentObject : studentList) {
	            student stdobject = (student) studentObject;
	            producer.send(new ProducerRecord<String, student>(nombreTopic,stdobject.getDept(),stdobject));
	         }
	        logger.info("Producer has sent all employee records successfully...");
	        producer.close();
		
		
	        String databaseConfig = "src\\main\\resources\\DB.properties";
	        MySQLDatabase database = new MySQLDatabase(databaseConfig);

	        String consumerConfig = "src\\main\\resources\\Consumer.properties";
	        Properties consumerProperties = new Properties();
	        consumerProperties.load(new FileInputStream(consumerConfig));

	        ConsumerGroup consumerGroup = new ConsumerGroup(consumerProperties, database);

	        List<Consumer> consumers = new ArrayList<>();
	        for (int i = 0; i < Integer.parseInt(consumerProperties.getProperty("number.of.consumers")); i++) {
	            consumers.add(new ConsumerThread(consumerProperties, database));
	        }

	        consumerGroup.assignListConsumers(consumers);

	        consumerGroup.run();
	

	}

}
