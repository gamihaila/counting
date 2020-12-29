package com.example;

public class Main {

  public static void print(WindowCounter w, int ts) {
    System.out.println("Time: " + ts / 10_000 + " count: " + w.count(ts));
  }

  public static void main(String[] args) {
    WindowCounter w = new WindowCounter(9_000_000, 0.1);

    for (int ts = 0; ts < 50_000_000; ts += 10_000) {
      //System.out.println("Adding event with ts: " + ts);
      w.add(ts);
      print(w, ts);
    }
  }
}
