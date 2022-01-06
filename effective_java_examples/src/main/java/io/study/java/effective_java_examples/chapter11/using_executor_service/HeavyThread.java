package io.study.java.effective_java_examples.chapter11.using_executor_service;

public class HeavyThread extends Thread{

	private final String threadAlias;
	private final long createdMs;

	public HeavyThread(String threadAlias){
		createdMs = System.currentTimeMillis();
		this.threadAlias = threadAlias;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(3000L);
			System.out.println(threadAlias + " + is Done. It tooks " + (System.currentTimeMillis() - createdMs));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
