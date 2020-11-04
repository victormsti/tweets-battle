package com.github.victormsti.tweets.tweetsdemo.service;

import com.github.victormsti.tweets.tweetsdemo.model.Tweet;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TweetsService {

    Tweet tweet;
    List<Tweet> tweets;
    String hostname = "YOUR_CREDENTIALS";
    String username = "YOUR_CREDENTIALS";
    String password = "YOUR_CREDENTIALS";
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();


    public TweetsService(){
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username,password));
    }

    public List<Tweet> getTweets(){
        tweets = new ArrayList<>();
        tweets.add(getDocumentsFromIndex("tweets-bolsonaro", "Bolsonaro"));
        /*tweets.add(getDocumentsFromIndex("tweets-mandetta", "Mandetta"));
        tweets.add(getDocumentsFromIndex("tweets-isolamento-vertical", "Isolamento Vertical"));
        tweets.add(getDocumentsFromIndex("tweets-isolamento-horizontal", "Isolamento Horizontal"));
        tweets.add(getDocumentsFromIndex("tweets-fique-em-casa", "Fique em casa"));
        tweets.add(getDocumentsFromIndex("tweets-voltar-a-trabalhar", "Voltar a trabalhar"));*/
        tweets.add(getDocumentsFromIndex("tweets-covid", "COVID"));
        return tweets;
    }

    private Tweet getDocumentsFromIndex(String index, String name){

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.size(0);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = null;
        try {
            searchResponse = createClient().search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String scrollId = searchResponse.getScrollId();
        SearchHits hits = searchResponse.getHits();

        if(name != null) {
            tweet = new Tweet();
            tweet.setName(name);
            tweet.setQuantity(searchResponse.getHits().getTotalHits());
            return tweet;
        }
        else {
            return null;
        }
    }

    private RestHighLevelClient createClient() {

        // Don't do it if you run a local ES
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost(hostname, 443, "https"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}
