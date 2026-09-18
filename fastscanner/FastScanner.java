import java.io.*;
import java.util.*;

public class FastScanner {
    private final InputStream in;
    private final byte[] buffer = new byte[1 << 16];
    private int ptr = 0, len = 0;

    FastScanner(InputStream is) {
        this.in = is;
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
        int sign = 1;
        if (c == '-') {
            sign = -1;
            c = read();
        }
        int val = 0;
        while (c > 32) {
            val = val * 10 + (c - '0');
            c = read();
        }
        return val * sign;
    }
}