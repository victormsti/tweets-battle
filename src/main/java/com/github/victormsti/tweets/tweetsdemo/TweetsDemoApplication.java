package com.github.victormsti.tweets.tweetsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "com/github/victormsti/tweets/tweetsdemo")
public class TweetsDemoApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(TweetsDemoApplication.class, args);
		// Activate producers and consumers
		/*ExecutorService service = Executors.newFixedThreadPool(4);
		ExecutorService service2 = Executors.newFixedThreadPool(4);
		ExecutorService service3 = Executors.newFixedThreadPool(4);
		ExecutorService service4 = Executors.newFixedThreadPool(4);
		ExecutorService service5 = Executors.newFixedThreadPool(4);
		ExecutorService service6 = Executors.newFixedThreadPool(4);
		ExecutorService service7 = Executors.newFixedThreadPool(4);
		ExecutorService service8 = Executors.newFixedThreadPool(4);
		ExecutorService service9 = Executors.newFixedThreadPool(4);
		ExecutorService service10 = Executors.newFixedThreadPool(4);
		ExecutorService service11 = Executors.newFixedThreadPool(4);
		ExecutorService service12 = Executors.newFixedThreadPool(4);
		ExecutorService service13 = Executors.newFixedThreadPool(4);
		ExecutorService service14 = Executors.newFixedThreadPool(4);

		*//**
		 * PRODUCERS
		 *//*
		service.submit(new Runnable() {
			public void run() {
				TwitterBolsonaroProducer.main(null);
			}
		});
		service2.submit(new Runnable() {
			public void run() {
				TwitterMandettaProducer.main(null);
			}
		});
		service3.submit(new Runnable() {
			public void run() {
				TwitterIsolamentoHorizontalProducer.main(null);
			}
		});
		service4.submit(new Runnable() {
			public void run() {
				TwitterIsolamentoVerticalProducer.main(null);
			}
		});
		service5.submit(new Runnable() {
			public void run() {
				TwitterStayHomeProducer.main(null);
			}
		});
		service6.submit(new Runnable() {
			public void run() {
				TwitterBackToWorkProducer.main(null);
			}
		});
		service7.submit(new Runnable() {
			public void run() {
				TwitterCOVIDProducer.main(null);
			}
		});
		*//**
		 * CONSUMERS
		 *//*
		service8.submit(new Runnable() {
			public void run() {
				try {
					TwitterBolsonaroConsumer.main(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		service9.submit(new Runnable() {
			public void run() {
				try {
					TwitterMandettaConsumer.main(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		service10.submit(new Runnable() {
			public void run() {
				try {
					TwitterIsolamentoHorizontalConsumer.main(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		service11.submit(new Runnable() {
			public void run() {
				try {
					TwitterIsolamentoVerticalConsumer.main(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		service12.submit(new Runnable() {
			public void run() {
				try {
					TwitterStayHomeConsumer.main(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		service13.submit(new Runnable() {
			public void run() {
				try {
					TwitterBackToWorkConsumer.main(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		service14.submit(new Runnable() {
			public void run() {
				try {
					TwitterCOVIDConsumer.main(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	}
}
