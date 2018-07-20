package producerconsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerBlockingQueue {

	static int MAX_SIZE = 5;
	static BlockingQueue queue = new LinkedBlockingQueue(MAX_SIZE);

	public static void main(String[] args) {

		Producer producer = new Producer();
		Consumer consumer = new Consumer();
		producer.start();
		consumer.start();
	}

	static class Producer extends Thread {
		Random random = new Random();

		public void run() {
			while (true) {
				int element = random.nextInt(MAX_SIZE);
				try {
					queue.put(element);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	static class Consumer extends Thread {
		public void run() {
			while (true) {
				try {
					System.out.println("Consumed " + queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}