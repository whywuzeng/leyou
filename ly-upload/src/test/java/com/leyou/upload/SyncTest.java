package com.leyou.upload;

/**
 * Created by Administrator on 2020/2/26.
 * <p>
 * by author wz
 * <p>
 * com.leyou.upload
 */

public class SyncTest {
    static class Main{
        public int i=10;
        public synchronized void operationSup(){
            i--;
            System.out.println("main print i = :"+i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class subMain extends Main{
        public synchronized void operationSub(){
            while (i>0){
                i--;
                System.out.println("Sub print i = :"+i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                operationSup();
            }
        }
    }

    public static void main(String[] args)
    {
        Thread thread = new Thread(() -> {
            subMain subMain = new subMain();
            subMain.operationSub();
        });

        thread.start();

    }
}
