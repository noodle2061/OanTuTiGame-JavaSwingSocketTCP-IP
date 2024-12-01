/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDateTime;
import model.MatchHistory;
import model.User;

/**
 *
 * @author admin
 */
public class RoomController {

    private int id;
    private ServerThread playerThread1;
    private ServerThread playerThread2;
    private int set;
    private int playerThread1Ready;
    private int playerThread2Ready;

    private int playerThread1WinCount; // số trận thắng của người 1 trong một trận đấu: x/3
    private int playerThread2WinCount; // số trận thắng của người 2 trong một trận đấu: x/3

    private int currentWinner;         // người thắng ở hiệp vừa rồi, mục tiêu để hiển thị lên màn hình

    private String playerThread1Chose; // lựa chọn kéo búa bao gần nhất của người chơi 1
    private String playerThread2Chose; // lựa chọn kéo búa bao gần nhất của người chơi 2

    private int winner;                // người chiến thắng sau 3 set. 0 là hòa, 1 là người 1, 2 là người 2
    private int endingRequestCount;    // số

    // model user ứng với hai người chơi trong phòng
    private int soNguoiGuiKeoBuaBao = 0;

    public int numberOfUserInRoom() {
        int cnt = 0;
        if (playerThread1 != null) {
            cnt++;
        }
        if (playerThread2 != null) {
            cnt++;
        }
        return cnt;
    }

    // reset phòng khi bắt đầu trận mới hoặc chơi lại
    public void resetRoom() {
        set = 0;
        playerThread1Ready = 0;
        playerThread2Ready = 0;
        playerThread1WinCount = 0;
        playerThread2WinCount = 0;
        currentWinner = 0;
        playerThread1Chose = "none";
        playerThread2Chose = "none";
        winner = 0;
        endingRequestCount = 0;
    }

    // ----------------thêm user vào phòng, đã chắc chắn có it nhat một chỗ trống trong phòng (kiểm tra bên phía server thread--------------------
    public void addPlayer(ServerThread player) {
        if (this.playerThread1 == null) {
            this.playerThread1 = player;
            User u1 = playerThread1.getUser();
            u1.setGameStatus(2);
            ServerThread.udb.updateUser(u1);
        } else {
            this.playerThread2 = player;
            User u2 = playerThread2.getUser();
            u2.setGameStatus(2);
            ServerThread.udb.updateUser(u2);
        }
        Server.mainFrm.updateTable();
        sendRoomState();
    }

    // ----------------------------------------xử lý gửi tin nhắn trong phòng----------------------------------------------------
    public void sendMessage(int senderId, String message) {
        if (playerThread1 == null || playerThread2 == null) return;
        if (playerThread1 != null && playerThread1.getUser().getId() == senderId) {
            playerThread2.write("room-chat-receive;" + playerThread1.getUser().getName() + ": " + message);
        } else {
            playerThread1.write("room-chat-receive;" + playerThread2.getUser().getName() + ": " + message);
        }
    }

    //----------------------------------------------------- xử lý thoát phòng------------------------------------------------
    public void outRoomHandle(int userId) {
        if (set == 0) {//----nếu trận đầu chưa bắt đầu thì có thể out phòng
            removePlayer(userId);
        }
    }

    public void removePlayer(int userId) {
        if (playerThread1 != null && this.playerThread1.getUser().getId() == userId) {
            playerThread1.write("out-room-response;success");
            User u1 = playerThread1.getUser();
            u1.setGameStatus(1);
            ServerThread.udb.updateUser(u1);
            this.playerThread1 = null;
        } else {
            playerThread2.write("out-room-response;success");
            User u2 = playerThread2.getUser();
            u2.setGameStatus(1);
            ServerThread.udb.updateUser(u2);
            this.playerThread2 = null;
        }
        Server.mainFrm.updateTable();
        resetRoom();
        sendRoomState();
    }

