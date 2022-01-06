package io.study.java.effective_java_examples.item11.simple_producer_consumer;

public class Main {
	public static void main(String [] args){
		Producer producer = new Producer();
		producer.setName("Producer #1 ");
		producer.start();

		Consumer c1 = new Consumer(producer);
		c1.setName("Consumer #1 ");
		c1.start();

		Consumer c2 = new Consumer(producer);
		c2.setName("Consumer #2 ");
		c2.start();

		Consumer c3 = new Consumer(producer);
		c3.setName("Consumer #3 ");
		c3.start();
	}
}
