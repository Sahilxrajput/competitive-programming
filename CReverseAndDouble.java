import java.io.*;

public class CReverseAndDouble {

    static final long MOD = 1_000_000_007L;
    static final long INF = Long.MAX_VALUE / 4;

    // ==================== FAST SCANNER ====================

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;
        private int len = 0;

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0)
                    return -1;
            }
            return buffer[ptr++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;

            do {
                c = read();
            } while (c <= ' ');

            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }

            return sb.toString();
        }

        int nextInt() throws IOException {
            int c;

            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;

            if (c == '-') {
                sign = -1;
                c = read();
            }

            int res = 0;

            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = read();
            }

            return res * sign;
        }

        long nextLong() throws IOException {
            int c;

            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;

            if (c == '-') {
                sign = -1;
                c = read();
            }

            long res = 0;

            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = read();
            }

            return sign == 1 ? res : -res;
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        char nextChar() throws IOException {
            return next().charAt(0);
        }
    }

    // ==================== MATH ====================

    static long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    static long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }

    static long pow(long a, long b) {
        long result = 1;

        while (b > 0) {
            if ((b & 1) == 1) {
                result *= a;
            }

            a *= a;
            b >>= 1;
        }

        return result;
    }

    static long modPow(long a, long b) {
        long result = 1;
        a %= MOD;

        while (b > 0) {
            if ((b & 1) == 1) {
                result = result * a % MOD;
            }

            a = a * a % MOD;
            b >>= 1;
        }

        return result;
    }

    static long modInverse(long a) {
        return modPow(a, MOD - 2);
    }

    static long ceilDiv(long a, long b) {
        return (a + b - 1) / b;
    }

    static boolean isPrime(long n) {
        if (n < 2)
            return false;
        if (n % 2 == 0)
            return n == 2;

        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }

        return true;
    }

    static int min(int... a) {
        int ans = a[0];

        for (int x : a) {
            ans = Math.min(ans, x);
        }

        return ans;
    }

    static int max(int... a) {
        int ans = a[0];

        for (int x : a) {
            ans = Math.max(ans, x);
        }

        return ans;
    }

    // ==================== BINARY SEARCH ====================

    static int lowerBound(int[] arr, int target) {
        int l = 0;
        int r = arr.length;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (arr[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }

    static int upperBound(int[] arr, int target) {
        int l = 0;
        int r = arr.length;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (arr[mid] > target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }

    // ==================== ARRAY METHODS ====================

    static int[] readArray(FastScanner fs, int n) throws IOException {
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = fs.nextInt();
        }

        return arr;
    }

    static long[] readLongArray(FastScanner fs, int n) throws IOException {
        long[] arr = new long[n];

        for (int i = 0; i < n; i++) {
            arr[i] = fs.nextLong();
        }

        return arr;
    }

    static int[][] readMatrix(FastScanner fs, int n, int m) throws IOException {
        int[][] a = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = fs.nextInt();
            }
        }

        return a;
    }

    static long[] prefixSum(long[] arr) {
        long[] pref = new long[arr.length + 1];

        for (int i = 0; i < arr.length; i++) {
            pref[i + 1] = pref[i] + arr[i];
        }

        return pref;
    }

    static void reverse(int[] arr) {
        int l = 0;
        int r = arr.length - 1;

        while (l < r) {
            swap(arr, l, r);
            l++;
            r--;
        }
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void printArray(int[] arr) {
        StringBuilder sb = new StringBuilder();

        for (int x : arr) {
            sb.append(x).append(' ');
        }

        System.out.println(sb);
    }

    // ==================== SOLVE ====================

    static void solve(FastScanner sc) throws Exception {
        int n = sc.nextInt();
        int[] a_int = readArray(sc, n);
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = a_int[i];
        }

        if (n == 2) {
            long g0 = Math.abs(a[0] - a[1]);
            long g1 = Math.abs(2 * a[0] - a[1]);
            long g2 = Math.abs(a[0] - 2 * a[1]);
            long g3 = Math.abs(2 * a[0] - 2 * a[1]);
            long ans = Math.min(Math.min(g0, g1), Math.min(g2, g3));
            System.out.println(ans);
            return;
        }

        long[] gaps = new long[n - 1];
        for (int i = 0; i < n - 1; ++i) {
            gaps[i] = Math.abs(a[i] - a[i + 1]);
        }

        long[] pref = new long[n - 1];
        long[] suff = new long[n - 1];

        pref[0] = gaps[0];
        for (int i = 1; i < n - 1; ++i) {
            pref[i] = Math.max(pref[i - 1], gaps[i]);
        }

        suff[n - 2] = gaps[n - 2];
        for (int i = n - 3; i >= 0; --i) {
            suff[i] = Math.max(suff[i + 1], gaps[i]);
        }

        long min_gap = pref[n - 2];

        // 1. Length 1 operations: l = r = k
        for (int k = 0; k < n; ++k) {
            long current_max = 0;
            int l_gap = k - 1;
            int r_gap = k;

            if (l_gap > 0)
                current_max = Math.max(current_max, pref[l_gap - 1]);
            if (r_gap < n - 2)
                current_max = Math.max(current_max, suff[r_gap + 1]);

            if (k > 0) {
                current_max = Math.max(current_max, Math.abs(a[k - 1] - 2 * a[k]));
            }
            if (k < n - 1) {
                current_max = Math.max(current_max, Math.abs(2 * a[k] - a[k + 1]));
            }

            min_gap = Math.min(min_gap, current_max);
        }

        // 2. Length 2 operations: l = k, r = k + 1
        for (int k = 0; k < n - 1; ++k) {
            long current_max = 0;
            int l_gap = k - 1;
            int r_gap = k + 1;

            if (l_gap > 0)
                current_max = Math.max(current_max, pref[l_gap - 1]);
            if (r_gap < n - 2)
                current_max = Math.max(current_max, suff[r_gap + 1]);

            current_max = Math.max(current_max, 2 * Math.abs(a[k] - a[k + 1]));

            if (k > 0) {
                current_max = Math.max(current_max, Math.abs(a[k - 1] - 2 * a[k + 1]));
            }
            if (k + 2 < n) {
                current_max = Math.max(current_max, Math.abs(2 * a[k] - a[k + 2]));
            }

            min_gap = Math.min(min_gap, current_max);
        }

        System.out.println(min_gap);
    }

    // ==================== MAIN ====================

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        int t = 1;
        t = fs.nextInt();

        while (t-- > 0) {
            solve(fs);
        }
    }
}