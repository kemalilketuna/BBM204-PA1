import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class CSVReader {
    public static int[] readCSV(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine(); // skip the first line
        String cvsSplitBy = ",";
        List<Integer> data = new ArrayList<Integer>();
        while ((line = br.readLine()) != null) {
            String[] row = line.split(cvsSplitBy);
            // last column will be added
            data.add(Integer.parseInt(row[row.length - 1]));
        }
        br.close();
        // print the data
        return data.stream().mapToInt(i -> i).toArray();
    }
}

class Main {

    public static void main(String args[]) throws IOException {
        int[] inputs = CSVReader.readCSV(args[0]);
        List<Integer> inputList = Arrays.stream(inputs).boxed().collect(Collectors.toList());
        Collections.shuffle(inputList);
        
        inputs = inputList.stream().mapToInt(i -> i).toArray();
        SortTest test = new SortTest(inputs);
        test.runTests();
    }

}
