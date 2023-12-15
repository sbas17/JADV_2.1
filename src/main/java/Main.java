import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new TreeMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                String result = generateRoute("RLRFR", 100);
                int charCount = 0;
                char target = 'R';

                for (int j = 0; j < result.length(); j++) {
                    if (result.charAt(j) == target) {
                        charCount++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (!sizeToFreq.containsKey(charCount)) {
                        sizeToFreq.put(charCount, 0);
                    }
                    for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
                        if (entry.getKey() == charCount) {
                            entry.setValue(entry.getValue() + 1);
                        }
                    }
                }
            }).start();
        }
        int maxCount = 0;
        int maxCountRepeat = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxCountRepeat) {
                maxCountRepeat = entry.getValue();
                maxCount = entry.getKey();

            }
        }

        System.out.println("Самое частое количество повторений " + maxCount + " (встретилось " + maxCountRepeat + " раз)");
        System.out.println("Другие размеры: ");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() != maxCount) {
                int key = entry.getKey();
                int value = entry.getValue();
                System.out.println(key + " (" + value + " раз)");
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
