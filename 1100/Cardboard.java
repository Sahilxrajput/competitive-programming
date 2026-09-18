import java.io.*;
import java.util.*;

public class Cardboard {

    static boolean possible(long w, int[] s, long c) {
        long total = 0;

        for (int x : s) {
            long side = x + 2L * w;

            // If side^2 > c, total can never be <= c
            if (side > Math.sqrt(c)) {
                return false;
            }

            long area = side * side;

            // Avoid total + area overflowing
            if (total > c - area) {
                return false;
            }

            total += area;
        }

        return total <= c;
    }

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {

            int n = fs.nextInt();
            long c = fs.nextLong();

            int[] s = new int[n];

            for (int i = 0; i < n; i++) {
                s[i] = fs.nextInt();
            }

            long low = 1;
            long high = 1_000_000_000L;
            long ans = -1;

            while (low <= high) {

                long mid = low + (high - low) / 2;

                if (possible(mid, s, c)) {
                    ans = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            out.append(ans).append('\n');
        }

        System.out.print(out);
    }

    static class FastScanner {

        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;
        private int len = 0;

        FastScanner(InputStream in) {
            this.in = in;
        }

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

        long nextLong() throws IOException {

            int ch;

            do {
                ch = read();
            } while (ch <= ' ');

            long sign = 1;

            if (ch == '-') {
                sign = -1;
                ch = read();
            }

            long res = 0;

            while (ch > ' ') {
                res = res * 10 + (ch - '0');
                ch = read();
            }

            return res * sign;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}