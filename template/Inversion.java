class Inversion {

    public static long inversionCount(int arr[]) {
        return mergeSort(arr, 0, arr.length - 1);
    }

    private static long mergeSort(int[] arr, int low, int high) {
        if (low >= high) return 0;

        int mid = low + (high - low) / 2;

        int count = 0;

        count += mergeSort(arr, low, mid);
        count += mergeSort(arr, mid + 1, high);

        count += merge(arr, low, mid, high);

        return count;
    }

    private static long merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];

        int i = low;
        int j = mid + 1;
        int k = 0;

        long count = 0L;

        while (i <= mid && j <= high) {

            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } 
            else {
                temp[k++] = arr[j++];

                // All remaining elements in left half
                // form inversions with arr[j - 1].
                count += (mid - i + 1);
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= high) {
            temp[k++] = arr[j++];
        }

        for (int x = 0; x < temp.length; x++) {
            arr[low + x] = temp[x];
        }

        return count;
    }

    public static void main(String[] args) {
        int[] arr = {5, 3, 2, 4, 1};
        System.out.println(inversionCount(arr));
    }
}
