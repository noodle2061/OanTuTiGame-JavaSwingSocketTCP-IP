/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.MatchHistoryDAO;
import dal.UserDAO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.MatchHistory;
import model.User;

/**
 *
 * @author admin
 */
public class ServerThread implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private User user;
    private int roomId;
    private int numberOfClient;

    // Khai báo đối tượng DAO 
    public static UserDAO udb = new UserDAO();
    public static MatchHistoryDAO mdb = new MatchHistoryDAO();

    public ServerThread(Socket socket, int numberOfClient) {
        this.numberOfClient = numberOfClient;
        try {
            this.socket = socket;
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getNumberOfClient() {
        return numberOfClient;
    }

    public void setId_user(int numberOfClient) {
        this.numberOfClient = numberOfClient;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Gửi gói tin đến Client
    public void write(String message) {
        String userName = "guess";
        writer.println(message);
        writer.flush();
        if (user != null) {
            userName = user.getName();
        }
        System.out.println("Server to " + userName + " : " + message);

    }

    @Override
    public void run() {

        try {
            // Luôn lắng nghe Client
            while (true) {
                String receiveMessage = reader.readLine();
                String[] req = receiveMessage.split(";");
                String type = req[0];

                System.out.println("Client: " + receiveMessage);

                // Phần này Khanh code
                // Xử lý yêu cầu đăng nhập
                if (type.equals("login-request")) {
                    try {
                        String name = req[1];
                        String password = req[2];
                        user = udb.verifyUser(name, password);
                        if (user != null) {
                            if (user.getGameStatus() == 1) {
                                write("login-response;fail;Tài khoản hiện đang được đăng nhập ở nơi khác!");
                            } else {
                                user.setGameStatus(1);
                                udb.updateUser(user);
                                write("login-response;success;" + user);
                                Server.threadBus.boardCast(numberOfClient, "Thông báo hệ thống: " + user.getName() + " vừa mới online.");
                                Server.threadBus.sendMessageToUserID(user.getId(), "home;Thông báo hệ thống: Bạn vừa đăng nhập thành công. Chào mừng bạn đến với trò chơi.");
                                Server.mainFrm.addMessage(user.getName() + " vừa đăng nhập thành công.");
                                Server.mainFrm.updateTable();
                            }
                        } else {
                            write("login-response;fail;Đăng nhập thất bại! Vui lòng kiểm tra lại tài khoản và mật khẩu.");
                        }
                    } catch (Exception e) {
                        System.out.println("lối đăng nhập: " + e);
                    }
                }

                // Xử lý yêu cầu đăng ký
                if (type.equals("register-request")) {
                    String name = req[1];
                    String password = req[2];
                    String avatar = req[3];

                    User u = udb.verifyUser(name, password);

                    if (u == null) {
                        udb.createUser(name, password, avatar);
                        write("register-response;success");
                    } else {
                        write("register-response;fail;Tài khoản đã tồn tại");
                    }
                }
                // xử lý log-out
                if (type.equals("log-out-request")) {
                    user = udb.getUserById(user.getId()); // lấy lại thông tin user trước khi cập nhật
                    this.user.setGameStatus(0);
                    udb.updateUser(user);
                    write("log-out-sucess");
                    Server.threadBus.boardCast(numberOfClient, "Thông báo hệ thống: " + user.getName() + " vừa thoát trò chơi.");
                    Server.mainFrm.addMessage(user.getName() + " vừa thoát trò chơi.");
                    Server.mainFrm.updateTable();
                    this.user = null;
                }
                // xử lý đổi mật khẩu
                if (type.equals("change-password-request")) {
                    String name = req[1];
                    String password = req[2];
                    String newPassword = req[3];
                    if (!user.getPassword().equals(password)) {
                        write("change-password;fail;Nhập sai mật khẩu.");
                    } else {
                        user = udb.getUserById(user.getId()); // lấy lại thông tin user trước khi cập nhật
                        user.setPassword(newPassword);
                        udb.updateUser(user);
                        write("change-password;success;" + user);
                    }

                }
                // xử lý xóa tài khoản
                if (type.equals("delete-account-request")) {
                    String name = req[1];
                    String password = req[2];

                    if (!user.getPassword().equals(password)) {
                        write("delete-account;fail;Nhập sai mật khẩu!");
                    } else {
                        udb.deleteUser(user.getId());
                        write("delete-account;success");
                    }
                    Server.mainFrm.updateTable();
                }
                // tìm list room
                if (type.equals("get-list-room")) {
                    try {
                        int num = (Integer.parseInt(req[1]) - 1) * 5;
                        String resultString = "";
                        for (int i = num; i < num + 5; i++) {
                            resultString += Server.lstRoomController.get(i);
                        }
                        write("return-list-room;" + resultString);
                    } catch (NumberFormatException e) {
                    }

                }
                // boardcast từ client
                if (type.equals("boardcast-request")) {
                    String message = req[1];
                    Server.threadBus.boardCast(numberOfClient, user.getName() + ": " + message);
                    Server.mainFrm.addMessage(user.getName() + ": " + message);
                }

                // lấy lịch sử đấu
                if (type.equals("get-history-request")) {
                    try {
                        // Kiểm tra yêu cầu hợp lệ
                        if (req.length < 2) {
                            write("error;Invalid request format;");
                            return;
                        }

                        int id = Integer.parseInt(req[1]);
                        List<MatchHistory> matchHistories = mdb.getMatchHistoryById(id);

                        // Chuẩn bị thông điệp trả về
                        StringBuilder messageToSend = new StringBuilder("return-history;");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                        for (MatchHistory m : matchHistories) {
                            int opponentId = (m.getPlayer1Id() == id) ? m.getPlayer2Id() : m.getPlayer1Id();
                            int state = m.getState();

                            // Đảo trạng thái nếu người chơi thứ hai là user
                            if (m.getPlayer2Id() == id && state != 0) {
                                if (state == 1) {
                                    state = 2;
                                } else {
                                    state = 1;
                                }
                            }

                            // Lấy tên người chơi đối thủ
                            User opponent = udb.getUserById(opponentId);
                            String opponentName = (opponent == null) ? "Unknown" : opponent.getName();

                            // Lấy ngày giờ định dạng
                            String formattedDate = m.getDateTime().format(formatter);

                            // Thêm dữ liệu lịch sử vào thông điệp
                            messageToSend.append(opponentName)
                                    .append(",")
                                    .append(state)
                                    .append(",")
                                    .append(formattedDate)
                                    .append(";");
                        }

                        // Gửi dữ liệu về client
                        write(messageToSend.toString());

                    } catch (NumberFormatException e) {
                        write("error;Invalid user ID format;");
                        e.printStackTrace();
                    } catch (Exception e) {
                        write("error;An error occurred while processing the request;");
                        e.printStackTrace();
                    }
                }

// Phần này Yến code
                if (type.equals("get-ranking-request")) {
                    List<User> rankingList = udb.getRankingList();
                    StringBuilder rankingMessage = new StringBuilder("ranking-response;");

                    int rank = 1;
                    for (User rankedUser : rankingList) {
                        int stateGame = rankedUser.getGameStatus();
                        String stateString;
                        if (stateGame == 0) {
                            stateString = "off";
                        } else if(stateGame == 1) {
                            stateString = "onl";
                        } else {
                            stateString = "playing";
                        }
                        rankingMessage.append(rank).append(";") // Thứ hạng
                                .append(rankedUser.getName()).append(";") // Tên người chơi
                                .append(rankedUser.getPoints()).append(";") // Điểm số
                                .append(stateString) // Trạng thái
                                .append(";");
                        rank++;
                    }

                    write(rankingMessage.toString());
                }

// Phần này Hải và Anh code
                //Xử lý chức năng chơi ngay
                if (type.equals("play-now-request")) {
                    try {
                        int findRoom1Player = 0;
                        for (RoomController room : Server.lstRoomController) {
                            if (room.numberOfUserInRoom() == 1) {
                                write("join-room-response;success;" + room.getId());
                                roomId = room.getId();
                                room.addPlayer(this);
                                findRoom1Player = 1;
                                break;
                            }
                        }
                        if (findRoom1Player == 0) {
                            for (RoomController room : Server.lstRoomController) {
                                if (room.numberOfUserInRoom() == 0) {
                                    write("join-room-response;success;" + room.getId());
                                    roomId = room.getId();
                                    room.addPlayer(this);
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("lỗi chơi ngay " + e);
                    }

                }

                // Xử lý yêu cầu vào phòng từ danh sách phòng
                if (type.equals("join-room-request")) {
                    this.roomId = Integer.parseInt(req[1]);
                    int playerId = Integer.parseInt(req[2]);

                    if (Server.lstRoomController.get(roomId).numberOfUserInRoom() == 2) { // so luong nguoi da du
                        write("join-room-response;fail;Phòng đã đủ 2 người");
                    } else {
                        write("join-room-response;success;" + roomId);
                        Server.lstRoomController.get(roomId).addPlayer(this);
                    }
                }

                // xử lý tin nhắn trong phòng chơi game
                if (type.equals("room-chat-send")) {
//                    int competitorId = Integer.parseInt(req[1]);
                    String chatText = req[1];
                    Server.lstRoomController.get(roomId).sendMessage(user.getId(), chatText);
                }

                // client bấm nút sẵn sàng
                if (type.equals("player-ready")) {
                    Server.lstRoomController.get(roomId).setPlayerReady(user.getId());
                }

                // nhận thao tác game
                if (type.equals("send-game-action")) {
                    String action = req[1];
                    Server.lstRoomController.get(roomId).getActionHandle(user.getId(), action);
                }

                // kết thúc trò chơi
                if (type.equals("finish-game-request")) {
                    Server.lstRoomController.get(roomId).finishGameHandle(this);
                }

                // làm mới phòng
                if (type.equals("refresh-room-request")) {
                    Server.lstRoomController.get(roomId).refreshRoom(this, req[1]);
                }

                // nhận yêu cầu rời phòng
                if (type.equals("out-room-request")) {
                    Server.lstRoomController.get(roomId).outRoomHandle(user.getId());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            disconnectedHandle();
        }
    }
    
    // xử lý khi client mất kết nối
    public void disconnectedHandle() {
        System.out.println("Client disconnected: " + numberOfClient);
        if (this.user != null) {
            user = udb.getUserById(user.getId()); // lấy lại các thông số của user trước khi cập nhật
            Server.threadBus.boardCast(numberOfClient, "Thông báo hệ thống: " + user.getName() + " vừa thoát trò chơi.");
            Server.mainFrm.addMessage(user.getName() + " vừa thoát trò chơi.");
            this.user.setGameStatus(0);
            udb.updateUser(user);
        }
        Server.threadBus.remove(numberOfClient);
        Server.mainFrm.updateTable();
    }
}
