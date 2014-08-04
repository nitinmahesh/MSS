package com.threads;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		/*A a = new A();
		
		a.start();*/
		
		B b = new B();
		
		Thread t  = new Thread(b);
		Thread t1 = new Thread(b);
		t.setName("t");
		t1.setName("t1");
		t.start();
		t1.start();
		t1.join();
		
	}
}
