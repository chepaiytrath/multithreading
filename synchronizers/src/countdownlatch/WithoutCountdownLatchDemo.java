package countdownlatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WithoutCountdownLatchDemo {
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 1; i <= 10; i++) {
			es.submit(new Process2());
		}
		//Below code interferes with the 10 child threads spawned above. Results are mixed
		for (int i = 1; i <= 3; i++) {
			System.out.println("Thread" + Thread.currentThread().getName() + " : " + i);
		}
		es.shutdown();
	}
}

class Process2 implements Runnable {
	@Override
	public void run() {
		for (int i = 1; i <= 3; i++) {
			System.out.println("Thread" + Thread.currentThread().getName() + " : " + i);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}