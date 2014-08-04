package com.threads;

public class B implements Runnable{

	@Override
	public void run() {

		for(int j=0;j<5;j++)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread().getName()+" j: "+j);
		}
	}
	
	

}
