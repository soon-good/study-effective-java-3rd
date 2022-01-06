package io.study.java.effective_java_examples.item11.using_executor_service.new_cached_thread_executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.study.java.effective_java_examples.item11.using_executor_service.HeavyThread;

public class NewCachedThreadPoolExecutor {
	public static void main(String [] args){
		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.submit(new HeavyThread("HeavyThread #1"));
		executorService.submit(new HeavyThread("HeavyThread #2"));
		executorService.submit(new HeavyThread("HeavyThread #3"));
		executorService.submit(new HeavyThread("HeavyThread #4"));
		executorService.submit(new HeavyThread("HeavyThread #5"));

		executorService.shutdown();
	}
}
