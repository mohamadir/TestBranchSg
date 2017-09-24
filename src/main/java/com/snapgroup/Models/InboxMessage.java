package com.snapgroup.Models;

/**
 * Created by snapgroup2 on 06/09/17.
 */

public class InboxMessage {
  public   String from,group,date,message;
        public int read;

    public InboxMessage(String from, String group, String date, String message,int read) {
        this.from = from;
        this.group = group;
        this.date = date;
        this.message = message;
        this.read=read;
    }
}
