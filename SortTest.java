import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

class MergeSort{
    public static int[] sort(int[] arr) {
        if (arr.length > 1) {
            int mid = arr.length / 2;
            int[] left = new int[mid];
            int[] right = new int[arr.length - mid];
            for (int i = 0; i < mid; i++) {
                left[i] = arr[i];
            }
            for (int i = mid; i < arr.length; i++) {
                right[i - mid] = arr[i];
            }
            sort(left);
            sort(right);
            merge(arr, left, right);
        }
        return arr;
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }
        while (i < left.length) {
            arr[k] = left[i];
            i++;
            k++;
        }
        while (j < right.length) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }
}

class InsertionSort{
    public static int[] sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        return arr;
    }
}

class CountingSort{
    public static int[] sort(int[] arr) {
        int n = arr.length;
        int[] output = new int[n];
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        int[] count = new int[max + 1];
        for (int i = 0; i < max; ++i) {
            count[i] = 0;
        }
        for (int i = 0; i < n; ++i) {
            count[arr[i]]++;
        }
        for (int i = 1; i <= max; ++i) {
            count[i] += count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
        return arr;

        // // only sort arr
        // Arrays.sort(arr);
        // return arr;
    }
}

public class SortTest {
    private int[] n_values = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
    int[] arr;

    public SortTest(int[] arr) {
        this.arr = arr;
    }

    public double[] testAlgortihm(Function<int[], int[]> algorithm) {
        double[] test_results = new double[n_values.length];

        int duration = 0;
        int test_count;
        for (int i = 0; i < n_values.length; i++) {
            test_count = 0;
            if(n_values[i] > arr.length){
                break;
            }

            int[] test_arr = new int[n_values[i]];
            for (int j = 0; j < n_values[i]; j++) {
                test_arr[j] = arr[j];
            }

            while(test_count < 10){
                long startTime = System.nanoTime();
                algorithm.apply(test_arr);
                long endTime = System.nanoTime();
                duration += (endTime - startTime) / 1000000;
                test_count++;
            }
            test_results[i] = duration / 10;
        }
        return test_results;
    }
    
    public void runTests() throws IOException {
        double[][] results = new double[3][n_values.length];
        results[0] = testAlgortihm(MergeSort::sort);
        results[1] = testAlgortihm(InsertionSort::sort);
        results[2] = testAlgortihm(CountingSort::sort);
        Chart.saveChart("Time Spent in Sorting Process", new String[]{"Merge Sort", "Insertion Sort", "Counting Sort"}, n_values, results);
    }
}
