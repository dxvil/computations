package dev.computations;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class ComputationsOnFiles {
    volatile AtomicLong totalCount = new AtomicLong();

    static final ReentrantLock reentrantLock = new ReentrantLock();

    public long getTotalCount() {
        return totalCount.get();
    }

    public void setTotalCount(long totalCount) {
        this.totalCount.getAndSet(this.totalCount.addAndGet(totalCount));
    }

    public void readFileByChunks(File fileToRead, int threadNeeded) throws Exception {
        if(threadNeeded <= 0) {
            throw new RuntimeException("Please, clarify the amount of thread needed.");
        }

        if(fileToRead == null || !fileToRead.exists()) {
            throw new RuntimeException("The file is broken or not exist. Please, try another file.");
        }

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNeeded);

        try {
            int chunkSize = (int) (fileToRead.length() / threadNeeded);
            RandomAccessFile file = new RandomAccessFile(fileToRead, "r");

            for (int i = 0; i <= threadNeeded; i++) {
                final byte[] part = new byte[chunkSize];
                file.seek((long) i * chunkSize);
                file.read(part, 0, chunkSize);
                executor.execute(count(part));
            }

            // If the file size is not divisible by the number of threads, read the remaining part
            if (fileToRead.length() % threadNeeded != 0) {
                int remainingPartSize = (int) (fileToRead.length() - (chunkSize * threadNeeded));
                byte[] remainingPart = new byte[remainingPartSize];
                file.seek((long) threadNeeded * chunkSize);
                file.read(remainingPart, 0, remainingPartSize);
                executor.execute(count(remainingPart));
            }

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            executor.shutdown();
        }
    }

    public Runnable count(final byte[] part) {
        return new Runnable() {
            public void run() {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(part);
                     InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
                     BufferedReader reader = new BufferedReader(isr)) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line
                                .replace("\n", "")
                                .replace("\r", "")
                                .trim();

                        Arrays.stream(line.split(",")).parallel().forEach(num -> {
                            if (!num.isBlank()) {
                                reentrantLock.lock();
                                setTotalCount(Integer.parseInt(num.trim()));
                                System.out.println(getTotalCount());
                                reentrantLock.unlock();
                            }
                        });
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
