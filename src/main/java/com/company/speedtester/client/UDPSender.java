package com.company.speedtester.client;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class UDPSender extends Sender {
  private InetAddress inetAddress;
  private DatagramSocket socket;

  UDPSender(String ipAddr, int port, int size) {
    super(ipAddr, port, size);
    try {
      this.socket = new DatagramSocket();
      this.inetAddress = InetAddress.getByName(ipAddr);
    } catch (UnknownHostException | SocketException e) {
      System.out.println("Unable to create client");
    }
  }

  public void run(){
    DatagramPacket datagramPacket;
    byte[] message = ("SIZE:" + this.messageSize).getBytes();
    datagramPacket = new DatagramPacket(message, message.length, this.inetAddress, this.port);
    try {
      this.socket.send(datagramPacket);
    } catch (IOException e) {
      System.out.println("Unable to send size");
    }
    while (sending){
      message = new byte[this.messageSize];
      Arrays.fill(message, (byte) 1);
      datagramPacket = new DatagramPacket(message, message.length, this.inetAddress, this.port);
      try {
        this.socket.send(datagramPacket);
      } catch (IOException e) {
        System.out.println("Unable to send message");
      }
      try {
        sleep(200L);
      } catch (InterruptedException e) {
        System.out.println("Interrupted");
      }
    }

    message = ("FINE").getBytes();
    datagramPacket = new DatagramPacket(message, message.length, this.inetAddress, this.port);
    try {
      this.socket.send(datagramPacket);
    } catch (IOException e) {
      System.out.println("Unable to send FINE");    }

  }

}