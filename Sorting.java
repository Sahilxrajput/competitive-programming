public class Sorting {

    static void mergeSort(int[] arr, int left, int right) {
        int mid = left + (right - left) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid+1, right);

        merge(arr, left, mid, right);
    }

    public static void main(String[] args) {

    }
}