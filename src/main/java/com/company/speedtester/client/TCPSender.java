package com.company.speedtester.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class TCPSender extends Sender{
  private boolean nagle;
  private Socket socket;

  TCPSender(String ipAddr, int port, int size, boolean nagle) {
    super(ipAddr, port, size);
    this.nagle = nagle;
  }

  public void run(){
    try {
      this.socket = new Socket(this.ipAddr, this.port);
      this.socket.setTcpNoDelay(this.nagle);

      DataOutputStream clientOutputSteam = new DataOutputStream(this.socket.getOutputStream());
      final byte[] sizeMessage = ("SIZE:" + this.messageSize).getBytes();
      clientOutputSteam.write(sizeMessage, 0, sizeMessage.length);
      clientOutputSteam.flush();
      while (sending){
        final byte[] message = new byte[this.messageSize];
        Arrays.fill(message, (byte) 1);

        clientOutputSteam.write(message, 0, message.length);
        clientOutputSteam.flush();
        sleep(300);
      }
      clientOutputSteam.close();
      socket.close();
    } catch (IOException | InterruptedException e) {
        System.out.println("Connection refused");
    }
  }
}
