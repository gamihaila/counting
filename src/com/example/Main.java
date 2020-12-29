package com.example;

public class Main {

  public static void print(WindowCounter w, int ts) {
    System.out.println("Time: " + ts + " count: " + w.count(ts));
  }

  public static void main(String[] args) {
    WindowCounter w = new WindowCounter(100, 0.1);

    for (int ts = 1; ts < 400; ts++) {
      System.out.println("Adding event with ts: " + ts);
      w.add(ts);
      print(w, ts);
    }
    for (int ts = 400; ts <= 500; ts++) {
      print(w, ts);
      w.cleanup(ts);
    }
  }
}
