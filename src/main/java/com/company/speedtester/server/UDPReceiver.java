package com.company.speedtester.server;

import javafx.scene.paint.Stop;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPReceiver extends Receiver {
  private DatagramSocket udpSocket;

  public UDPReceiver(int portToUse) {
    super(portToUse);
  }

  public void run() {
    try {
      udpSocket = new DatagramSocket(this.port);
    } catch (SocketException e) {
      System.out.println("UNABLE TO CREATE SOCKET ON PORT");
      e.printStackTrace();
    }

    int sizeOfBuffer = 32;
    StopWatch watch = new StopWatch();
    byte[] dataReceived = new byte[sizeOfBuffer];
    watch.start();
    while (Avaible) {
      DatagramPacket datagramPacket = new DatagramPacket(dataReceived, dataReceived.length);
      try {
        udpSocket.receive(datagramPacket);
      } catch (IOException exception ) {
        System.out.println("socket closed");
      }

      String clientMessage = new String(dataReceived);

      if (clientMessage.contains("FINE")) {
        watch.stop();
        System.out.println("Wątek (UDP): odebrano " + receivedBytes / 1024.0 + "kb danych w czasie " + watch.getTime() / 1000.0 + "s z prędkością " + (receivedBytes / 1024.0) / (watch.getTime() / 1000.0) + "kb/sec");
        this.receivedBytes = 0;
      } else if (clientMessage.contains("SIZE:")) {
        sizeOfBuffer = Integer.parseInt(clientMessage.replaceAll("[^0-9]", ""));
        dataReceived = new byte[sizeOfBuffer];
        watch.start();
        this.receivedBytes = 0;
      } else {
        this.receivedBytes += sizeOfBuffer;
      }
    }
  }

  public void terminate() {
    this.Avaible = false;
    this.udpSocket.close();
  }
}
