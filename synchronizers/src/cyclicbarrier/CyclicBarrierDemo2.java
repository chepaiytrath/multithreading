package cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//Different processes
public class CyclicBarrierDemo2 {
	public static void main(String[] args) throws InterruptedException {
		CyclicBarrier cb = new CyclicBarrier(3);
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		es.submit(new Thread(new Process1(cb), "1"));
		es.submit(new Thread(new Process2(cb), "2"));
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 1; i <= 5; i++) {
			System.out.println("Thread:" + Thread.currentThread().getName() + " = " + i);
		}
		try {
			cb.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 6; i <= 10; i++) {
			System.out.println("Thread:" + Thread.currentThread().getName() + " = " + i);
		}
		
		
		es.shutdown();
		es.awaitTermination(1, TimeUnit.DAYS);
	}
}

class Process1 implements Runnable {
	CyclicBarrier cb = null;

	public Process1(CyclicBarrier cb) {
		this.cb = cb;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 1; i <= 5; i++) {
			System.out.println("Thread:" + Thread.currentThread().getName() + " = " + i);
		}

		try {
			cb.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 6; i <= 10; i++) {
			System.out.println("Thread:" + Thread.currentThread().getName() + " = " + i);
		}
	}

}
class Process2 implements Runnable {
	CyclicBarrier cb = null;

	public Process2(CyclicBarrier cb) {
		this.cb = cb;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 1; i <= 5; i++) {
			System.out.println("Thread:" + Thread.currentThread().getName() + " = " + 10*i);
		}

		try {
			cb.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 6; i <= 10; i++) {
			System.out.println("Thread:" + Thread.currentThread().getName() + " = " + 10*i);
		}
	}

}