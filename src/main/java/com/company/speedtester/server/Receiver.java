package com.company.speedtester.server;

public class Receiver extends Thread {
  protected int port;
  protected long receivedBytes;
  protected long timeRunning;
  protected boolean Avaible;
  Receiver(){
    this.port = 8080;
    this.receivedBytes = 0;
    this.timeRunning = 0;
    this.Avaible = true;
  }
  Receiver(int portToUse){
    this.port = portToUse;
    this.receivedBytes = 0;
    this.timeRunning = 0;
    this.Avaible = true;
  }
}
