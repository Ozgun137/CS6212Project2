import java.util.Random;

public class Main {

    public static int quickSelect(int[] numbers, int k) {
        return quickSelect(numbers, 0, numbers.length - 1, k - 1);
    }

    private static int quickSelect(int[] numbers, int left, int right, int k) {
        //Base step
        if (left == right) {
            return numbers[left];
        }

        int pivotIndex = medianOfMedians(numbers, left, right);
        pivotIndex = partition(numbers, left, right, pivotIndex);

        if (k == pivotIndex) {
            return numbers[k];
        } else if (k < pivotIndex) {
            return quickSelect(numbers, left, pivotIndex - 1, k);
        } else {
            return quickSelect(numbers, pivotIndex + 1, right, k);
        }
    }

    private static int medianOfMedians(int[] numbers, int left, int right) {
        int size = right - left + 1;
        //If we have less than 5 elements, we can just sort and return the median
        if (size <= 5) {
            sortArray(numbers, left, right);
            return left + size / 2;
        }

        int numberOfGroups = (int) Math.ceil(size / 5.0);
        int[] medianArray = new int[numberOfGroups];

        for (int i = 0; i < numberOfGroups; i++) {
            int groupLeftStartIndex = left + i * 5;
            int groupRightStartIndex = Math.min(left + i * 5 + 4, right);
            medianArray[i] = medianOfMedians(numbers, groupLeftStartIndex, groupRightStartIndex);
        }

        return quickSelect(medianArray, 0, numberOfGroups - 1, numberOfGroups / 2);
    }

    //Insertion sort
    private static void sortArray(int[] numbers, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int current = numbers[i];
            int j = i - 1;
            while (j >= left && numbers[j] > current) {
                numbers[j + 1] = numbers[j];
                j--;
            }
            numbers[j + 1] = current;
        }
    }

    private static int partition(int[] numbers, int left, int right, int pivotIndex) {
        int pivotValue = numbers[pivotIndex];
        int currentIndex = left;

        swapNumbers(numbers, pivotIndex, right);

        for (int i = left; i < right; i++) {
            if (numbers[i] < pivotValue) {
                swapNumbers(numbers, i, currentIndex);
                currentIndex++;
            }
        }

        swapNumbers(numbers, currentIndex, right);
        return currentIndex;
    }

    private static void swapNumbers(int[] numbers, int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    public static void main(String[] args) {
        //Creating an array with random integer values
        int randomValue = 100000000;
        int[] arr = new int[randomValue];
        Random rand = new Random();
        for (int i = 0; i < randomValue; i++) {
            arr[i] = rand.nextInt(randomValue);
        }

        int k = 30000000;
        Long start = System.nanoTime();
        int result = quickSelect(arr, k);
        Long end = System.nanoTime();
        System.out.println("The " + k + "th smallest element is: " + result);
        System.out.println("Elapsed time : " + (end-start));
    }
}