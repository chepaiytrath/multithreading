package cyclicbarrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

//Mixing up CyclicBarrier with CountdownLatch
public class CyclicBarrierDemoAndCountDownLatch {
	

	public static void main(String[] args) {
		// IMPORTANT to use Synchronized list. Otherwise random insertion
		List<Integer> list = Collections.synchronizedList(new ArrayList<>());
		CountDownLatch cdl = new CountDownLatch(2);
		// BarrierAction: when the barrier is tripped Process6 barrier action will be
		// executed, performed by the last thread entering the barrier
		// No other thread can interfere while barrier action is being executed
		CyclicBarrier cb = new CyclicBarrier(2, new Process6(list));
		
		for (int i = 0; i < 2; i++) {
			Thread t = new Thread(new Process5(cb, cdl, list), i + "");
			t.start();
		}
		// cb.reset();
		try {
			cdl.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Now main thread resumes");
		System.out.println("NumberWaiting : "+cb.getNumberWaiting());
		System.out.println("Parties : "+cb.getParties());
		System.out.println("isBroken : "+cb.isBroken());
	}
}

class Process5 implements Runnable {
	CyclicBarrier cb;
	CountDownLatch cdl; 
	List<Integer> list;
	
	public Process5(CyclicBarrier cb, CountDownLatch cdl, List<Integer> list) {
		this.cb = cb;
		this.cdl = cdl;
		this.list = list;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		for (int i = 1; i <= 3; i++) {
			list.add(i);
			System.out.println(Thread.currentThread().getName() + " added = " + i);
			System.out.println("List is now: " + list);
		}

		try {
			System.out.println(Thread.currentThread().getName() + " waiting for others to reach barrier.");
			cb.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 4; i <= 6; i++) {
			System.out.println(Thread.currentThread().getName() + " added at last = " + i);
			list.add(i);
		}
		cdl.countDown();
	}
}

class Process6 implements Runnable {
	List<Integer> list;
	
	public Process6(List<Integer> list) {
		this.list = list;
	}

	@Override
	public void run() {
		int sum = 0;
		System.out.println("List: " + list);
		for (int i : list) {
			sum += i;
		}
		System.out.println("Total sum Process6 = " + sum);
		// CyclicBarrierDemo5.cdl.countDown();
	}

}