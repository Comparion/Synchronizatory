package B;

import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ObiadUBabci {

	static CyclicBarrier b;

	public static void main(String[] args) {

		Runnable babcia = new Runnable() {
			public void run() {
				System.out.println("Prosze jeszcze dok�ada!");
			}
		};

		b = new CyclicBarrier(5,babcia);
		
		Vector<Thread> vec = new Vector<Thread>();
		
		for(int i=0;i<5;i++)
		{
			int p = i;
			vec.add(new Thread(){
			    public void run(){
			        Obiad(p,b);
			      }
			    });
		};
		
		for(Thread t: vec)
		{
			t.start();
		}
		
		 Runnable wnuczek =  new Runnable() {
				public void run() {
					System.out.println("Komu jeszcze dok�adki??");
				}
			};

	}

static void Obiad(int i, CyclicBarrier b)
{
	System.out.println("Wnuczek "+ i + " ju� zdjad�em babciu, poprosz� dok�adk�!");
    try {
		b.await();
	} catch (InterruptedException e) {
		e.printStackTrace();
	} catch (BrokenBarrierException e) {
		e.printStackTrace();
	}
    System.out.println("Wnuczek "+ i + " by�o ci�ko to zje�� babciu.");
    try {
		b.await();
	} catch (InterruptedException e) {
		e.printStackTrace();
	} catch (BrokenBarrierException e) {
		e.printStackTrace();
	}
    System.out.println("Wnuczek "+ i + " babciu ja ju� nie mog�!");
    try {
		b.await();
	} catch (InterruptedException e) {
		e.printStackTrace();
	} catch (BrokenBarrierException e) {
		e.printStackTrace();
	}
    System.out.println("Czuj�, �e zaraz p�kn�!!!");
}
	
}


  
 