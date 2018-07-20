package cyclicbarrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo4 {

	public static CyclicBarrier cyclicBarrier;
	public static List<List<Integer>> partialResults = Collections.synchronizedList(new ArrayList<>());
	public static Random random = new Random();
	public static int NUM_PARTIAL_RESULTS;
	public static int NUM_WORKERS;

	public static void main(String[] args) {
		CyclicBarrierDemo4 play = new CyclicBarrierDemo4();
		play.runSimulation(5, 3);
	}
	private void runSimulation(int numWorkers, int numberOfPartialResults) {
		NUM_PARTIAL_RESULTS = numberOfPartialResults;
		NUM_WORKERS = numWorkers;

		cyclicBarrier = new CyclicBarrier(NUM_WORKERS, new AggregatorThread());
		System.out.println("Spawning " + NUM_WORKERS + " worker threads to compute " + NUM_PARTIAL_RESULTS
				+ " partial results each");
		for (int i = 0; i < NUM_WORKERS; i++) {
			Thread worker = new Thread(new NumberCruncherThread());
			worker.setName("Thread " + i);
			worker.start();
		}
	}

}

class NumberCruncherThread implements Runnable {

	@Override
	public void run() {
		String thisThreadName = Thread.currentThread().getName();
		List<Integer> partialResult = new ArrayList<>();
		for (int i = 0; i < CyclicBarrierDemo4.NUM_PARTIAL_RESULTS; i++) {
			System.out.println(thisThreadName + ": Crunching some numbers! Final result - " + i);
			partialResult.add(i);
		}
		CyclicBarrierDemo4.partialResults.add(partialResult);
		try {
			System.out.println(thisThreadName + " waiting for others to reach barrier.");
			CyclicBarrierDemo4.cyclicBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}

class AggregatorThread implements Runnable {

	@Override
	public void run() {
		String thisThreadName = Thread.currentThread().getName();
		System.out.println(thisThreadName + ": Computing final sum of " + CyclicBarrierDemo4.NUM_WORKERS + " workers, having "
				+ CyclicBarrierDemo4.NUM_PARTIAL_RESULTS + " results each.");
		int sum = 0;
		for (List<Integer> threadResult : CyclicBarrierDemo4.partialResults) {
			System.out.print("Adding ");
			for (Integer partialResult : threadResult) {
				System.out.print(partialResult + " ");
				sum += partialResult;
			}
			System.out.println();
		}
		System.out.println(Thread.currentThread().getName() + ": Final result = " + sum);
	}

}