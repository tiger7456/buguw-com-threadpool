package buguw.com.common.threadpool;

import org.apache.log4j.Logger;

/**
 *处理器线程池
 *
 *@author 刘恭亮
 *@since 2012-3-22
 *@version 1.1.0
 */
public class ProcessorPool {

    /**
     * 处理器数组
     */
    private static Processor[] processors;

    /**
     * 处理器线程最大数
     */
    private static int maxProcessorNum = 10;

    /**
     * 运行标志
     */
    private static boolean run = false;

    /**
     * 调试器
     */
    private static Logger log ;

    /**
     * 启动
     */
    public static void start() {
        run = true;
        processors = new Processor[maxProcessorNum];
        for (int i = 0; i < maxProcessorNum; i++) {
            processors[i] = new Processor();
            processors[i].setName("EMU-Work-"+i);
            processors[i].setLog(log);
            processors[i].setDaemon(true);
            processors[i].start();
        }
    }

    /**
     * 停止
     */
    public static void stop() {
        run = false;
        // 停止所有线程
        log.fatal("stop ProcessorPool");
        for (int i = 0; i < maxProcessorNum; i++) {
            getLog().info("stop processor[" + i + "]");
            try {
                processors[i].interrupt();
                processors[i].join(200);
            } catch (InterruptedException e) {
                getLog().warn("stop processor[" + i + "] failed", e);
            }
        }

    }
    
    /**
     * add by 修改人：尹晓涛 
     * 修改时间：20110228 
     * 修改原因：监控当前有多少个线程正在running start 
     * @return int
     */
    public static synchronized int getRunningProcessorsNum(){
    	int num = 0;
    	for(int i = 0;i < processors.length;i++){
     		if(0 == Thread.State.RUNNABLE.compareTo(processors[i].getState())){
     		 	num++;
     		}
    	}
    	return num;
    }
    /**
     * add by 修改人：尹晓涛
     *  修改时间：20110228 
     *  修改原因：监控当前有多少个线程正在running end 
     *  @return int  
     */
    public static int getMaxProcessorNum() {
        return maxProcessorNum;
    }

    public static void setMaxProcessorNum(int newMaxProcessorNum) {
        maxProcessorNum = newMaxProcessorNum;
    }

    public static boolean isRun() {
        return run;
    }

    public static void setRun(boolean newRun) {
        run = newRun;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger newLogger) {
        log = newLogger;
    }

}