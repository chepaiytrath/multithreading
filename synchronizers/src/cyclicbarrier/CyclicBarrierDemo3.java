package cyclicbarrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//3 threads including main
//Firstly, 1 to 5 is printed in random order for the 3 threads.
//For each thread completing 5, cb.await is called
//Once 5 is printed for all 3 threads, i.e. the last cb.await() is executed, it means that all threads waiting on others can now resume their activity
//Resume printing 6 to 10: which is done in random order

//Same process
public class CyclicBarrierDemo3 {
	//IMPORTANT to use Synchornized list. Otherwise randowm insertion
	public static List<Integer> list = Collections.synchronizedList(new ArrayList<>());
	//BarrierAction: when the barrier is tripped Process4 barrier action will be executed, performed by the last thread entering the barrier
	//No other thread can interfere while barrier action is being executed
	public static CyclicBarrier cb = new CyclicBarrier(2, new Process4());
	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			Thread t = new Thread(new Process3(), i + "");
			t.start();
		}
		cb.reset();
		System.out.println("NumberWaiting : "+cb.getNumberWaiting());
		System.out.println("Parties : "+cb.getParties());
		System.out.println("isBroken : "+cb.isBroken());
	}
}

class Process3 implements Runnable {
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		for (int i = 1; i <= 3; i++) {
			System.out.println(Thread.currentThread().getName() + " added = " + i);
			CyclicBarrierDemo3.list.add(i);
			System.out.println("List is now: "+CyclicBarrierDemo3.list);
		}

		try {
			System.out.println(Thread.currentThread().getName() + " waiting for others to reach barrier.");
			CyclicBarrierDemo3.cb.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 4; i <= 6; i++) {
			System.out.println(Thread.currentThread().getName() + " added at last = " + i);
			CyclicBarrierDemo3.list.add(i);
		}
	}
}

class Process4 implements Runnable {
	@Override
	public void run() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int sum = 0;
		System.out.println("List: " +CyclicBarrierDemo3.list);
		for(int i : CyclicBarrierDemo3.list) {
			sum+=i;
		}
		System.out.println("Total sum = " + sum);
	}

}