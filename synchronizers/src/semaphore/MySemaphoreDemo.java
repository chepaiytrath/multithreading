package semaphore;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySemaphoreDemo {
	public static void main(String[] args) {
		ExecutorService ex = Executors.newCachedThreadPool();
		MySemaphore sem = new MySemaphore(10);
		//MySemaphore sem = new MySemaphore(1); Binary Semaphore
		//sem.release(); : adds an extra permit befor starting the execution.
		for (int i = 1; i <= 50; i++) {
			ex.submit(new MyTask(sem));
		}
		ex.shutdown();
	}
}

class MySemaphore {
	int signal = 0;

	MySemaphore(int signal) {
		this.signal = signal;
	}

	public synchronized void acquire() throws InterruptedException {
		while (signal == 0) {
			wait();
		}
		signal--;
	}

	public synchronized void release() {
		signal++;
		notify();
	}
}

class MyTask implements Runnable {
	MySemaphore sem = null;

	public MyTask(MySemaphore s) {
		sem = s;
	}

	@Override
	public void run() {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(2000);
		}catch(InterruptedException ex) {
			
		}finally {
			sem.release();
		}
	}
}