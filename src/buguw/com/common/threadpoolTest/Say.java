/**
 *@copyright Copyright (c) 2008 - 2020
 *@company Giantstone
 */
package buguw.com.common.threadpoolTest;

import buguw.com.common.threadpool.ThreadPoolTask;


/**
 *@author 方宗虎
 *@since 2012-4-24
 *@version 1.1.0 
 */
public class Say implements ThreadPoolTask{

    private String sayInfo;
    /* (non-Javadoc)
     * @see buguw.com.common.threadpool.ThreadPoolTask#run()
     */
    @Override
    public void run() {
        SayHello sayHello = new SayHello();
        sayHello.say(sayInfo);
    }
    /**
     * @param taskName
     */
    public Say(String sayInfo) {
        super();
        this.sayInfo = sayInfo;
    }
    
}
