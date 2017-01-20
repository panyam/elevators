package com.panyam;

public class Main {
    public static void main(String[] args) {
        Scheduler sc = new Scheduler(5, 50);
        sc.start();

        while (true) {
            // Something happens here
        }
    }
}
