import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class BSecretStringPatterns {

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

    static void solve(FastScanner sc, BufferedReader reader) throws Exception {
        int n = sc.nextInt();

        // Query 1: Get total number of 1s in the entire string
        System.out.println("? 1 " + n);
        System.out.flush();

        String resp = reader.readLine();
        if (resp == null)
            System.exit(0);
        int c = Integer.parseInt(resp.trim());
        if (c == -1)
            System.exit(0);

        // If all 0s or all 1s, no "01" pattern can exist
        if (c == 0 || c == n) {
            System.out.println("! NO");
            System.out.flush();
            return;
        }

        // Query 2: Get number of 1s in the prefix of length c
        System.out.println("? 1 " + c);
        System.out.flush();

        resp = reader.readLine();
        if (resp == null)
            System.exit(0);
        int x = Integer.parseInt(resp.trim());
        if (x == -1)
            System.exit(0);

        // If all c ones are in the first c positions, the string is 1^c 0^{n-c} (no
        // "01")
        if (x == c) {
            System.out.println("! NO");
        } else {
            System.out.println("! YES");
        }
        System.out.flush();
    }

    // ==================== MAIN ====================

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String firstToken = fs.next();
        if (firstToken == null)
            return;
        int t = Integer.parseInt(firstToken);

        while (t-- > 0) {
            solve(fs, reader);
        }
    }
}