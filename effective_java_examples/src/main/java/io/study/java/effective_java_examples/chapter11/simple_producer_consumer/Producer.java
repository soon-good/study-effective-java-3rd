package io.study.java.effective_java_examples.chapter11.simple_producer_consumer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.study.java.effective_java_examples.utils.DateFormatters;

// https://minholee93.tistory.com/entry/Java-Multithreading-Producer-Consumer
public class Producer extends Thread{
	private static final int MAX_SIZE = 5;
	private final List<String> queue = new ArrayList<>();

	@Override
	public void run() {
		try{
			while(true){
				produce();
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private synchronized void produce() throws Exception{
		while(queue.size() == MAX_SIZE){
			System.out.println(">>> [Producer (Queue FULL)] QUEUE MAX SIZE 도달. Consumer가 Producer 가 깨울때(=notify)까지 기다리기 시작합니다.");
			wait();
			System.out.println(">>> [Producer (Queue FULL)] Consumer가 Producer를 깨웠고(=notify), queue size 가 MAX가 아니라면 탈출합니다.");
		}
		String data = LocalDateTime.now().format(DateFormatters.yyyyMMddHHmmssSSS);
		queue.add(data);
		System.out.println("[Producer] ADD >>> " + data);
		notify();
	}

	public synchronized String consume() throws Exception{
		notify();
		while(queue.isEmpty()){
			System.out.println("<<< [Consumer (Queue EMPTY)] Queue 가 모두 비어서 대기합니다.");
			wait();
			System.out.println("<<< [Consumer (Queue EMPTY)] Producer 가 queue 를 채우면서 생산자를 notify 햇습니다.");
		}
		String data = queue.get(0);
		System.out.println("[Consumer] REMOVE >>> " + data);
		queue.remove(data);
		return data;
	}
}
