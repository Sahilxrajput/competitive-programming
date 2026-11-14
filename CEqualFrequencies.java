import java.io.*;
import java.util.*;

public class CEqualFrequencies {

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
        int n = fs.nextInt();
        String s = fs.next();

        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Sort character indices by descending frequency
        Integer[] idx = new Integer[26];
        for (int i = 0; i < 26; i++) {
            idx[i] = i;
        }

        Arrays.sort(
                idx,
                (a, b) -> (freq[b] != freq[a]) ? (freq[b] - freq[a]) : (b - a));

        int bestSave = 0;
        int bestK = 1;

        // Find the optimal number of distinct characters
        for (int k = 1; k <= 26; k++) {
            if (n % k != 0)
                continue;

            int target = n / k;
            int saved = 0;

            for (int i = 0; i < k; i++) {
                saved += Math.min(freq[idx[i]], target);
            }

            if (saved > bestSave) {
                bestSave = saved;
                bestK = k;
            }
        }

        int target = n / bestK;
        char[] result = new char[n];
        Arrays.fill(result, '#');

        int[] remaining = freq.clone();
        List<Character> extra = new ArrayList<>();

        // Preserve characters that can stay unchanged
        for (int i = 0; i < bestK; i++) {
            int ch = idx[i];
            int keep = Math.min(freq[ch], target);

            remaining[ch] = keep;

            for (int j = 0; j < keep; j++) {
                extra.add((char) ('a' + ch));
            }

            for (int j = keep; j < target; j++) {
                extra.add((char) ('a' + ch));
            }
        }

        // Track how many copies of each selected character
        // still need to be placed.
        int[] need = new int[26];

        for (int i = 0; i < bestK; i++) {
            int ch = idx[i];
            need[ch] = target;
        }

        // Keep existing characters up to the target frequency
        for (int i = 0; i < n; i++) {
            int ch = s.charAt(i) - 'a';

            if (need[ch] > 0 && remaining[ch] > 0) {
                result[i] = s.charAt(i);
                remaining[ch]--;
                need[ch]--;
            }
        }

        // Fill changed positions with the characters still needed
        int next = 0;

        for (int i = 0; i < n; i++) {
            if (result[i] != '#')
                continue;

            while (next < bestK && need[idx[next]] == 0) {
                next++;
            }

            result[i] = (char) ('a' + idx[next]);
            need[idx[next]]--;
        }

        System.out.println(n - bestSave);
        System.out.println(new String(result));
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

// problem name - C. Equal Frequencies
// problem link - https://codeforces.com/problemset/problem/1781/C
// Date - 2026-10-29
// Time - 22:56:45