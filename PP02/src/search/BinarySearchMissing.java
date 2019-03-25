package search;

public class BinarySearchMissing {
    static int n, x;
    static int[] a;

    public static void main(String[] args) {
        n = args.length - 1;
        x = Integer.parseInt(args[0]);
        a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = Integer.parseInt(args[i + 1]);
        }
        int ans = BinarySearchIter(x);
        //int ans = BinarySearchRec(0, n, x);
        //System.out.println(ans);
        if (ans >= a.length) {
            System.out.println(-1 - a.length);
        } else if (a[ans] == x) {
            System.out.println(ans);
        } else if (x > a[ans]) {
            System.out.println(-1 - ans);
        } else {
            System.out.println(-1 - a.length);
        }
    }

    // pre: (0 < i < n : a[i - 1] >= a[i])
    // post: (R == n && a[n - 1] > x) || (R == 0 && x >= a[0]) || (0 < R < n && a[R - 1] > x && a[R] <= x)
    public static int BinarySearchIter(int x) {
        // (0 < i < n : a[i - 1] >= a[i])
        int l = 0;
        int r = n;
        // (0 < i < n : a[i - 1] >= a[i]) && l == 0 && r == n
        // inv: (a' == a) && (0 < i < n : a[i - 1] >= a[i]) &&
        //       0 <= l <= r <= n && (l == 0 || x < a[l - 1]) &&
        //      (r == n || x >= a[r]) && (r' - l') >= 2 * (r - l)
        while (l < r) {
            int mid = (l + r) / 2;
            if (a[mid] > x) {
                l = mid + 1;
                //x < a[mid]
            } else {
                r = mid;
                //x >= a[r']
            }
        }
        return r;
        //R = r
    }

    // pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l <= r <= n
    // post: (R == n && a[n - 1] >= x) || (R == 0 && x > a[0]) ||
    //       (0 < R < n && a[R - 1] > x && a[R] <= x)
    // inv: (a' == a) && (0 < i < n : a[i - 1] >= a[i]) &&
    //      0 <= l <= r <= n && (l == 0 || x < a[l - 1]) &&
    //      (r == n || x >= a[r]) && (r' - l') >= 2 * (r - l)
    public static int BinarySearchRec(int l, int r, int x) {
        if (l == r) {
            // l == r
            return r;
            // R = r
        }
        int mid = (l + r) / 2;
        if (a[mid] > x) {
            // r' = r && l' = mid + 1 ->  r'- l' = r - (l+r) div 2 + 1 = (r-l+1) div 2
            // l < r && l' = mid + 1 && l < mid < r  ->  l' < r'
            return BinarySearchRec(mid + 1, r, x);
        } else {
            // r' = mid && l' = l  ->  mid - l' = (l+r) div 2 - l = (r-l+1) div 2
            // l < r && r' = mid && l < mid < r  ->  l' < r'
            return BinarySearchRec(l, mid, x);
        }
    }
}


