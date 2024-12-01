/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ThreadBus {

    private final List<ServerThread> listSocket = new ArrayList<>();

    public ThreadBus() {
    }

    public List<ServerThread> getListSocket() {
        return listSocket;
    }

    public void add(ServerThread thread) {
        listSocket.add(thread);
        Server.mainFrm.setNumberOfClient(listSocket.size());
    }
    
    public int getLength() {
        return listSocket.size();
    }

    public void boardCast(int id, String message) { // id = 1, server id = -1, 0
        for (ServerThread s : Server.threadBus.getListSocket()) {
            if (s.getNumberOfClient() != id && s.getUser() != null) {
                s.write("boardcast;" + message);
            }
        }
    }

    public void sendMessageToUserID(int id, String message) { // id 
        for (ServerThread s : Server.threadBus.getListSocket()) {
            if (s.getUser() != null && s.getUser().getId() == id) {
                s.write("message-to-one;" + message);
                return;
            }
        }
    }
    
    public void sendResponseToUserID(int id, String message) { // id 
        for (ServerThread s : Server.threadBus.getListSocket()) {
            System.out.println(s.getUser().getId());
            if (s.getUser().getId() == id) {
                s.write(message);
                return;
            }
        }
    }

    public void remove(int id) {
        listSocket.removeIf(serverThread -> serverThread.getNumberOfClient() == id);
        System.out.println("Client với ID " + id + " đã được xóa. Số Client đang kết nối là " + listSocket.size());
        Server.mainFrm.setNumberOfClient(listSocket.size());
    }
}
