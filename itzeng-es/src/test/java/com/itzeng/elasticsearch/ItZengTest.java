package com.itzeng.elasticsearch;

/**
 * Created by Administrator on 2020/2/28.
 * <p>
 * by author wz
 *
 * <p>
 * com.itzeng.elasticsearch
 */

public class ItZengTest {

   static class OneTest {



       //类对象锁
       public void OperationSubClassObject() {
           synchronized (OneTest.class) {
               System.out.println(Thread.currentThread().getName() + "" + Thread.currentThread().getId());

               try {
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }


       //任何对象锁
       Object object = new Object();

       public void OperationSubObjec() {
           synchronized (object) {
               System.out.println(Thread.currentThread().getName() + "" + Thread.currentThread().getId());

               try {
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }


       /*
       方法上
        */
        public synchronized void OperationSub() {
            System.out.println(Thread.currentThread().getName() + "" + Thread.currentThread().getId());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        OneTest oneTest = new OneTest();

        OneTest oneTest1 = new OneTest();

        Thread t1 = new Thread(() -> {
            oneTest.OperationSub();
        });

        Thread t2 = new Thread(() -> {
            oneTest1.OperationSub();
        });

        t1.start();
        t2.start();
    }
}
