package com.interview.brushups.multithreading;

class Customer {
    int amount = 10000;
    synchronized void withdraw(int amount) {
        System.out.println("going to withdraw...");

        if (this.amount < amount) {
            System.out.println("Current Balance :"+this.amount);
            System.out.println("Requested Withdraw Amount :"+amount);
            System.out.println("Less balance; waiting for deposit...");
            try {
                wait(); //this will release the lock, and wait for notify() signal to resume
            } catch (Exception e) {
            }
        }
        this.amount -= amount;
        System.out.println("withdraw completed..."+"Left Balance "+this.amount);
    }

    synchronized void deposit(int amount) {
        System.out.println("going to deposit..."+amount);
        this.amount += amount;
        System.out.println("deposit completed... "+"New Total "+this.amount);
        notify();
    }
}

class Test {
    public static void main(String args[]) {
        final Customer c = new Customer();
        new Thread() {
            public void run() {
                c.withdraw(15000);
            }
        }.start();
        new Thread() {
            public void run() {
                c.deposit(5000);
            }
        }.start();

    }
}