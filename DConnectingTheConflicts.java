import java.io.*;
import java.util.*;

public class DConnectingTheConflicts {

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

    static int findExcludedVertex(ArrayList<ArrayList<Integer>> g, int n) {
        int[] parent = new int[n + 1];
        int[] order = new int[n];
        int[][] dp = new int[n + 1][2];
        int[] stack = new int[n];

        int top = 0, cnt = 0;
        stack[top++] = 1;
        parent[1] = -1;

        while (top > 0) {
            int u = stack[--top];
            order[cnt++] = u;

            for (int v : g.get(u)) {
                if (v == parent[u])
                    continue;

                parent[v] = u;
                stack[top++] = v;
            }
        }

        for (int i = cnt - 1; i >= 0; i--) {
            int u = order[i];

            dp[u][0] = 0;
            dp[u][1] = 1;

            for (int v : g.get(u)) {
                if (parent[v] != u)
                    continue;

                dp[u][0] += Math.max(dp[v][0], dp[v][1]);
                dp[u][1] += dp[v][0];
            }
        }

        boolean[] selected = new boolean[n + 1];

        for (int i = 0; i < cnt; i++) {
            int u = order[i];

            if (parent[u] != -1 && selected[parent[u]]) {
                selected[u] = false;
            } else {
                selected[u] = dp[u][1] >= dp[u][0];
            }
        }

        for (int u = 1; u <= n; u++) {
            if (!selected[u])
                return u;
        }

        return -1;
    }

    static void solve(FastScanner fs, StringBuilder out)
            throws IOException {

        int k = fs.nextInt();
        int hubTree = -1;
        int hubVertex = -1;

        for (int i = 1; i <= k; i++) {
            int n = fs.nextInt();

            ArrayList<ArrayList<Integer>> g = new ArrayList<>();

            for (int j = 0; j <= n; j++) {
                g.add(new ArrayList<>());
            }

            for (int j = 0; j < n - 1; j++) {
                int u = fs.nextInt();
                int v = fs.nextInt();

                g.get(u).add(v);
                g.get(v).add(u);
            }

            if (hubTree == -1 && n > 1) {
                hubVertex = findExcludedVertex(g, n);
                hubTree = i;
            }
        }

        if (hubTree == -1) {
            for (int i = 1; i < k; i++) {
                out.append(i).append(" 1 ")
                        .append(i + 1).append(" 1\n");
            }
        } else {
            for (int i = 1; i <= k; i++) {
                if (i == hubTree)
                    continue;

                out.append(hubTree).append(" ")
                        .append(hubVertex).append(" ")
                        .append(i).append(" 1\n");
            }
        }
    }
    // ==================== MAIN ====================

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        int t = 1;
        t = fs.nextInt();

        StringBuilder out = new StringBuilder();

        while (t-- > 0) {
            solve(fs, out);
        }
        System.out.print(out);
    }
}

// problem name - D. Connecting the Conflicts
// problem link - https://codeforces.com/contest/2272/problem/D
// Date - 2026-10-26
// Time - 21:14:25