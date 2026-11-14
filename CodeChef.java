// import java.util.*;
// import java.lang.*;
// import java.io.*;

// class Codechef {
//     public static void main(String[] args) throws java.lang.Exception {
//         // your code goes here
//         Scanner sc = new Scanner(System.in);

//         int t = sc.nextInt();

//         while (t-- > 0) {
//             int n = sc.nextInt();

//             int[] a = new int[n];

//             for (int i = 0; i < n; i++) {
//                 a[i] = sc.nextInt();
//             }

//             for (int i = 0; i < n; i++) {
//                 for (int j = i + 1; j < n; j++) {
//                     if (a[i] - a[j] > 1) {
//                         int temp = a[i];
//                         a[i] = a[j];
//                         a[j] = temp;
//                     }
//                 }
//             }
//             for (int elm : a) {
//                 System.out.print(elm + " ");
//             }
//             System.out.println();
//         }
//     }
// }

// import java.util.*;
// import java.lang.*;
// import java.io.*;

// public class Codechef {

//     static class FastScanner {
//         private final InputStream in = System.in;
//         private final byte[] buffer = new byte[1 << 16];
//         private int ptr = 0, len = 0;

//         private int read() throws IOException {
//             if (ptr >= len) {
//                 len = in.read(buffer);
//                 ptr = 0;
//                 if (len <= 0)
//                     return -1;
//             }
//             return buffer[ptr++];
//         }

//         int nextInt() throws IOException {
//             int c;
//             do {
//                 c = read();
//             } while (c <= ' ');

//             int sign = 1;
//             if (c == '-') {
//                 sign = -1;
//                 c = read();
//             }

//             int res = 0;
//             while (c > ' ') {
//                 res = res * 10 + (c - '0');
//                 c = read();
//             }
//             return res * sign;
//         }
//     }

//     public static void main(String[] args) throws Exception {
//         FastScanner fs = new FastScanner();
//         StringBuilder out = new StringBuilder();

//         int T = fs.nextInt();

//         while (T-- > 0) {
//             int N = fs.nextInt();

//             int[] pos = new int[N + 1];

//             for (int i = 0; i < N; i++) {
//                 int x = fs.nextInt();
//                 pos[x] = i;
//             }

//             // graph[x] contains y if x must come before y
//             List<Integer>[] graph = new ArrayList[N + 1];

//             for (int i = 1; i <= N; i++) {
//                 graph[i] = new ArrayList<>();
//             }

//             int[] indegree = new int[N + 1];

//             // Only consecutive values have fixed relative order.
//             for (int x = 1; x < N; x++) {
//                 if (pos[x] < pos[x + 1]) {
//                     // x must appear before x + 1
//                     graph[x].add(x + 1);
//                     indegree[x + 1]++;
//                 } else {
//                     // x + 1 must appear before x
//                     graph[x + 1].add(x);
//                     indegree[x]++;
//                 }
//             }

//             // Lexicographically smallest topological ordering
//             PriorityQueue<Integer> pq = new PriorityQueue<>();

//             for (int x = 1; x <= N; x++) {
//                 if (indegree[x] == 0) {
//                     pq.offer(x);
//                 }
//             }

//             while (!pq.isEmpty()) {
//                 int x = pq.poll();

//                 out.append(x).append(' ');

//                 for (int y : graph[x]) {
//                     indegree[y]--;

//                     if (indegree[y] == 0) {
//                         pq.offer(y);
//                     }
//                 }
//             }

//             out.append('\n');
//         }

//         System.out.print(out);
//     }
// }

// import java.util.*;
// import java.lang.*;
// import java.io.*;

// public class Codechef {

//     static final long MOD = 998244353L;

//     // ==================== FAST SCANNER ====================

//     static class FastScanner {
//         private final InputStream in = System.in;
//         private final byte[] buffer = new byte[1 << 16];
//         private int ptr = 0;
//         private int len = 0;

//         private int read() throws IOException {
//             if (ptr >= len) {
//                 len = in.read(buffer);
//                 ptr = 0;

//                 if (len <= 0) {
//                     return -1;
//                 }
//             }

//             return buffer[ptr++];
//         }

