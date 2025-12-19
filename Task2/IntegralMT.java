import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.*;

public class IntegralMT {

    @FunctionalInterface
    public interface DoubleFunction {
        double apply(double x);
    }

    public static double integrateNonNegativeHitOrMiss(
            DoubleFunction f,
            double a,
            double b,
            double yMax,
            long samples,
            int threads,
            long seed
    ) throws InterruptedException, ExecutionException {

        if (b <= a) throw new IllegalArgumentException("b must be > a");
        if (yMax <= 0) throw new IllegalArgumentException("yMax must be > 0");
        if (samples <= 0) throw new IllegalArgumentException("samples must be > 0");
        if (threads <= 0) throw new IllegalArgumentException("threads must be > 0");

        final double width = b - a;
        final double rectArea = width * yMax;

        ExecutorService pool = Executors.newFixedThreadPool(threads);

        long basePerThread = samples / threads;
        long remainder = samples % threads;

        List<Future<long[]>> futures = new ArrayList<>(threads);

        for (int t = 0; t < threads; t++) {
            final long n = basePerThread + (t < remainder ? 1 : 0);
            final long threadSeed = mixSeed(seed, t);

            futures.add(pool.submit(() -> {
                SplittableRandom rnd = new SplittableRandom(threadSeed);

                long hits = 0;
                for (long i = 0; i < n; i++) {
                    double x = a + rnd.nextDouble() * width;
                    double y = rnd.nextDouble() * yMax;

                    if (y <= f.apply(x)) {
                        hits++;
                    }
                }
                return new long[]{hits, n};
            }));
        }

        long totalHits = 0;
        long total = 0;

        for (Future<long[]> ftr : futures) {
            long[] res = ftr.get();
            totalHits += res[0];
            total += res[1];
        }

        pool.shutdown();

        double pHit = totalHits / (double) total;
        return pHit * rectArea;
    }

    private static long mixSeed(long seed, int threadIndex) {
        long z = seed + 0x9E3779B97F4A7C15L * (threadIndex + 1L);
        z ^= (z >>> 30);
        z *= 0xBF58476D1CE4E5B9L;
        z ^= (z >>> 27);
        z *= 0x94D049BB133111EBL;
        z ^= (z >>> 31);
        return z;
    }

    public static void main(String[] args) throws Exception {
        DoubleFunction f = Math::sin;

        double a = 0.0;
        double b = Math.PI;

        double yMax = 1.0;

        long samples = 20_000_000L;
        int threads = Runtime.getRuntime().availableProcessors();
        long seed = 123456789L;

        double estimate = integrateNonNegativeHitOrMiss(f, a, b, yMax, samples, threads, seed);

        System.out.println("Estimate: " + estimate);

    }
}