    // -------------------------------gửi thông tin phòng đến các player có trong phòng-----------------------------------------
    public void sendRoomState() {
        String message = "send-info-of-room";

        // -------------------------------------khi trận đấu chưa bắt đầu---------------------------------------------------------
        if (set == 0) {

            message += ";waiting;";
            String u1 = "null", u2 = "null"; // người chơi 1 và 2
            if (playerThread1 != null) {
                u1 = playerThread1.getUser().toString2();
            } else {
                playerThread1Ready = 0;
            }
            if (playerThread2 != null) {
                u2 = playerThread2.getUser().toString2();
            } else {
                playerThread2Ready = 0;
            }

            message += playerThread1Ready + ";" + playerThread2Ready + ";" + u1 + ";" + u2 + ";";
        } else { // -----------------------------khi bắt đầu trận đấu---------------------------------------
            message += ";playing;" + playerThread1.getUser().getId() + ";" + playerThread2.getUser().getId() + ";" + playerThread1WinCount + ";" + playerThread2WinCount + ";" + currentWinner + ";" + set + ";" + playerThread1Chose + ";" + playerThread2Chose;
            // playerThread1WinCount;playerThread2WinCount;currentWinner;currentSet;playerThread1Chose;playerThread2Chose
        }

        // gui cap nhat den 2 nguoi
        if (playerThread1 != null) {
            playerThread1.write(message);
        }
        if (playerThread2 != null) {
            playerThread2.write(message);
        }
    }

    // --------------------nhận các lựa chọn kéo búa bao của người chơi-----------
    public void getActionHandle(int playerId, String action) {
        if (playerThread1 != null && playerThread1.getUser().getId() == playerId) {
            playerThread1Chose = action;
        } else {
            playerThread2Chose = action;
        }

        //-------------------------- khi nhận được cả hai action của player
        if (!playerThread1Chose.equals("none") && !playerThread2Chose.equals("none")) {
            if (playerThread1Chose.equals(playerThread2Chose)) { // ------------------------- trường hợp hoà
                currentWinner = 0;
            } else if (playerThread1Chose.equals("no-action")) { //------------------------------người 1 không chọn gì
                currentWinner = 2;
            } else if (playerThread2Chose.equals("no-action")) {  //------------------------------người 2 không chọn gì
                currentWinner = 1;
            } else if (playerThread1Chose.equals("Búa")) {
                if (playerThread2Chose.equals("Kéo")) {
                    currentWinner = 1;
                } else if (playerThread2Chose.equals("Bao")) {
                    currentWinner = 2;
                }
            } else if (playerThread1Chose.equals("Kéo")) {
                if (playerThread2Chose.equals("Búa")) {
                    currentWinner = 2;
                } else if (playerThread2Chose.equals("Bao")) {
                    currentWinner = 1;
                }
            } else if (playerThread1Chose.equals("Bao")) {
                if (playerThread2Chose.equals("Búa")) {
                    currentWinner = 1;
                } else if (playerThread2Chose.equals("Kéo")) {
                    currentWinner = 2;
                }
            }

            if (currentWinner == 1) {
                playerThread1WinCount++;
            } else if (currentWinner == 2) {
                playerThread2WinCount++;
            }

            // -------------------------------------nếu đến hiệp cuối thì cập nhật database luôn
            if (set == 3) {
                if (playerThread1WinCount == playerThread2WinCount) {
                    winner = 0;
                } else if (playerThread1WinCount > playerThread2WinCount) {
                    winner = 1;
                } else {
                    winner = 2;
                }
                // cập nhật database
                udateDatabase();
            }

            set += 1;
            sendRoomState();
            playerThread1Chose = "none";
            playerThread2Chose = "none";
        }
    }

