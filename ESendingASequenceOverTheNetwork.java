import java.io.*;

public class ESendingASequenceOverTheNetwork {

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

    // ==================== SOLVE ====================

    static void solve(FastScanner fs) throws Exception {
        // start writing you code
        int n = fs.nextInt();
        boolean[] dp = new boolean[n + 1]; // dp[i] = true if first i elements of b can be produced
        dp[0] = true; // empty prefix is always valid

        for (int i = 1; i <= n; i++) {
            int x = fs.nextInt();

            if (dp[i - 1] && i + x <= n) {
                dp[i + x] = true; // length x written to the left of its segment starting at i+1
            }
            if (i - x - 1 >= 0 && dp[i - x - 1]) {
                dp[i] = true; // length x written to the right of a segment ending at i-1
            }
        }

        System.out.println(dp[n] ? "YES" : "NO");
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

// problem name - E. Sending a Sequence Over the Network
// problem link - https://codeforces.com/problemset/problem/1741/E
// Date - 2026-09-29
// Time - 17:59:58