import java.io.IOException;
import java.util.Arrays;
import java.util.function.BiFunction;

class LinearSearch {
    public static int search(int[] arr, int x) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                return i;
            }
        }
        return -1;
    }
}

class BinarySearch {
    public static int search(int[] arr, int x) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == x) {
                return mid;
            }
            if (arr[mid] < x) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}

public class SearchTest {
    private int[] n_values = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
    int arr[];

    public SearchTest(int[] arr) {
        this.arr = arr;
    }

    public double[] searchTest(BiFunction<int[], Integer, Integer> search) {
        double[] test_results = new double[n_values.length];

        double amount;
        int test_count;
        for(int i = 0; i < n_values.length; i++) {
            test_count = 0;
            amount = 0;
            if(n_values[i] > arr.length) {
                break;
            }
            int test_arr[] = Arrays.copyOf(this.arr, n_values[i]);

            while(test_count < 1000) {
                int randomItem = test_arr[(int) (Math.random() * n_values[i])];
                long startTime = System.nanoTime();
                search.apply(test_arr, randomItem);
                long endTime = System.nanoTime();
                amount += (endTime - startTime);
                test_count++;
            }
            test_results[i] = amount / 1000;
        }

        return test_results;
    }

    public void runTests() throws IOException{
        double[][] results = new double[3][n_values.length];
        results[0] = searchTest(LinearSearch::search);
        Arrays.sort(arr);
        results[1] = searchTest(LinearSearch::search);
        results[2] = searchTest(BinarySearch::search);
        System.out.println("Time Spent for Searching");
        System.out.println("Linear Search in Random Data" + Arrays.toString(results[0]));
        System.out.println("Linear Search in Sorted Data" + Arrays.toString(results[1]));
        System.out.println("Binary Search in Sorted Data" + Arrays.toString(results[2]));
        Chart.saveChart("Time Spent for Searching", new String[]{"Linear Search in Random Data", "Linear Search in Sorted Data", "Binary Search in Sorted Data"}, n_values, results);
    }
}
