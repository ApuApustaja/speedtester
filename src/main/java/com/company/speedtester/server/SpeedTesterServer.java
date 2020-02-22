package com.company.speedtester.server;

import java.util.Scanner;

public class SpeedTesterServer {

    public static void main(String[] args) {

        Scanner scanPort = new Scanner(System.in);
        System.out.println("Enter port number:");
        int port = 8080;
        //scanner.nextInt();
        TCPReceiver tcpServ = new TCPReceiver(port);
        UDPReceiver udpServ = new UDPReceiver(port);
        tcpServ.start();
        udpServ.start();

        System.out.printf("1. stop \n 2. exit");
        while (true){
            try {
                if (scanPort.nextLine().equals("1")) {
                    tcpServ.terminate();
                    udpServ.terminate();
                } else if (scanPort.nextLine().equals("2")) {
                    udpServ.terminate();
                    tcpServ.terminate();
                    break;
                }
            } catch (Exception e){
                System.out.println("Wrong input");
            }

        }
    }
}
