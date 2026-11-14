import java.io.*;

public class MexGameHard {

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

    // First index where arr[index] >= target
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

    // First index where arr[index] > target
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

    static void solve(FastScanner fs) throws Exception {
        // start writing you code
        int n = fs.nextInt();

        int[] a = new int[n + 1];
        int maxA = 0;

        for (int i = 1; i <= n; i++) {
            a[i] = fs.nextInt();
            maxA = Math.max(maxA, a[i]);
        }

        int maxMex = maxA + 1;

        int[][] minLast = new int[maxMex + 1][n + 1];

        int[] last = new int[maxMex + 1];

        for (int r = 1; r <= n; r++) {

            int x = a[r];

            if (x <= maxA) {
                last[x] = r;
            }

            int mn = n + 1;

            for (int m = 1; m <= maxMex; m++) {
                mn = Math.min(mn, last[m - 1]);
                minLast[m][r] = mn;
            }
        }

        long answer = 0;

        for (int m = 0; m <= maxMex; m++) {

            // Prefix parity.
            int[] pref = new int[n + 1];

            for (int i = 1; i <= n; i++) {

                int x = a[i];

                int w;

                if (x < m) {
                    w = x;
                } else if (x == m) {
                    w = 0;
                } else {
                    w = x - (m + 1);
                }

                pref[i] = pref[i - 1] ^ (w & 1);
            }

            int[] cnt0 = new int[n + 1];
            int[] cnt1 = new int[n + 1];

            cnt0[0] = 1;

            for (int i = 1; i <= n; i++) {
                cnt0[i] = cnt0[i - 1];
                cnt1[i] = cnt1[i - 1];

                if (pref[i] == 0) {
                    cnt0[i]++;
                } else {
                    cnt1[i]++;
                }
            }

            int[] lastPos = new int[maxMex + 1];

            int baseParity = (m * (m - 1) / 2) & 1;
            int requiredSumParity = baseParity ^ 1;

            for (int r = 1; r <= n; r++) {

                int x = a[r];

                if (x <= maxMex) {
                    lastPos[x] = r;
                }

                int left = lastPos[m] + 1;

                int right;

                if (m == 0) {
                    right = r;
                } else {
                    right = minLast[m][r];
                }

                if (left > right) {
                    continue;
                }

                int qLeft = left - 1;
                int qRight = right - 1;

                int neededParity = pref[r] ^ requiredSumParity;

                if (neededParity == 0) {
                    answer += cnt0[qRight]
                            - (qLeft > 0 ? cnt0[qLeft - 1] : 0);
                } else {
                    answer += cnt1[qRight]
                            - (qLeft > 0 ? cnt1[qLeft - 1] : 0);
                }
            }
        }
        System.out.println(answer);
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

// problem name - MEX Game (Hard)
// problem link - https://www.codechef.com/START258C/problems/MEXGAME2
// Date - 2026-09-30
// Time - 21:28:30




