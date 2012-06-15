/**
 *@copyright Copyright (c) 2008 - 2020
 *@company Giantstone
 */
package buguw.com.common.threadpoolTest;

import buguw.com.common.threadpool.Server;
import buguw.com.common.threadpool.WorkQueue;

import com.giantstone.common.config.ConfigManager;

/**
 * @author 方宗虎
 * @since 2012-4-24
 * @version 1.1.0
 */
public class ThreadpoolTest extends Thread {

    /**
     * 初始化
     */
    static {
        ConfigManager.setRootPath("config");
    }

    public static Server server = new Server();

    /**
     * 向线程池加任务
     */
    public void run() {
        boolean flg = false;

        for (int i = 0; i < 200; i++) {
            Say sayTask = new Say("Hell Word number " + i);
            flg = WorkQueue.putTask(sayTask);
            while (!flg) {
                flg = WorkQueue.putTask(sayTask);
            }
        }
    }

    public static void main(String[] args) {
        
        server.startThreadPool();//启动服务器
        ThreadpoolTest threadpoolTest = new ThreadpoolTest();
        threadpoolTest.run();// 向线程池加任务

        while (true) {
        }

    }
}
