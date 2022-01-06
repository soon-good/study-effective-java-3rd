package io.study.java.effective_java_examples.chapter11.simple_producer_consumer;

public class Consumer extends Thread{

	private Producer producer;

	public Consumer(Producer producer){
		this.producer = producer;
	}

	@Override
	public void run() {
		try{
			while(true){
				String data = producer.consume();
				System.out.println("[Consumer] Cosumer consumed data = " + data + ", at" + Thread.currentThread().getName());
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
