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
public class MyThread extends Thread {

    private String threadName;
    
    /**
     * @param threadName
     */
    public MyThread(String threadName) {
        super();
        this.threadName = threadName;
        this.setName(this.threadName);
        this.setDaemon(true);
        
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        while(true){
            System.out.println("Thread name:"+threadName);
        }
    }
    
}
