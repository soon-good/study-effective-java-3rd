package io.study.java.effective_java_examples.item11.semaphore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class SemaphoreMain2_CachedExecutorService {

	private final ExecutorService executorService;
	private final Semaphore semaphore;

	public SemaphoreMain2_CachedExecutorService(
		ExecutorService executorService, int limit
	){
		this.executorService = executorService;
		this.semaphore = new Semaphore(limit);
	}

	public <T> Future<T> submit(final Callable<T> task) throws InterruptedException {
		semaphore.acquire();
		System.out.println("semaphore.acquire() ... ");

		return executorService.submit(()->{
			try{
				return task.call();
			}
			finally {
				semaphore.release();
				System.out.println("semaphore.release() ... ");
			}
		});
	}

	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public static void main(String [] args) throws InterruptedException {

		ExecutorService executorService = Executors.newCachedThreadPool();

		SemaphoreMain2_CachedExecutorService execService =
			new SemaphoreMain2_CachedExecutorService(executorService, 2);


		execService.submit(()->{
			System.out.println("Task 1 is running. " + getCurrentDateTime());
			Thread.sleep(2000L);
			System.out.println("Task 1 is done. " + getCurrentDateTime());
			return 0;
		});

		execService.submit(()->{
			System.out.println("Task 2 is running. " + getCurrentDateTime());
			Thread.sleep(2000L);
			System.out.println("Task 2 is done. " + getCurrentDateTime());
			return 0;
		});

		execService.submit(()->{
			System.out.println("Task 3 is running. " + getCurrentDateTime());
			Thread.sleep(2000L);
			System.out.println("Task 3 is done. " + getCurrentDateTime());
			return 0;
		});

		execService.submit(()->{
			System.out.println("Task 4 is running. " + getCurrentDateTime());
			Thread.sleep(2000L);
			System.out.println("Task 4 is done. " + getCurrentDateTime());
			return 0;
		});

		execService.submit(()->{
			System.out.println("Task 5 is running. " + getCurrentDateTime());
			Thread.sleep(2000L);
			System.out.println("Task 5 is done. " + getCurrentDateTime());
			return 0;
		});

		executorService.shutdown();
	}

	private static String getCurrentDateTime(){
		return LocalDateTime.now().format(formatter);
	}
}
