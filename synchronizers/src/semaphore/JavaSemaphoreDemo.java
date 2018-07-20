package semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class JavaSemaphoreDemo {
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 1; i <= 100; i++) {
			es.submit(new Runnable() {
				@Override
				public void run() {
					Task.getInstance().operation();
				}
			});
		}
		es.shutdown();
	}
}

class Task {
	Semaphore sem = new Semaphore(10);
	private int value;

	private static Task obj;

	private Task() {

	}

	public static Task getInstance() {
		if (obj == null) {
			synchronized (Task.class) {
				if (obj == null) {
					obj = new Task();
				}
			}
		}
		return obj;
	}
	public void operation() {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			doOperation();
		}catch(InterruptedException e) {
			
		}finally{
			sem.release();
		}
	}
	
	public void doOperation() throws InterruptedException {
		synchronized (this) {
			value++;
			System.out.println("Value = " + value);
		}
		Thread.sleep(2000);
		synchronized (this) {
			value--;
		}
	}
}