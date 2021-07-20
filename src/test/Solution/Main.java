package test.Solution;


import java.util.*;

public class Main {

    private static int res = 0;

    public static void main(String[] args) {
        double f = 1.456;
    }


    public static double dis(int mid, int dist[]) {
        double r = 0.0;
        for (int i = 0; i < dist.length; i++) {
            if (i != dist.length - 1) {
                double temp = (double)(dist[i] / (double)mid);
                r += Math.ceil(temp);
            } else {
                r += Double.parseDouble(String.format("%.2f", dist[i] / (double)mid));
            }
        }
        return r;
    }
}
