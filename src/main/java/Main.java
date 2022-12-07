import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static BlockingQueue<String> queue1 = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queue2 = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queue3 = new ArrayBlockingQueue<>(100);
    public static final int ITERATIONS = 10_000;
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                try {
                    queue1.put(generateText("abc", 100_000));
                    queue2.put(generateText("abc", 100_000));
                    queue3.put(generateText("abc", 100_000));
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        thread1.start();

        Thread thread2 = new Thread(() -> {
            int size = 0;
            for (int n = 0; n < ITERATIONS; n++) {
                String i;
                try {
                    i = queue1.take();
                } catch (InterruptedException e) {
                    return;
                }
                char[] c = i.toCharArray();
                int counting = 0;
                for (char j : c) {
                    if (j == 'c') {
                        counting += 1;
                    }
                }
                if (counting > size) {
                    size = counting;
                }
            }
            System.out.println("Среди строк самое большое количество c " + size);
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            int size = 0;
            for (int n = 0; n < ITERATIONS; n++) {
                String i;
                try {
                    i = queue2.take();
                } catch (InterruptedException e) {
                    return;
                }
                char[] c = i.toCharArray();
                int counting = 0;
                for (char j : c) {
                    if (j == 'b') {
                        counting += 1;
                    }
                }
                if (counting > size) {
                    size = counting;
                }
            }
            System.out.println("Среди строк самое большое количество b " + size);
        });
        thread3.start();

        Thread thread4 = new Thread(() -> {
            int size = 0;
            for (int n = 0; n < ITERATIONS; n++) {
                String i;
                try {
                    i = queue3.take();
                } catch (InterruptedException e) {
                    return;
                }
                char[] c = i.toCharArray();
                int counting = 0;
                for (char j : c) {
                    if (j == 'a') {
                        counting += 1;
                    }
                }
                if (counting > size) {
                    size = counting;
                }
            }
            System.out.println("Среди строк самое большое количество a " + size);
        });
        thread4.start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}