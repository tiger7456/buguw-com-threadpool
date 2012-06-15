package buguw.com.common.threadpool;

import org.apache.log4j.Logger;

/**
 *任务处理器线程
 * @author 刘恭亮
 *@since 2012-3-22
 *@version 1.1.0
 */
public class Processor extends Thread {
    /**
     * 日志
     */
    private Logger log = null;

    /**
     * 构造
     */
    public Processor() {
    }
    
    /**
     * 运行
     */
    public void run() {
        ThreadPoolTask task = null;
        while (ProcessorPool.isRun()) {
            // 1.取得一个任务
            task = WorkQueue.getTask();
            if (null == task) {
                continue;
            }

            // 2.执行任务
            try {
                executeTask(task);
            } catch (Exception e) {
                log.error("execute task failed:\n",e);
            }
        }
    }

    /**
     * 执行任务(同步执行任务)
     *
     * @param task
     */
    private void executeTask(ThreadPoolTask task) {
        task.run();
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger logger) {
        this.log = logger;
    }

}