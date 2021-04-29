package test.Solution;


import java.util.*;

public class Main {

    private static int res = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        String strs[] = str.split(" ");
        int n = Integer.valueOf(strs[0]);
        int arr[] = new int[strs.length - 1];
        for (int i = 1; i < strs.length; i++) {
            arr[i - 1] = Integer.valueOf(strs[i]);
        }
        int i = arr[0];
        int j = arr[1];
        List<Integer> list = new ArrayList<>();
    }
}
