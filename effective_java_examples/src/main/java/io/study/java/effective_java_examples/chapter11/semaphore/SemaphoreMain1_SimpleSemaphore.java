package io.study.java.effective_java_examples.chapter11.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreMain1_SimpleSemaphore {
	public static void main(String [] args) throws InterruptedException {
		Semaphore semaphore = new Semaphore(5);

		semaphore.acquire(2);
		System.out.println("semaphore >> acquired thread (" + 2 + ") ... ");
		System.out.println("semaphore >> now available thread pool space = " + semaphore.availablePermits() + ".");

		System.out.println("semaphore >> 2 threads's tasks are finished.");
		semaphore.release(2);
		System.out.println("semaphore >> now available thread pool space = " + semaphore.availablePermits() + ".");
	}
}
