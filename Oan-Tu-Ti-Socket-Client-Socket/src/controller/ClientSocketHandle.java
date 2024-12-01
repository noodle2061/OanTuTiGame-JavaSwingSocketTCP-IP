
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import javax.swing.SwingUtilities;
import model.User;

/**
 *
 * @author admin
 */
public class ClientSocketHandle implements Runnable { // đa luồng = extends Thread

    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;
//    private RankFrm rankFrm;

    public ClientSocketHandle(Socket socket) {
        try {
            this.socket = socket;
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            this.rankFrm = rankFrm;
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void sendJoinRoomRequest(int roomId, int playerId) {
        String message = "join-room-request;" + roomId + ";" + playerId;
        write(message);
    }

    private void showError(String message) {
        SwingUtilities.invokeLater(() -> Client.homeFrm.notify(message));
    }

    public void write(String message) {
        try {
            if (socket != null && socket.isConnected() && !socket.isClosed()) {
                writer.println(message);
                writer.flush();
                if (writer.checkError()) {
                    throw new IOException("Lỗi khi gửi dữ liệu đến server.");
                }
            } else {
                showError("Kết nối đến server đã mất.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi gửi dữ liệu: " + e.getMessage());
            showError("Lỗi khi gửi dữ liệu đến server.");
//            closeConnection();
        }
    }

    @Override
    public void run() {
        try {
            // lắng nghe từ server
            while (true) {

                String receivedMessage = reader.readLine();
                String[] res = receivedMessage.trim().split(";");
                String type = res[0];

                System.out.println(receivedMessage);

                // phan nay Khanh code
                // phản hồi login
                if (type.equals("login-response")) {
                    String state = res[1];
                    if (state.equals("success")) {
                        setUser(res);
                        System.out.println("hello");
                        Client.CloseView(Client.View.LOGIN);
                        Client.OpenView(Client.View.HOME);
                    } else {
                        String notifyString = res[2];
                        Client.loginFrm.notify(notifyString);
                    }
                }
                // phản hồi đăng ký
                if (type.equals("register-response")) {
                    String state = res[1];
                    System.out.println(state);
                    if (state.equals("success")) {
//                        System.out.println("hello");
//                        Client.CloseView(Client.View.REGISTER);
//                        Client.OpenView(Client.View.LOGIN);
                        Client.loginFrm.notify("Đăng kí thành công");
                    } else {
                        String msg = res[2];
                        Client.loginFrm.notify(msg);
                    }
                }
                // phản hồi đăng xuất
                if (type.equals("log-out-sucess")) {
                    Client.user = null;
                    Client.globalChatList.clear();
                    Client.CloseView(Client.View.HOME);
                    Client.OpenView(Client.View.LOGIN);
                }
                // phản hồi đổi mật khẩu
                if (type.equals("change-password")) {
                    String result = res[1];
                    if (result.equals("fail")) {
                        String notifyString = res[2];
                        Client.changePasswordFrm.notify(notifyString);
                    } else {
                        setUser(res);
                        Client.CloseView(Client.View.CHANGE_PASSSWORD);
                        Client.OpenView(Client.View.HOME);
                        Client.homeFrm.notify("Thay đổi mật khẩu thành công!");
                    }

                }
                // phản hồi xóa tài khoản
                if (type.equals("delete-account")) {
                    String result = res[1];
                    if (result.equals("fail")) {
                        String notifyMessage = res[2];
                        Client.deleteAccountFrm.notify(notifyMessage);
                    } else {
                        Client.CloseView(Client.View.DELETE_ACCOUNT);
                        Client.OpenView(Client.View.LOGIN);
                        Client.loginFrm.notify("Xóa tài khoản thành công");
                    }
                }

                // trả về list phòng
                if (type.equals("return-list-room")) {
                    Client.CloseView(Client.View.HOME);
                    if (Client.findRoomFrm == null) {
                        Client.OpenView(Client.View.FIND_ROOM);
                    } else {
//                        Client.findRoomFrm.dispose();
                        Client.findRoomFrm.setVisible(true);
                    }
                    
                    Client.findRoomFrm.updateTable(res);
                }
                // xử lý tin nhắn ở kênh thế giới, hiển thị ở Home Frame
                if (type.equals("boardcast")) {
                    Client.globalChatList.add(res[1]);
                    if (Client.homeFrm != null) { // kiểm tra giao diện Home có đang mở hay không rồi mới hiển thị
                        Client.homeFrm.updateChatArea();
                    }
                }

                // xử lý tin nhắn đến cá nhân
                if (type.equals("message-to-one")) {
                    String target = res[1];
                    if (target.equals("home")) {
                        Client.globalChatList.add(res[2]);
                        if (Client.homeFrm != null) {
                            Client.homeFrm.updateChatArea();
                        }
                    }
                }

                if (type.equals("return-history")) {
                    Client.matchHistoryFrm.updateTable(res);
                }

                // Phan nay Yen code
                if (type.equals("ranking-response")) {
                    if (res.length > 1) {
                        onReceiveRank(res);  // Assumes rank data is in the second element
                    } else {
                        System.out.println("Error: No ranking data received.");
                    }
                }

                // Phan nay Hai va Anh code
                //Xử lý yêu cầu vào phòng
                if (type.equals("join-room-response")) {
                    String state = res[1];
                    if (state.equals("fail")) {
                        Client.findRoomFrm.notify(res[2]);
                    } else {
                        Client.roomId = Integer.parseInt(res[2]);
                        Client.CloseView(Client.View.FIND_ROOM);
                        if (Client.homeFrm != null) {
                            Client.CloseView(Client.View.HOME);
                        }
                        Client.OpenView(Client.View.GAME_ROOM);
                        System.out.println("đã chạy dến đây");
                    }
                }

                // xử lý khi nhận về thông tin phòng
                // luôn luôn set vị trí đầu tiên trong phòng là ta, còn vị trí thứ hai trong phòng luôn là đối thủ
                if (type.equals("send-info-of-room")) {
                    Client.gameRoomFrm.updateRoom(res);
                }

                // xử lý nhận tin nhắn trong phòng chơi
                if (type.equals("room-chat-receive")) {
                    String messageTxt = res[1];
                    Client.gameRoomFrm.setChatArea(messageTxt);
                }

                // xử lý kết thúc trận đấu
                if (type.equals("finish-this-game")) {
                    Client.gameRoomFrm.updateRoom(res);
                }

                // xử lý thoát phòng
                if (type.equals("out-room-response")) {
                    String state = res[1];
                    if (state.equals("success")) {
                        Client.competitor = null;
                        Client.CloseView(Client.View.GAME_ROOM);
                        Client.OpenView(Client.View.HOME);
                    }
                }
                
                // xử lý cập nhật user sau khi kết thúc game
                if (type.equals("update-user")) {
                    Client.user = new User(res[1]);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi kết nối đến server: " + e.getMessage());
            showError("Lỗi kết nối đến server. Vui lòng thử lại.");
        }
    }

    private void setUser(String[] res) {
        int id = Integer.parseInt(res[2]);
        String name = res[3];
        String password = ""; // khong lưu pass ở Client
        int matchCount = Integer.parseInt(res[4]);
        int winCount = Integer.parseInt(res[5]);
        int drawCount = Integer.parseInt(res[6]);
        int loseCount = Integer.parseInt(res[7]);
        int points = Integer.parseInt(res[8]);
        String avatar = res[9];
        int stateGame = Integer.parseInt(res[10]);
        // System.out.println(avatar);
        Client.user = new User(id, name, password, matchCount, winCount, drawCount, loseCount, points, avatar,
                stateGame);
    }

    private User setUser2(String[] res) {
        try {
            if (res.length < 9) {
                return null; // trường hợp ko có ai
            }
            int id = Integer.parseInt(res[1]);
            String name = res[2];
            String password = ""; // khong lưu pass ở Client
            int matchCount = Integer.parseInt(res[3]);
            int winCount = Integer.parseInt(res[4]);
            int drawCount = Integer.parseInt(res[5]);
            int loseCount = Integer.parseInt(res[6]);
            int points = Integer.parseInt(res[7]);
            String avatar = res[8];
            int stateGame = Integer.parseInt(res[9]);
            // System.out.println(avatar);
            return new User(id, name, password, matchCount, winCount, drawCount, loseCount, points, avatar,
                    stateGame);
        } catch (Exception e) {
            return null;
        }

    }

    private void onReceiveRank(String[] rankData) {
        // Chuyển đổi mảng String[] thành String với dấu ";" giữa các phần tử
        StringBuilder sb = new StringBuilder();
        for (String part : rankData) {
            sb.append(part).append(";");
        }
        // Gọi updateRankDisplay với chuỗi đã nối
        Client.rankFrm.updateRankDisplay(sb.toString());
    }

}
