package buguw.com.common.threadpool;

import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * 工作队列。待处理任务在此排队。
 * 任务生产者： 
 * (1)Server ：生产任务 
 * (2) ServerTask ：任务 
 * (3) WorkQueue : 任务仓库
 * 任务消费者： 
 * (1) ProcessorPool : 处理线程池
 * @author 刘恭亮
 * @since 2012-3-22
 * @version 1.1.0
 */
public class WorkQueue {

    /**
     * 默认的最大任务数。
     */
    private static final int DEFAULT_MAX_TASK_NUM = 20;

    /**
     * 最大任务数。仅对会话任务限制。
     */
    private static int maxTaskNum = DEFAULT_MAX_TASK_NUM;

    /* add by 修改人：尹晓涛 修改时间：20110228 修改原因：记录启动服务后，执行的任务总和 start */
    /**
     * 当前处理任务数，从服务启动算起
     */
    protected static long TASK_NUM_TOTAL = 0;
    /* add by 修改人：尹晓涛 修改时间：20110228 修改原因：记录启动服务后，执行的任务总和 end */

    /**
     * 队列
     */
    public static final LinkedList<ThreadPoolTask> queue = new LinkedList<ThreadPoolTask>();

    /**
     * 调试器
     */
    private static Logger log = null;

    /**
     * 开始 void
     */
    public static void start() {

    }

    /**
     * 任务入队
     * 
     * @param task ThreadPoolTask
     * @return false入队失败
     */
    public static boolean putTask(ThreadPoolTask task) {

        synchronized (queue) {
            // 当排队任务超过最大任务数时，禁止会话任务加入
            if (queue.size() > maxTaskNum) {
                return false;
            }

            // 加入任务
            queue.add(task);
            log = Logger.getRootLogger();
            log.info("ServiceTask into the queue");

            // 唤醒一个等待的处理线程
//            queue.notify();
            queue.notifyAll();

        }

        return true;
    }

    /**
     * 取得一个任务。当队列为空时wait。
     * 
     * @return ThreadPoolTask
     */
    public static ThreadPoolTask getTask() {
        ThreadPoolTask task = null;
        synchronized (queue) {
            while (queue.size() == 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            if (queue.size() > 0) {
                task = queue.remove();
                /* add by 修改人：尹晓涛 修改时间：20110228 修改原因：记录启动服务后，执行的任务总和 start */
                TASK_NUM_TOTAL++;
                log = Logger.getRootLogger();
                log.info("ServiceTask out of the queue");
                /* add by 修改人：尹晓涛 修改时间：20110228 修改原因：记录启动服务后，执行的任务总和 end */
            } else {
                task = null;
            }
        }
        return task;
    }

    public static long getTaskNumToal() {
        return TASK_NUM_TOTAL;
    }

    public static int getMaxTaskNum() {
        return maxTaskNum;
    }

    /**
     * 最大ThreadPoolTask数
     * @param maxTaskNum void
     */
    public static void setMaxTaskNum(int maxTaskNum) {
        if (maxTaskNum > 0) {
            WorkQueue.maxTaskNum = maxTaskNum;
        }
    }

    public static int getCurrentTaskNum() {
        return queue.size();
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        WorkQueue.log = log;
    }

}
