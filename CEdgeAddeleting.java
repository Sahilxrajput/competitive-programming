import java.io.*;
import java.util.*;

public class CEdgeAddeleting {

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

    // static void initialDist(List<List<Integer>> adj, int[] dist) {

    //     Queue<Integer> q = new LinkedList<>();
    //     q.offer(n);
    //     dist[n] = 0;

    //     while (!q.isEmpty()) {
    //         int u = q.poll();

    //         for (int v : adj.get(u)) {
    //             if (dist[v] == -1) {
    //                 dist[v] = dist[u] + 1;
    //                 q.offer(v);
    //             }
    //         }
    //     }
    // }

    // static void solve(FastScanner fs) throws Exception {
    //     int n = fs.nextInt();
    //     int m = fs.nextInt();

    //     List<List<Integer>> adj = new ArrayList<>();

    //     for (int i = 0; i <= n; i++) {
    //         adj.add(new ArrayList<>());
    //     }

    //     for (int i = 0; i < n - 1; i++) {
    //         int u = fs.nextInt();
    //         int v = fs.nextInt();

    //         adj.get(u).add(v);
    //         adj.get(v).add(u);
    //     }

    //     // Distance between every vertex i and vertex n
    //     int[] dist = new int[n + 1];
    //     Arrays.fill(dist, -1);

    //     initialDist(adj, dist);

    //     // Initial maximum distance from vertex 1 to vertex n
    //     int maxHeight = dist[1];

    //     int[] opers = readArray(fs, m);

    //     for (int oper : opers) {
    //         int minDist = Integer.MAX_VALUE;
    //         int node = -1;

    //         for (int next : adj.get(1)) {
    //             if (dist[next] < minDist) {
    //                 minDist = dist[next];
    //                 node = next;
    //             }
    //         }

    //         if (node != -1) {
    //             adj.get(1).remove(Integer.valueOf(node));
    //             adj.get(node).remove(Integer.valueOf(1));
    //         }
    //     }
        
    // }

    static void initialDist(List<List<Integer>> adj, int[] dist, int n) {
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(n);
        dist[n] = 0;

        while (!q.isEmpty()) {
            int u = q.poll();

            for (int v : adj.get(u)) {
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    q.offer(v);
                }
            }
        }
    }

    static void solve(FastScanner fs) throws Exception {
        int n = fs.nextInt();
        int m = fs.nextInt();

        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < n - 1; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();

            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] opers = readArray(fs, m);

        // Initial distance from every vertex to n
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        initialDist(adj, dist, n);

        // Find the child of vertex 1 that leads to vertex n
        int[] parent = new int[n + 1];
        Arrays.fill(parent, -1);

        Queue<Integer> q = new ArrayDeque<>();
        q.offer(1);
        parent[1] = 0;

        while (!q.isEmpty()) {
            int u = q.poll();

            for (int v : adj.get(u)) {
                if (parent[v] == -1) {
                    parent[v] = u;
                    q.offer(v);
                }
            }
        }

        int child = n;

        while (parent[child] != 1) {
            child = parent[child];
        }

        // Mark the branch containing n
        boolean[] sameBranch = new boolean[n + 1];
        q.offer(child);
        sameBranch[child] = true;

        while (!q.isEmpty()) {
            int u = q.poll();

            for (int v : adj.get(u)) {
                if (!sameBranch[v] && v != 1) {
                    sameBranch[v] = true;
                    q.offer(v);
                }
            }
        }

        // Evaluate the operations
        int ans = dist[1];

        for (int oper : opers) {
            if (sameBranch[oper]) {
                ans = Math.max(ans, dist[oper] + 1);
            }
        }

        System.out.println(ans);
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

// problem name - C. Edge Addeleting
// problem link - https://codeforces.com/contest/2277/problem/C
// Date - 2026-10-24
// Time - 17:42:34