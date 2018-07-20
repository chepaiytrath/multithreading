package producerconsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import producerconsumer.WaitNotifyExample.Consumer;
import producerconsumer.WaitNotifyExample.Producer;

public class BlockingQueueExample {
	private static final int MIN = 1;
	private static final int MAX = 3;
	private static BlockingQueue<Integer> q = new LinkedBlockingQueue<>(MAX);
	private static int val = 0;

	public static void main(String[] args) {
		for(int i = 0; i < 4; i++) {
			new Thread(new Producer()).start();
		}
		
		for(int i = 0; i < 4; i++) {
			new Thread(new Consumer()).start();
		}
	}

	static class Producer1 implements Runnable {
		Random random = new Random();
		@Override
		public void run() {
			while (true) {
				try {
					q.put(random.nextInt(MAX));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(q);
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	static class Consumer1 implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					q.take();
					val--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(q);
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
