package pw.mihou.amaririsu.features.threading;

import java.util.concurrent.*;

public class AmaririsuThreadPool {

    private static final int CORE_POOL_SIZE = 1;
    public static final ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(CORE_POOL_SIZE, new ThreadFactory("Amaririsu - Scheduler - %d", false));
    private static final int MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;
    private static final int KEEP_ALIVE_TIME = 120;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    public static final ExecutorService executorService = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, new SynchronousQueue<>(),
            new ThreadFactory("Amaririsu - Executor - %d", false));

    public static ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit measurement) {
        return scheduledExecutorService.schedule(task, delay, measurement);
    }

}
