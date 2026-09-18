import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        sc.nextLine();

        while (t-- > 0) {
            String A = sc.nextLine();
            String B = sc.nextLine();

            int n = A.length();
            int m = B.length();
            int lcs = 0;

            for (int len = 1; len <= Math.min(n, m); len++) {
                for (int i = 0; i + len <= n; i++) {

                    for (int j = 0; j + len <= m; j++) {
                        String extractA = A.substring(i, i + len);
                        String extractB = B.substring(j, j + len);

                        if (extractA.equals(extractB)) {
                            lcs = Math.max(lcs, len);
                        }
                    }
                }
            }

            int operations = n + m - 2 * lcs;
            System.out.println(operations);
        }

        sc.close();
    }
}