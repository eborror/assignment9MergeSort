import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MergeSort {
    public static void main(String[] args) throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            int arrayLength, userInput;
            String filename;
            int[] arrayGiven;

            System.out.println("Enter one of the following");
            System.out.println("1 : generate a file of random integers");
            System.out.println("2 : read and sort an existing file");
            userInput = sc.nextInt();
            if (userInput == 1) {
                System.out.println("What should the file be named?");
                filename = sc.next() + ".txt";
                System.out.println("How long do you want your array?");
                arrayLength = sc.nextInt();
                int[] array = createRandomArray(arrayLength);
                writeArrayToFile(array, filename);
            }
            else if (userInput == 2) {
                System.out.println("What is the name of your file?");
                filename = sc.next() + ".txt";
                arrayGiven = readFileToArray(filename);
                sort(arrayGiven, 0, arrayGiven.length-1);
                printArray(arrayGiven);
            }
        }
    }

    public static int[] createRandomArray(int arrayLength) {
        Random random = new Random();
        int array1[] = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            array1[i] = random.nextInt(101);
        }
        return array1;
    }

    public static void writeArrayToFile(int[] array, String filename) {
        try {
            File randomArray = new File(filename);
            if (randomArray.createNewFile()) {
                System.out.println("File created: " + filename);
            }
            try (FileWriter writer = new FileWriter(filename)) {
                for (int i = 0; i < array.length; i++) {
                    writer.write(array[i] + "");
                    if (i+1 < array.length) {
                        writer.write("\n");
                    }
                }
                writer.close();
            }
            System.out.println("Wrote to file");
        } catch (IOException e) {
            System.err.println("Error writing to the file");
        }
    }

    public static int[] readFileToArray(String filename) {
        File givenFile = new File(filename);
        int[] givenArray = {0};
        try (Scanner fileReader = new Scanner(givenFile)) {
            int fileLength = 0;
            while (fileReader.hasNextLine()) {
                fileReader.nextLine();
                fileLength++;
            }
            System.out.println("File has " + fileLength + " Lines");
            givenArray = new int[fileLength];
        } catch (Exception e) {
            System.err.println("Error reading the file");
        }
        try (Scanner fileReader = new Scanner(givenFile)) {
            int i = 0;
            while (fileReader.hasNextLine()) {
                givenArray[i] = fileReader.nextInt();
                i++;
            }
            fileReader.close();
        } catch (Exception e) {
            System.err.println("Error reading the file");
        }
        return givenArray;
    }

    public static void sort(int[] array, int count, int length) {
        if (count < length) {
            int mid = count + (length - count) / 2;
            sort(array, count, mid);
            sort(array, mid + 1, length);
            merge(array, count, mid, length);
        }
    }

    public static void merge(int array[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];
        for (int i = 0; i < n1; ++i) {
            L[i] = array[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = array[m + 1 + j];
        }
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            }
            else {
                array[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }

    public static void printArray(int array[])
    {
        int n = array.length;
        for (int i = 0; i < n; ++i)
            System.out.print(array[i] + " ");
        System.out.println();
    }
}
