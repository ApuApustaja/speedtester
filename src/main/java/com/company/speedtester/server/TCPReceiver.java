package com.company.speedtester.server;

import org.apache.commons.lang3.time.StopWatch;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPReceiver extends Receiver {
  private ServerSocket tcpSocket;
  public TCPReceiver(int portToUse){
    super(portToUse);
  }

  public void run(){
    System.out.println("Start TCP thread");
    try {
      tcpSocket = new ServerSocket(this.port);
    } catch (IOException e) {
      System.out.println("UNABLE TO CREATE SOCKET ON PORT");
      e.printStackTrace();
    }

    while (Avaible){
      try {
        Socket client = tcpSocket.accept();

        final DataInputStream in = new DataInputStream(client.getInputStream());
        byte[] clientBufferSizeBytes = new byte[32];
        int bytes = in.read(clientBufferSizeBytes);
        String clientBufferSizeString = new String(clientBufferSizeBytes, 0, bytes);

        int clientBufferSize = Integer.parseInt(clientBufferSizeString.replaceAll("[^0-9]", ""));

        byte[] clientMessage = new byte[clientBufferSize];

        StopWatch watch = new StopWatch();
        watch.start();

        while(Avaible) {
          try {
            bytes = in.read(clientMessage);
            if (bytes < 0) {
              watch.stop();
              break;
            }
            this.receivedBytes += clientBufferSize;
          } catch (Exception exc) {
            System.out.println("Closing connection due unexpected error");
            tcpSocket.close();
            watch.stop();
            break;
          }
        }
        System.out.println("Wątek (TCP): odebrano " + receivedBytes/1024.0 + "kb danych w czasie " + watch.getTime() + "s z prędkością " + (receivedBytes/1024.0)/watch.getTime() + "kb/sec");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  public void terminate() {
    this.Avaible = false;
    try {
      this.tcpSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
