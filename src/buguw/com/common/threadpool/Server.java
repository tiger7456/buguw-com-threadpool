/**************************************************
 ***            中国民生银行EMU系统             ***
 ***
 *** 开 发 商：北京长信通信息技术有限公司
 *** 创 建 人：刘恭亮
 *** 创建日期：2008-7-28 19:50:01
 *** 版 本 号：V0.1
 *** 维护记录：
 ***    刘恭亮  2008-7-28 19:50:01 新建
 ***********************************************/
package buguw.com.common.threadpool;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.giantstone.common.config.ConfigManager;

/**
 * 服务器程序
 *@author 方宗虎
 *@since 2012-3-22
 *@version 1.1.0
 */
public class Server {
    /**
     * MSG_TYP_EUSP
     */
    public static final int MSG_TYP_EUSP = 0;
    /**
     * MSG_TYP_CommXML
     */
    public static final int MSG_TYP_CommXML = 1;

    /**
     * 服务ID
     */
    private String no = null;

    /**
     * 最大任务数
     */
    private static int maxTaskNum = 20;

    /**
     * 最大线程数
     */
    private int maxProcessorNum = 10;


    /**
     * 调试信息输出器
     */
    private static Logger log = null;

    /**
     * 是否重起服务器
     */
   /* private static boolean isRestart = false;*/

    /**
     * 构造
     */
    public Server() {
        init();
    }
    
    /**
     * 构造
     * @param no 服务ID
     */
    public Server(String no) {
        this.no = no;

        init();
    }

    /**
     * 停止
     *  void
     */
    public static void stop() {

        ProcessorPool.stop();
    }

    /**
     * 加载配置文件
     */
    private void init() {

        /*Properties systemP = ConfigManager.loadProperties("system.properties");
        log.debug(systemP);*/

        /*
         * modify by 修改人：李宏乐 修改时间：20101118
         * 修改原因：由于IP不同，需要提供可配置的集群编号,提供给TransDataObject对象，用于创建流水号 start
         */
        //no = systemP.getProperty("server_number", "99");
        /*if (2 != no.length()) {
            throw new IllegalArgumentException("集群编号server_number=" + no + ",应为2位");
        }*/
        log = Logger.getRootLogger();
        if (log.isInfoEnabled()){
            log.info("Start Server [" + no + "] ......");
        }
        // 2. 加载基本参数
        String configFileName = "server_" + "01" + ".properties";
        if (log.isInfoEnabled()){
            log.info("(1) load configFileName [" + configFileName + "]");
        }
        Properties p = ConfigManager.loadProperties(configFileName);
        if (log.isInfoEnabled()){
            log.info(p);
        }
        maxTaskNum = Integer.parseInt(p.getProperty("maxTaskNum", "20"));

        maxProcessorNum = Integer.parseInt(p.getProperty("maxProcessorNum", "10"));

    }

    /**
     * 启动线程池
     * 
     */
    public void startThreadPool() {

        // 队列
        WorkQueue.setLog(log);
        WorkQueue.setMaxTaskNum(maxTaskNum);

        // 启动程线池
        ProcessorPool.setLog(log);
        ProcessorPool.setMaxProcessorNum(maxProcessorNum);
        ProcessorPool.start();
    }
}
