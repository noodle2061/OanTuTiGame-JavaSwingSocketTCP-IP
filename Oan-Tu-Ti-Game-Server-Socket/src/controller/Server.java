/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import view.MainFrm;

/**
 *
 * @author admin
 */
public class Server {

    public static ThreadBus threadBus;
    public static MainFrm mainFrm;
    public static List<RoomController> lstRoomController = new ArrayList<>();

    public Server() {
    }

    public static void main(String[] args) {
        
        for (int i = 0; i < 100; i++) {
            RoomController r = new RoomController(i);
            lstRoomController.add(r);
        }
        
        threadBus = new ThreadBus();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                100,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8)
        );
        mainFrm = new MainFrm();
        mainFrm.setLocation(200, 200);
        mainFrm.setVisible(true);

        try {
            mainFrm.addMessage("Server đang hoạt động tại cổng 9989");
            ServerSocket server = new ServerSocket(9989);
            int cnt_user = 1;
            while (true) {
                Socket socket= server.accept();
                ServerThread serverThread = new ServerThread(socket, cnt_user++);
                System.out.println("Client thứ " + cnt_user + " đã kết nối với hệ thống. Số lượng Client đang kết nối là " + threadBus.getListSocket().size());
                threadBus.add(serverThread);
                executor.execute(serverThread);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
