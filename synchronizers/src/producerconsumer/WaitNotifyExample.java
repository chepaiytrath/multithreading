package producerconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaitNotifyExample {
	private static List<Integer> list = new ArrayList<>();
	private static final int MIN = 0;
	private static final int MAX = 5;

	public static void main(String[] args) throws InterruptedException {
		for(int i = 0; i < 4; i++) {
			new Thread(new Producer()).start();
		}
		
		for(int i = 0; i < 4; i++) {
			new Thread(new Consumer()).start();
		}
	}

	static class Producer implements Runnable {
		Random random = new Random();

		@Override
		public void run() {
			while (true) {
				synchronized (list) {
					while (list.size() == MAX) {
						try {
							list.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					list.add(random.nextInt(MAX));
					System.out.println(list);
					list.notify();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	static class Consumer implements Runnable {
		@Override
		public void run() {
			while (true) {
				synchronized (list) {
					while (list.size() == MIN) {
						try {
							list.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					list.remove(list.size() - 1);
					System.out.println(list);
					list.notify();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

}
