package countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountdownLatchDemo {
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		CountDownLatch cdl = new CountDownLatch(10);
		for (int i = 1; i <= 10; i++) {
			es.submit(new Process1(cdl));
		}
		try {
			cdl.await();
			cdl.await(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//cdl.await() ensures that below code runs after the code for 10 child threads spawned above is executed. Results are not mixed.
		for (int i = 1; i <= 3; i++) {
			System.out.println("Thread" + Thread.currentThread().getName() + " : " + i);
		}
		es.shutdown();
	}
}

class Process1 implements Runnable {
	CountDownLatch cdl;

	public Process1(CountDownLatch cdl) {
		this.cdl = cdl;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 1; i <= 3; i++) {
			System.out.println("Thread" + Thread.currentThread().getName() + " : " + i);
		}
		cdl.countDown();
	}

}