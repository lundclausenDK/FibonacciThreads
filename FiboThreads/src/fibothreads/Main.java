package fibothreads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Thread {

    static long sum = 0;
    static boolean pRun = true;
    static boolean cRun = true;

    public static void main(String[] args) throws InterruptedException {

        long[] rootNums = {4, 5, 8, 12, 21, 22, 34, 35, 36, 37, 42};

        BlockingQueue S1 = new ArrayBlockingQueue(11);
        BlockingQueue S2 = new ArrayBlockingQueue(11);

        for (int i = 0; i < rootNums.length; i++) {
            long number = rootNums[i];
            S1.put(number);
        }

        Thread P1 = new Thread(() -> {
            try {
                while (pRun) {
                    Object temp = S1.poll();
                    if (temp == null) {
                        pRun = false;
                    } else {
                        long calcTemp = new Fib().fib((long) temp);
                        S2.put(calcTemp);
                        //System.out.println("P1: " + calcTemp);
                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Thread P2 = new Thread(() -> {
            try {
                while (pRun) {
                    Object temp = S1.poll();
                    if (temp == null) {
                        pRun = false;
                    } else {
                        long calcTemp = new Fib().fib((long) temp);
                        S2.put(calcTemp);
                        //System.out.println("P2: " + calcTemp);

                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Thread P3 = new Thread(() -> {
            try {
                while (pRun) {
                    Object temp = S1.poll();
                    if (temp == null) {
                        pRun = false;
                    } else {
                        long calcTemp = new Fib().fib((long) temp);
                        S2.put(calcTemp);
                        //System.out.println("P3: " + calcTemp);

                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Thread P4 = new Thread(() -> {
            try {
                while (pRun) {
                    Object temp = S1.poll();
                    if (temp == null) {
                        pRun = false;
                    } else {
                        long calcTemp = new Fib().fib((long) temp);
                        S2.put(calcTemp);
                        //System.out.println("P4: " + calcTemp);

                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Thread C1 = new Thread(() -> {
            try {
                while (cRun) {
                    Object newNum = (long) S2.take();
                    sum += (long) newNum;

                    System.out.println("C1 take: " + newNum);

                    if (S2.isEmpty() && !P1.isAlive()&& !P2.isAlive() && !P3.isAlive() && !P4.isAlive()) {
                        cRun = false;
                        System.out.println("C1 sum: " + Main.sum);
                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );

        P1.start();
        P2.start();
        P3.start();
        P4.start();
        C1.start();

    }

}

/* ANSWERS:

    - More threads can do more things at the same time, which is faster.
    - RaceCondition problem can when multiple threads are reading and writing to eg. the same variables, with eg. number++
    - Producer/Consumer problem is when a producer trys to put in an element and the array is full, locks and BlockingQueue is the solution
    - Busy waiting is when all thread workers contantly tries to do their job, instead of auto-sleep until they are woken up to work
    - BlockingQueue, take care of the Producer/Consumer problem by ask the workers to wait and start automatically

*/
