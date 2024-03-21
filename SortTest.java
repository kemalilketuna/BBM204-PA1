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
        int k = Arrays.stream(arr).max().getAsInt();
        int[] count = new int[k+1];
        Arrays.fill(count, 0);
        int size = arr.length;
        int[] output = new int[size];

        for(int i=0,j; i<size; i++){
            j = arr[i];
            count[j] += 1;
            }

        for(int i=1; i<k+1; i++){
            count[i] += count[i-1];
            }

        for(int i=size-1,j; i>=0; i--){
            j = arr[i];
            count[j] --;
            output[count[j]] = arr[i];
        }
        return output;
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

        double amount;
        int test_count;
        for (int i = 0; i < n_values.length; i++) {
            test_count = 0;
            amount = 0;
            if(n_values[i] > arr.length){
                break;
            }

            while(test_count < 10){
                int[] test_arr = Arrays.copyOfRange(arr, 0, n_values[i]);
                long startTime = System.nanoTime();
                algorithm.apply(test_arr);
                long endTime = System.nanoTime();
                double duration = (endTime - startTime) / 1000000.0;
                amount += duration;
                test_count++;
            }
            test_results[i] = amount / 10;
        }
        return test_results;
    }
    
    public void runTests() throws IOException {
        double[][] results = new double[3][n_values.length];
        results[0] = testAlgortihm(MergeSort::sort);
        results[1] = testAlgortihm(InsertionSort::sort);
        results[2] = testAlgortihm(CountingSort::sort);
        System.out.println("Time Spent in Sorting Process with Random Data");
        System.out.println("Merge Sort: " + Arrays.toString(results[0]));
        System.out.println("Insertion Sort: " + Arrays.toString(results[1]));
        System.out.println("Counting Sort: " + Arrays.toString(results[2]));
        System.out.println();
        Chart.saveChart("Time Spent in Sorting Process with Random Data", new String[]{"Merge Sort", "Insertion Sort", "Counting Sort"}, n_values, results);

        arr = Arrays.stream(arr).sorted().toArray();
        results[0] = testAlgortihm(MergeSort::sort);
        results[1] = testAlgortihm(InsertionSort::sort);
        results[2] = testAlgortihm(CountingSort::sort);
        System.out.println("Time Spent in Sorting Process with Sorted Data");
        System.out.println("Merge Sort: " + Arrays.toString(results[0]));
        System.out.println("Insertion Sort: " + Arrays.toString(results[1]));
        System.out.println("Counting Sort: " + Arrays.toString(results[2]));
        System.out.println();
        Chart.saveChart("Time Spent in Sorting Process with Sorted Data", new String[]{"Merge Sort", "Insertion Sort", "Counting Sort"}, n_values, results);
        
        arr = Arrays.stream(arr).boxed().sorted((a, b) -> b - a).mapToInt(i -> i).toArray();
        results[0] = testAlgortihm(MergeSort::sort);
        results[1] = testAlgortihm(InsertionSort::sort);
        results[2] = testAlgortihm(CountingSort::sort);
        System.out.println("Time Spent in Sorting Process with Reversed Sorted Data");
        System.out.println("Merge Sort: " + Arrays.toString(results[0]));
        System.out.println("Insertion Sort: " + Arrays.toString(results[1]));
        System.out.println("Counting Sort: " + Arrays.toString(results[2]));
        System.out.println();
        Chart.saveChart("Time Spent in Sorting Process with Reversed Sorted Data", new String[]{"Merge Sort", "Insertion Sort", "Counting Sort"}, n_values, results);
    }
}
