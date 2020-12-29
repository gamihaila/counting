package com.example;

import java.util.LinkedList;
import java.util.List;

public class WindowCounter {
  private final int width;
  private final List<Bucket> buckets;
  private int k;

  public WindowCounter(int widthDeciMillis, double epsilon) {
    width = widthDeciMillis;
    k = (int) Math.ceil(1 / epsilon);
    if (k % 2 == 1) {
      k++;
    }
    buckets = new LinkedList<>();
  }

  public void add(long tsDeciMillis) {
    expire(tsDeciMillis);
    buckets.add(0, new Bucket(tsDeciMillis));
    dump();
    merge();
  }


  public int count(long tsDeciMillis) {
    expire(tsDeciMillis);
    if (buckets.isEmpty()) {
      return 0;
    }
    int sum = buckets.get(0).count;
    for (int i = 1; i < buckets.size() - 1; i ++) {
      sum += buckets.get(i).count;
    }
    sum += buckets.get(buckets.size() - 1).count / 2;
    return sum;
  }

  public void cleanup(int tsDeciMillis) {
    expire(tsDeciMillis);
  }

  public void dump() {
    for (Bucket bucket : buckets) {
      System.out.print("[" + bucket.ts + ", " + bucket.count + "] ");
    }
    System.out.println();
  }

  private int tsDiff(int ts1, int ts2) {
    return ModuloInteger.mod(ts1 - ts2, width);
  }

  private void expire(long tsDeciMillis) {
    if (buckets.removeIf(bucket -> bucket.ts < tsDeciMillis - width)) {
      System.out.println("Removed expired buckets");
      dump();
    }
  }

  private int countSameSizeBuckets(int start) {
    int count = 1;
    final int startBucketCount = buckets.get(start).count;
    for (int i = start + 1; i < buckets.size(); i++) {
      if (buckets.get(i).count == startBucketCount) {
        count++;
      } else {
        break;
      }
    }
    return count;
  }

  private void merge() {
    for (int i = 0; i < buckets.size(); i++) {
      if (countSameSizeBuckets(i) == k / 2 + 2) {
        final int mergeIndex = i + k / 2;
        System.out.println("Merging buckets " + mergeIndex + " and " + (mergeIndex + 1));
        buckets.get(mergeIndex).count *= 2;
        buckets.remove(mergeIndex + 1);
        dump();
      }
    }
  }

  private static class Bucket {
    long ts;

    public Bucket(long ts) {
      this.ts = ts;
      this.count = 1;
    }

    int count;
  }
}