//         int nextInt() throws IOException {
//             int c;

//             do {
//                 c = read();
//             } while (c <= ' ');

//             int sign = 1;

//             if (c == '-') {
//                 sign = -1;
//                 c = read();
//             }

//             int res = 0;

//             while (c > ' ') {
//                 res = res * 10 + (c - '0');
//                 c = read();
//             }

//             return res * sign;
//         }
//     }

//     // ==================== SOLUTION ====================

//     static void solve(FastScanner fs, StringBuilder out) throws Exception {

//         int n = fs.nextInt();

//         int[] pos = new int[n + 1];

//         // Store the position of every value.
//         for (int i = 1; i <= n; i++) {
//             int x = fs.nextInt();
//             pos[x] = i;
//         }

//         long[] dp = new long[n + 1];
//         dp[1] = 1;

//         for (int i = 2; i <= n; i++) {

//             long[] prefix = new long[n + 1];

//             for (int j = 1; j <= i - 1; j++) {
//                 prefix[j] = (prefix[j - 1] + dp[j]) % MOD;
//             }

//             long[] next = new long[n + 1];

//             if (pos[i - 1] < pos[i]) {

//                 for (int j = 1; j <= i; j++) {
//                     next[j] = prefix[j - 1];
//                 }

//             } else {
//                 long total = prefix[i - 1];

//                 for (int j = 1; j <= i; j++) {
//                     next[j] = (total - prefix[j - 1] + MOD) % MOD;
//                 }
//             }

//             dp = next;
//         }

//         // Sum over all possible positions of n.
//         long answer = 0;

//         for (int j = 1; j <= n; j++) {
//             answer = (answer + dp[j]) % MOD;
//         }

//         out.append(answer).append('\n');
//     }

//     // ==================== MAIN ====================

//     public static void main(String[] args) throws Exception {

//         FastScanner fs = new FastScanner();

//         StringBuilder out = new StringBuilder();

//         int t = fs.nextInt();

//         while (t-- > 0) {
//             solve(fs, out);
//         }

//         System.out.print(out);
//     }
// }

// import java.util.*;
// import java.lang.*;
// import java.io.*;

// public class Codechef {

//     static final long INF = Long.MAX_VALUE / 4;

//     // ==================== FAST SCANNER ====================

//     static class FastScanner {
//         private final InputStream in = System.in;
//         private final byte[] buffer = new byte[1 << 16];
//         private int ptr = 0;
//         private int len = 0;

//         private int read() throws IOException {
//             if (ptr >= len) {
//                 len = in.read(buffer);
//                 ptr = 0;

//                 if (len <= 0) {
//                     return -1;
//                 }
//             }

//             return buffer[ptr++];
//         }

//         int nextInt() throws IOException {
//             int c;

//             do {
//                 c = read();
//             } while (c <= ' ');

//             int sign = 1;

//             if (c == '-') {
//                 sign = -1;
//                 c = read();
//             }

//             int res = 0;

//             while (c > ' ') {
//                 res = res * 10 + (c - '0');
//                 c = read();
//             }

//             return res * sign;
//         }
//     }

//     // ==================== SOLVE ====================

//     static void solve(FastScanner fs, StringBuilder out) throws Exception {

//         int n = fs.nextInt();

//         int[] a = new int[n];

//         long total = 0;

//         for (int i = 0; i < n; i++) {
//             a[i] = fs.nextInt();
//             total += a[i];
//         }

//         Arrays.sort(a);

//         long[] prefix = new long[n + 1];

//         for (int i = 0; i < n; i++) {
//             prefix[i + 1] = prefix[i] + a[i];
//         }

//         long answer = 0;

//         for (int k = 1; k < n; k++) {

//             long sr;

//             if (n - 2L * k > 0) {
//                 sr = prefix[n] - prefix[n - k];

//             } else if (n - 2L * k < 0) {
//                 sr = prefix[k];

//             } else {
//                 sr = 0;
//             }

//             long sb = total - sr;

//             long cb = n - k;

//             long value = sr * cb + sb * k;

//             answer = Math.max(answer, value);
//         }

//         out.append(answer).append('\n');
//     }

//     // ==================== MAIN ====================

