import java.io.*;
import java.util.*;

public class PathFinder {

    static final int[] DR = { -1, -1, -1, 0, 0, 1, 1, 1 };
    static final int[] DC = { -1, 0, 1, -1, 1, -1, 0, 1 };

    static int solve(int[][] value, boolean[][] blocked,
            int sr, int sc, int tr, int tc) {

        int n = value.length;
        int m = value[0].length;

        int[][] dist = new int[n][m];

        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(
                Comparator.comparingInt(a -> a[0]));

        dist[sr][sc] = 0;
        pq.offer(new int[] { 0, sr, sc });

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();

            int d = curr[0];
            int r = curr[1];
            int c = curr[2];

            if (d != dist[r][c])
                continue;

            if (r == tr && c == tc) {
                return d;
            }

            // Explore all 8 directions
            for (int k = 0; k < 8; k++) {
                int nr = r + DR[k];
                int nc = c + DC[k];

                if (nr < 0 || nr >= n ||
                        nc < 0 || nc >= m ||
                        blocked[nr][nc]) {
                    continue;
                }

                // Target must exceed the current cell
                int maxOther = value[r][c];

                // Find maximum among other valid neighbours
                for (int j = 0; j < 8; j++) {
                    int ar = r + DR[j];
                    int ac = c + DC[j];

                    if (ar < 0 || ar >= n ||
                            ac < 0 || ac >= m ||
                            blocked[ar][ac]) {
                        continue;
                    }

                    // Exclude the intended target
                    if (ar == nr && ac == nc) {
                        continue;
                    }

                    maxOther = Math.max(
                            maxOther, value[ar][ac]);
                }

                // Minimum addition to make target unique maximum
                int extra = Math.max(
                        0, maxOther - value[nr][nc] + 1);

                int nd = d + extra;

                if (nd < dist[nr][nc]) {
                    dist[nr][nc] = nd;
                    pq.offer(new int[] { nd, nr, nc });
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int n = fs.nextInt();
        int m = fs.nextInt();

        int[][] value = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                value[i][j] = fs.nextInt();
            }
        }

        // Convert 1-based indices to 0-based
        int sr = fs.nextInt() - 1;
        int sc = fs.nextInt() - 1;

        int tr = fs.nextInt() - 1;
        int tc = fs.nextInt() - 1;

        int k = fs.nextInt();
        boolean[][] blocked = new boolean[n][m];

        for (int i = 0; i < k; i++) {
            int r = fs.nextInt() - 1;
            int c = fs.nextInt() - 1;
            blocked[r][c] = true;
        }

        System.out.println(
                solve(value, blocked, sr, sc, tr, tc));
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];

        private int ptr = 0, len = 0;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;

                if (len <= 0)
                    return -1;
            }

            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;

            do {
                c = read();
            } while (c <= 32 && c != -1);

            int num = 0;

            while (c > 32) {
                num = num * 10 + c - '0';
                c = read();
            }

            return num;
        }
    }
}
