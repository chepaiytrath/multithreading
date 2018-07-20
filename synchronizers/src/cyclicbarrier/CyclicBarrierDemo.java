package cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//3 threads including main
//Firstly, 1 to 5 is printed in random order for the 3 threads.
//For each thread completing 5, cb.await is called
//Once 5 is printed for all 3 threads, i.e. the last cb.await() is executed, it means that all threads waiting on others can now resume their activity
//Resume printing 6 to 10: which is done in random order

//Same process
public class CyclicBarrierDemo {
	public static void main(String[] args) {
		CyclicBarrier cb = new CyclicBarrier(3);
		
		for(int i = 0; i <2; i++) {
			Thread t = new Thread(new Process(cb), i+"");
			t.start();
		}
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
	}
}

class Process implements Runnable {
	CyclicBarrier cb = null;

	public Process(CyclicBarrier cb) {
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