package io.study.java.effective_java_examples.item11.using_executor_service.new_singleton_thread_executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.study.java.effective_java_examples.item11.using_executor_service.HeavyThread;

public class NewSingleThreadExecutorMain {
	public static void main(String [] args){
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		executorService.execute(new HeavyThread("HeavyTh #1"));
		executorService.execute(new HeavyThread("HeavyTh #2"));
		executorService.execute(new HeavyThread("HeavyTh #3"));
		executorService.execute(new HeavyThread("HeavyTh #4"));
		executorService.execute(new HeavyThread("HeavyTh #5"));

		executorService.shutdown();
	}
}