    public void udateDatabase() {
        User playerModel1 = playerThread1.getUser();
        User playerModel2 = playerThread2.getUser();

        // Tăng số trận trong database lên 1
        playerModel1.setMatchCount(playerModel1.getMatchCount() + 1);
        playerModel2.setMatchCount(playerModel2.getMatchCount() + 1);

        // Thêm lịch sử đấu vào database
        MatchHistory matchHistory = new MatchHistory(
                0, // ID tự động tăng
                playerModel1.getId(),
                playerModel2.getId(),
                winner, // người thắng trận
                LocalDateTime.now() // thời điểm kết thúc trận đấu
        );

        // cập nhật các thông tin liên quan đến điểm và các loại số trận
        switch (winner) {
            case 1:
                playerModel1.setWinCount(playerModel1.getWinCount() + 1);
                playerModel2.setLoseCount(playerModel2.getLoseCount() + 1);
                playerModel1.setPoints(playerModel1.getPoints() + 2);
                break;
            case 2:
                playerModel2.setWinCount(playerModel2.getWinCount() + 1);
                playerModel1.setLoseCount(playerModel1.getLoseCount() + 1);
                playerModel2.setPoints(playerModel2.getPoints() + 2);
                break;
            case 0:
                playerModel1.setDrawCount(playerModel1.getDrawCount() + 1);
                playerModel2.setDrawCount(playerModel2.getDrawCount() + 1);
                playerModel1.setPoints(playerModel1.getPoints() + 1);
                playerModel2.setPoints(playerModel2.getPoints() + 1);
                break;
        }

        // Cập nhật người chơi trong database
        ServerThread.udb.updateUser(playerModel1);
        ServerThread.udb.updateUser(playerModel2);

        // Thêm lịch sử đấu
        ServerThread.mdb.insertMatchHistory(matchHistory);

        // cập nhật lại playerThread
        playerThread1.setUser(playerModel1);
        playerThread2.setUser(playerModel2);

        // cập nhật user ở client
        playerThread1.write("update-user;" + playerModel1.toString2());
        playerThread2.write("update-user;" + playerModel2.toString2());
    }

    // xử lý kết thúc game
    public void finishGameHandle(ServerThread player) {
        String message = "finish-this-game;" + playerThread1.getUser().getId() + ";" + playerThread2.getUser().getId() + ";"; // người thắng;
        player.write(message + winner);
    }

    public void refreshRoom(ServerThread player, String isOutRoom) {
        if (isOutRoom.equals("1")) {
            if (playerThread1 != null && player.getUser().getId() == playerThread1.getUser().getId()) {
                playerThread1.write("out-room-response;success");
                User u1 = playerThread1.getUser();
                u1.setGameStatus(1);
                ServerThread.udb.updateUser(u1);
                playerThread1 = null;
            } else {
                playerThread2.write("out-room-response;success");
                User u2 = playerThread2.getUser();
                u2.setGameStatus(1);
                ServerThread.udb.updateUser(u2);
                playerThread2 = null;
            }
        }

        endingRequestCount++;
        if (endingRequestCount == 2) {  // Xử lý làm mới phòng khi cả hai đã gửi yêu cầu làm mới phòng
            // Làm mới phòng
            resetRoom();
            sendRoomState();
            Server.mainFrm.updateTable();
        }
    }

    // ------------------------------------------------------constructor--------------------------------------------------
    public RoomController() {
        set = 0;
    }

    public RoomController(int id) {
        this.id = id;
        resetRoom();
    }

    // -----------------------------------------------------GET AND SET--------------------------------------------
    public void setPlayerReady(int id) {
        if (playerThread1 != null && playerThread1.getUser().getId() == id) {
            setPlayer1Ready(1 - getPlayer1Ready());
        } else {
            setPlayer2Ready(1 - getPlayer2Ready());
        }
        sendRoomState();
//         nếu cả hai đều sẵn sàng thì bắt đầu trò chơi
        if (playerThread1Ready + playerThread2Ready == 2) {
            // bat dau hiep 1
            set = 1;
            sendRoomState();

        }

    }

    public ServerThread getPlayer1() {
        return playerThread1;
    }

    public void setPlayer1(ServerThread playerThread1) {
        this.playerThread1 = playerThread1;
    }

    public ServerThread getPlayer2() {
        return playerThread2;
    }

    public void setPlayer2(ServerThread playerThread2) {
        this.playerThread2 = playerThread2;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayer1Ready() {
        return playerThread1Ready;
    }

    public void setPlayer1Ready(int playerThread1Ready) {
        this.playerThread1Ready = playerThread1Ready;
    }

    public int getPlayer2Ready() {
        return playerThread2Ready;
    }

    public void setPlayer2Ready(int playerThread2Ready) {
        this.playerThread2Ready = playerThread2Ready;
    }

    @Override
    public String toString() {
        return id + ";" + numberOfUserInRoom() + ";";
    }
}
