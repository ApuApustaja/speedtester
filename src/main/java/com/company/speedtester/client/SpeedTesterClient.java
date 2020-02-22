package com.company.speedtester.client;

import java.util.Scanner;

public class SpeedTesterClient {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    try {
      System.out.println("IP:");
      String ip = scanner.nextLine();
      System.out.println("port:");
      int port = scanner.nextInt();
      System.out.println("Size");
      int size = scanner.nextInt();
      System.out.println("Nagle (true/false");
      boolean nagle = scanner.nextBoolean();

      TCPSender tcpSender = new TCPSender(ip, port, size, nagle);
      UDPSender udpSender = new UDPSender(ip, port, size);

      tcpSender.start();
      udpSender.start();

      System.out.println("exit to exit program");
      while (true) {
        if (scanner.nextLine().equals("exit")) {
          tcpSender.sending = false;
          udpSender.sending = false;
          break;
        }
      }

    } catch (Exception e) {
      System.out.println("Wrong input");
    }
  }
}
