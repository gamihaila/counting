package com.example;

public class Main {

  public static void print(WindowCounter w, int ts) {
    System.out.println("Time: " + ts + " count: " + w.count(ts));
  }

  public static void main(String[] args) {
    WindowCounter w = new WindowCounter(100);

    for (int ts = 1; ts < 1000; ts++) {
      w.add(ts);
      w.dump();
    }
    for (int ts = 1000; ts <= 1200; ts++) {
      print(w, ts);
      w.cleanup(ts);
    }
  }
}
