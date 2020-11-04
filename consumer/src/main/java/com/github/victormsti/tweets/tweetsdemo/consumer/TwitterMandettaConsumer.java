package com.github.victormsti.tweets.tweetsdemo.consumer;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class TwitterMandettaConsumer {
    public static RestHighLevelClient createClient(){
        String hostname = "YOUR_CREDENTIALS";
        String username = "YOUR_CREDENTIALS";
        String password = "YOUR_CREDENTIALS";

        // Don't do it if you run a local ES
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username,password));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost(hostname,443,"https"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

    public static KafkaConsumer<String,String> createConsumer(String topic){
        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "kafka_demo_elasticsearch2";

        // Create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Create consumer
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        // Subsctibe consumer to out topic(s)
        consumer.subscribe(Arrays.asList(topic));

        return consumer;
    }
    public static void main(String[] args) throws IOException {
        Logger logger = LoggerFactory.getLogger(TwitterMandettaConsumer.class.getName());
        RestHighLevelClient client = createClient();


        KafkaConsumer<String,String> consumer = createConsumer("mandetta_tweets");

        // Poll for new data
        while (true){
            ConsumerRecords<String,String> records =
                    consumer.poll(Duration.ofMillis(100)); // new in Kafka 2.0.0

            for(ConsumerRecord<String,String> record : records){

                // 2 strateies
                // 1- Kafka custom ID
                String id = record.topic() + "_" + record.partition() + "_" + record.offset();

                //2- twitter feed specific id
                //String id = extractIdFromTweet(record.value());

                // Where we insert data into ElasticSearch
                IndexRequest indexRequest = new IndexRequest(
                        "tweets-mandetta",
                        "tweets",
                        id // This is to make our consumer idempotent
                ).source("{}", XContentType.JSON);
                IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
                logger.info(indexResponse.getId());
                /*try {
                    Thread.sleep(1000); // Introduce a small delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }

        // Close the client gracefully
        //client.close();
    }
}