//     public static void main(String[] args) throws Exception {

//         FastScanner fs = new FastScanner();

//         StringBuilder out = new StringBuilder();

//         int t = fs.nextInt();

//         while (t-- > 0) {
//             solve(fs, out);
//         }

//         System.out.print(out);
//     }
// }

import java.util.*;
import java.lang.*;
import java.io.*;

class Codechef {

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

        int nextInt() throws IOException {
            int c;

            do {
                c = read();
            } while (c <= ' ');

            int res = 0;

            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = read();
            }

            return res;
        }
    }

    // ==================== FENWICK TREE ====================

    static class Fenwick {

        int n;
        int[] bit;

        Fenwick(int n) {
            this.n = n;
            this.bit = new int[n + 1];
        }

        void add(int idx, int val) {
            while (idx <= n) {
                bit[idx] += val;
                idx += idx & -idx;
            }
        }

        int sum(int idx) {
            int res = 0;

            while (idx > 0) {
                res += bit[idx];
                idx -= idx & -idx;
            }

            return res;
        }
    }

    // ==================== SOLVE ====================

    static void solve(FastScanner fs, StringBuilder out)
            throws Exception {

        int n = fs.nextInt();

        int[] pos = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int x = fs.nextInt();
            pos[x] = i - 1; // 0-based position
        }

        long[] invStart = new long[n + 1];
        long[] invEnd = new long[n + 1];

        Fenwick fw = new Fenwick(n);

        // Calculate invStart.
        for (int x = n; x >= 1; x--) {

            int p = pos[x] + 1;

            invStart[x] = fw.sum(p - 1);

            fw.add(p, 1);
        }

        fw = new Fenwick(n);

        // Calculate invEnd.
        for (int x = 1; x <= n; x++) {

            int p = pos[x] + 1;

         
            long smaller = x - 1L;
            long beforeOrEqual = fw.sum(p);

            invEnd[x] = smaller - beforeOrEqual;

            fw.add(p, 1);
        }

        // Total number of inversions.
        long totalInv = 0;

        for (int x = 1; x <= n; x++) {
            totalInv += invStart[x];
        }

        long[] prefixPos = new long[n + 1];

        for (int x = 1; x <= n; x++) {
            prefixPos[x] = prefixPos[x - 1] + pos[x];
        }

        long[] suffixDistance = new long[n + 2];

        for (int x = n; x >= 1; x--) {
            suffixDistance[x] = suffixDistance[x + 1]
                    + (n - 1L - pos[x]);
        }

        long[] prefixBoth = new long[n + 1];

        for (int x = 1; x <= n; x++) {
            prefixBoth[x] = prefixBoth[x - 1]
                    + invStart[x]
                    + invEnd[x];
        }

        long[] prefixInv = new long[n + 1];

        for (int x = 1; x <= n; x++) {
            prefixInv[x] = prefixInv[x - 1] + invStart[x];
        }

        long answer = Long.MAX_VALUE;

        int L = 1;

        for (int R = 1; R <= n; R++) {

            boolean endOfRun = (R == n || pos[R] > pos[R + 1]);

            if (!endOfRun) {
                continue;
            }

            int leftCount = L - 1;
            int rightCount = n - R;

            long invLeft = prefixInv[L - 1];

            long invRight = totalInv
                    - prefixInv[R];

            long middleCross = prefixBoth[R]
                    - prefixBoth[L - 1];

            long cross = totalInv
                    - invLeft
                    - invRight
                    - middleCross;

       
            long leftCost = prefixPos[L - 1]
                    + (long) leftCount * (leftCount - 1) / 2
                    - invLeft;

            
            long rightCost = suffixDistance[R + 1]
                    + (long) rightCount * (rightCount - 1) / 2
                    - invRight;

            long cost = leftCost
                    + rightCost
                    - cross;

            answer = Math.min(answer, cost);

            L = R + 1;
        }

        out.append(answer).append('\n');
    }

    // ==================== MAIN ====================

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner();

        StringBuilder out = new StringBuilder();

        int T = fs.nextInt();

        while (T-- > 0) {
            solve(fs, out);
        }

        System.out.print(out);
    }
}