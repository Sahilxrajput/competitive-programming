public class Main {

    public static void solve(Scanner sc) {

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        // Case 0: already all K
        boolean allK = true;

        for (int x : a) {
            if (x != k) {
                allK = false;
                break;
            }
        }

        if (allK) {
            System.out.println(0);
            return;
        }

        // Find first and last element != K
        int left = 0;

        while (a[left] == k) {
            left++;
        }

        int right = n - 1;

        while (a[right] == k) {
            right--;
        }

        // Case 1: XOR
        // All elements in [left, right] must be equal
        boolean same = true;

        for (int i = left + 1; i <= right; i++) {
            if (a[i] != a[left]) {
                same = false;
                break;
            }
        }

        if (same) {
            System.out.println(1);
            return;
        }

        // Case 1: GCD
        // Every element must be divisible by K
        boolean divisible = true;

        for (int x : a) {
            if (x % k != 0) {
                divisible = false;
                break;
            }
        }

        if (divisible) {
            System.out.println(1);
            return;
        }

        // Otherwise
        System.out.println(2);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        while (t-- > 0) {
            solve(sc);
        }

        sc.close();
    }
}