package A;

import java.time.LocalTime;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;



public class Wyscig {
	final private static int LiczbaUczestnikow = 4;
	final private static CountDownLatch start = new CountDownLatch(1);
	final private static CountDownLatch ukonczenieWyscigu = new CountDownLatch(LiczbaUczestnikow);
	final private Queue<Integer> meta = new ConcurrentLinkedQueue<Integer>();

	public static void main(String[] args) {
		try {
			Wyscig wy = new Wyscig();
			for (int i = 0; i < LiczbaUczestnikow; i++)
				new Uczestnik(wy,i).start();
			wy.ZaczecieWyscigu();
			wy.finishExam();
		} catch (InterruptedException e) {
			System.err.printf("Sedzia: wyscig jest przerwany!");
		}
	}
	
	
	void Uczestnictwo(Uczestnik u) throws InterruptedException {
		Wyscig.start.await();
		System.out.println("Uczestnik " + u.numer + " zaczal    wyscig o "+ LocalTime.now());
	}

	void returnAnswer(Uczestnik u, int numer) {
		System.out.println("Uczestnik " + numer + " ukonczyl bieg o " + LocalTime.now());
		this.meta.add(numer);
		Wyscig.ukonczenieWyscigu.countDown();
	}

	private void ZaczecieWyscigu() {
		System.out.println("Sedzia    zaczyna    wyscig o " + LocalTime.now());
		Wyscig.start.countDown();
		
	}

	private void finishExam() throws InterruptedException {
		Wyscig.ukonczenieWyscigu.await();
		System.out.println("Sedzia    zakonczyl wyscig o " +  LocalTime.now());
		System.out.println("Uczestnicy dobiegli w kolejnoœci: " + meta);
	}

	
}

class Uczestnik extends Thread {
	final private Wyscig wy;
	final int numer;

	Uczestnik(Wyscig wy, int numer) {
		this.wy = wy;
		this.numer = numer;
	}

	@Override
	public void run() {
		try {
			wy.Uczestnictwo(this);
			bieg();
			wy.returnAnswer(this, numer);
		} catch (InterruptedException e) {
			System.err.printf("Uczestnik  %2d: dla mnie to koniec wyscigu", this.getId());
		}
	}

	private void bieg() throws InterruptedException {
		Random gen = new Random();
		TimeUnit.SECONDS.sleep(gen.nextInt(15));
	}
}
