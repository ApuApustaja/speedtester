package com.company.speedtester.client;

public class Sender extends Thread {
  protected String ipAddr;
  protected int port;
  protected int messageSize;
  protected boolean sending;

  Sender(String ipAddr, int port, int size){
    this.ipAddr = ipAddr;
    this.port = port;
    this.messageSize = size;
    this.sending = true;
  }
}
