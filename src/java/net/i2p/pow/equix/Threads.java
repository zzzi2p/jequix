package net.i2p.pow.equix;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 *  Basic thread pool
 */
class Threads {
    private static final ThreadPoolExecutor _executor = new CustomThreadPoolExecutor();
    private static final AtomicLong _executorThreadCount = new AtomicLong();
    /** how long to wait before dropping an idle thread */
    private static final long HANDLER_KEEPALIVE_MS = 60*1000;

    public static void execute(Runnable r) {
        _executor.execute(r);
    }

    /**
     *  from TunnelControllerGroup
     */
    public static void shutdown() {
        _executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        _executor.shutdownNow();
    }

    /**
     *  from TunnelControllerGroup
     */
    private static class CustomThreadPoolExecutor extends ThreadPoolExecutor {
        public CustomThreadPoolExecutor() {
             super(4, Integer.MAX_VALUE, HANDLER_KEEPALIVE_MS, TimeUnit.MILLISECONDS,
                   new SynchronousQueue<Runnable>(), new CustomThreadFactory());
        }
    }

    /**
     *  from TunnelControllerGroup
     */
    private static class CustomThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            Thread rv = Executors.defaultThreadFactory().newThread(r);
            rv.setName("jequix hasher " + _executorThreadCount.incrementAndGet());
            rv.setDaemon(true);
            System.out.println("Starting thread " + rv.getName());
            return rv;
        }
    }
}
