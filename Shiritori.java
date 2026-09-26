import java.io.*;
import java.util.*;

public class Shiritori {

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

                if (len <= 0) {
                    return -1;
                }
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
        if (n < 2) {
            return false;
        }

        if (n % 2 == 0) {
            return n == 2;
        }

        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
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

    // ==================== BUILD GRAPH ====================

    static ArrayList<ArrayList<Integer>> build(
            ArrayList<String> list,
            HashMap<String, Integer> map) {

        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        for (String s : list) {

            String from = s.substring(0, 3);
            String to = s.substring(s.length() - 3);

            if (!map.containsKey(from)) {
                map.put(from, map.size());
                adj.add(new ArrayList<>());
            }

            if (!map.containsKey(to)) {
                map.put(to, map.size());
                adj.add(new ArrayList<>());
            }

            int u = map.get(from);
            int v = map.get(to);

            adj.get(u).add(v);
        }

        return adj;
    }

    // ==================== SOLVE ====================

    static void solve(FastScanner fs) throws Exception {

        int n = fs.nextInt();

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            list.add(fs.next());
        }

        HashMap<String, Integer> map = new HashMap<>();

        ArrayList<ArrayList<Integer>> adj = build(list, map);

        int nodes = adj.size();

        /*
         * state:
         *
         * 0 = Unknown / Draw
         * 1 = Winning
         * 2 = Losing
         */

        int[] state = new int[nodes];

        /*
         * outDegree[u] =
         * number of possible moves from u.
         */
        int[] outDegree = new int[nodes];

        /*
         * Reverse graph.
         *
         * If:
         *
         * u -> v
         *
         * then:
         *
         * reverse[v] contains u.
         */

        ArrayList<ArrayList<Integer>> reverse = new ArrayList<>();

        for (int i = 0; i < nodes; i++) {
            reverse.add(new ArrayList<>());
        }

        for (int u = 0; u < nodes; u++) {

            outDegree[u] = adj.get(u).size();

            for (int v : adj.get(u)) {
                reverse.get(v).add(u);
            }
        }

        /*
         * Queue for reverse BFS.
         *
         * A state with no possible move is losing.
         */
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < nodes; i++) {

            if (outDegree[i] == 0) {
                state[i] = 2; // Losing
                queue.add(i);
            }
        }

        /*
         * Retrograde analysis.
         */
        while (!queue.isEmpty()) {

            int cur = queue.poll();

            for (int prev : reverse.get(cur)) {

                /*
                 * Already classified.
                 */
                if (state[prev] != 0) {
                    continue;
                }

                /*
                 * If we can move from prev to a
                 * losing state, prev is winning.
                 */
                if (state[cur] == 2) {

                    state[prev] = 1; // Winning
                    queue.add(prev);

                } else {

                    /*
                     * cur is winning.
                     *
                     * Remove this winning option.
                     *
                     * If all moves from prev lead
                     * to winning states, prev loses.
                     */
                    outDegree[prev]--;

                    if (outDegree[prev] == 0) {

                        state[prev] = 2; // Losing
                        queue.add(prev);
                    }
                }
            }
        }

        /*
         * For every original word:
         *
         * Takahashi has already spoken the word.
         * Therefore Aoki is now at the state
         * represented by the last 3 characters.
         */

        StringBuilder ans = new StringBuilder();

        for (String s : list) {

            String last = s.substring(s.length() - 3);

            int node = map.get(last);

            switch (state[node]) {
                case 2 -> ans.append("Takahashi\n");
                case 1 -> ans.append("Aoki\n");
                default -> ans.append("Draw\n");
            }
        }

        System.out.print(ans);
    }

    // ==================== MAIN ====================

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner();

        int t = 1;

        // t = fs.nextInt();

        while (t-- > 0) {
            solve(fs);
        }
    }
}