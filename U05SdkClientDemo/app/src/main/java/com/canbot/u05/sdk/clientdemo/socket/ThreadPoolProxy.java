package com.canbot.u05.sdk.clientdemo.socket;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProxy {

        ThreadPoolExecutor mExecutor;

        private int mCorePoolSize;

        private int mMaximumPoolSize;

        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
                mCorePoolSize = corePoolSize;
                mMaximumPoolSize = maximumPoolSize;
        }

        private void initThreadPoolExecutor() {
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                        synchronized (ThreadPoolProxy.class) {
                                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                                        long keepAliveTime = 0;
                                        TimeUnit unit = TimeUnit.MILLISECONDS;
                                        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
                                        ThreadFactory threadFactory = Executors.defaultThreadFactory();
//                                        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                                        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
                                        mExecutor = new ThreadPoolExecutor(
                                                mCorePoolSize,
                                                mMaximumPoolSize,
                                                keepAliveTime,
                                                unit,
                                                workQueue,
                                                threadFactory,
                                                handler
                                        );
                                }
                        }

                }
        }

        public Future<?> submit(Runnable task) {
                initThreadPoolExecutor();
                Future<?> submit = mExecutor.submit(task);
                return submit;
        }


        public void execute(Runnable task) {
                initThreadPoolExecutor();
                mExecutor.execute(task);
        }

        public void remove(Runnable task) {
                initThreadPoolExecutor();
                mExecutor.remove(task);
        }

        public void shutDown() {
                if (mExecutor != null) {
                        mExecutor.shutdown();
                }
        }

}
