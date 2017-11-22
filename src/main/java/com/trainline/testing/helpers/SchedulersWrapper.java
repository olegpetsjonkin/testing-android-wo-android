package com.trainline.testing.helpers;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class SchedulersWrapper {

   public Scheduler background() {
       return Schedulers.computation();
   }

   public Scheduler main() {
       return Schedulers.io();
   }
}
