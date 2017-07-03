package movie.example.ls.vvmoviemanager.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池队列
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/6/4
 * Email : lsw
 */
public class ThreadPoolInstanceUtils {

    private static BlockingQueue<Runnable> workQueue=new ArrayBlockingQueue<>(10);
    private static ThreadPoolExecutor pool=new ThreadPoolExecutor(3,7,1, TimeUnit.MICROSECONDS, workQueue);

    private ThreadPoolInstanceUtils(){

    }
    /**
     * 推送任务
     * @param runnable
     */
    public static void push(Runnable runnable){
        pool.execute(runnable);
    }

}
