/**
 *@copyright Copyright (c) 2008 - 2020
 *@company Giantstone
 */
package buguw.com.common.thread;


/**
 *@author 方宗虎
 *@since 2012-5-26
 *@version 1.1.0 
 */
public class RunMyThread {

    /**
     * @param args void
     */
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            MyThread myThread = new MyThread("Thread----->"+i);
            myThread.start();
            
        }
        
        while (true) {
            
        }
    }

}
