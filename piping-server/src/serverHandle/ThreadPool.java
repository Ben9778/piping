package serverHandle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static final int MAX_THREADS = 10;

    public static void ThreadPoolExecute(Thread thread) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        executorService.execute(thread);
    }
}
