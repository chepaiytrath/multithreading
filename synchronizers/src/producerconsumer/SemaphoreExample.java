package producerconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

//Unlike other examples, using semaphores limits queue size to 1.
public class SemaphoreExample {
	public static void main(String[] args) {
		Semaphore semProd = new Semaphore(1);
		Semaphore semCon = new Semaphore(0);
		List<Integer> list = new ArrayList<>();
		new Thread(new ProducerSem(semProd, semCon, list)).start();
		new Thread(new ProducerSem(semProd, semCon, list)).start();
		new Thread(new ConsumerSem(semProd, semCon, list)).start();
	}
}

class ProducerSem implements Runnable {
	Semaphore semProd = null;
	Semaphore semCon = null;
	List<Integer> list = new ArrayList<>();

	public ProducerSem(Semaphore semProd, Semaphore semCon, List<Integer> list) {
		super();
		this.semProd = semProd;
		this.semCon = semCon;
		this.list = list;
	}

	@Override
	public void run() {
		Random random = new Random();
		while (true) {
			try {
				semProd.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(random.nextInt(5));
			System.out.println(list);
			semCon.release();
		}
	}
}

class ConsumerSem implements Runnable {
	Semaphore semProd = null;
	Semaphore semCon = null;
	List<Integer> list = new ArrayList<>();

	public ConsumerSem(Semaphore semProd, Semaphore semCon, List<Integer> list) {
		super();
		this.semProd = semProd;
		this.semCon = semCon;
		this.list = list;
	}

	@Override
	public void run() {
		while(true) {
			try {
				semCon.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.remove(list.size() - 1);
			System.out.println(list);
			semProd.release();
		}
	}

}
