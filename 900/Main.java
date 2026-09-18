import java.util.*;

public class Main {

    static class Segment {
        int l, r, index;

        Segment(int l, int r, int index) {
            this.l = l;
            this.r = r;
            this.index = index;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Segment[] segments = new Segment[n];

        for (int i = 0; i < n; i++) {
            int l = sc.nextInt();
            int r = sc.nextInt();

            segments[i] = new Segment(l, r, i + 1);
        }

        Arrays.sort(segments, (a, b) -> {
            if (a.l != b.l) {
                return Integer.compare(a.l, b.l);
            }

            return Integer.compare(a.r, b.r);
        });

        int maxR = -1;
        int maxIndex = -1;

        for (Segment cur : segments) {

            // cur is inside the previous segment
            if (cur.r <= maxR) {
                System.out.println(cur.index + " " + maxIndex);
                return;
            }

            if (cur.r > maxR) {
                maxR = cur.r;
                maxIndex = cur.index;
            }
        }

        System.out.println("-1 -1");

        sc.close();
    }
}