/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import java.awt.Point;
import view.DeleteAccountFrm;
import view.LoginFrm2;
import java.net.Socket;
import java.util.ArrayList;
import model.User;
import view.ChangePasswordFrm;
import view.FindRoomFrm;
import view.HomeFrm;
import view.RegisterFrm;
import view.DetailsFrm;
import view.GameRoomFrm;
import view.LoginFrm;
import view.MatchHistoryFrm;
import view.RulesGameFrm;
import view.RankFrm;

/**
 *
 * @author admin
 */
public class Client {

    public static LoginFrm loginFrm;
    public static ClientSocketHandle socketHandle;
    public static Socket socket;
    public static RegisterFrm registerFrm;
    public static DeleteAccountFrm deleteAccountFrm;
    public static ChangePasswordFrm changePasswordFrm;
    public static HomeFrm homeFrm;
    public static DetailsFrm detailsFrm;
    public static FindRoomFrm findRoomFrm;
    public static GameRoomFrm gameRoomFrm;
    public static RulesGameFrm rulesGameFrm;
    public static MatchHistoryFrm matchHistoryFrm;
    public static RankFrm rankFrm;
    public static LoginFrm login2Frm;

    public static User user;
    public static User competitor;  // đối thủ
    public static int roomId;
//    public static boolean isPlaying = false;

    public static Point location = new Point(100, 100); // Biến để lưu tọa độ mà chương trình hiển thị lên màn hình để
    // khi chuyển
    public static String globalChatString = "";
    
    public static List<String> globalChatList = new ArrayList<>();


    // Client.loginFrm
    public Client() {
        try {
            socket = new Socket("localhost", 9989);
            OpenView(View.LOGIN);

            // Chạy socketHandle trên luồng mới để lắng nghe server
            socketHandle = new ClientSocketHandle(socket);
//            socketHandle.run();
            Thread thread = new Thread(socketHandle);
            thread.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public enum View {
        LOGIN,
        REGISTER,
        DELETE_ACCOUNT,
        CHANGE_PASSSWORD,
        HOME,
        DETAILS,
        RULESGAME,
        FIND_ROOM,
        GAME_ROOM,
        MATCH_HISTORY,
        RANK
    }

    public static void OpenView(View viewName) { // mở một view ở vị trí x,y trên màn hình
        int x = location.x, y = location.y;

        switch (viewName) {
            case LOGIN:
//                loginFrm = new LoginFrm();
//                loginFrm.setLocation(x, y);
//                loginFrm.setVisible(true);
                loginFrm = new LoginFrm();
                loginFrm.setLocation(x,y);
                loginFrm.setVisible(true);
                break;
            case REGISTER:
                registerFrm = new RegisterFrm();
                registerFrm.setLocation(x, y);
                registerFrm.setVisible(true);
                break;
            case DELETE_ACCOUNT:
                deleteAccountFrm = new DeleteAccountFrm();
                deleteAccountFrm.setLocation(x, y);
                deleteAccountFrm.setVisible(true);
                break;
            case CHANGE_PASSSWORD:
                changePasswordFrm = new ChangePasswordFrm();
                changePasswordFrm.setLocation(x, y);
                changePasswordFrm.setVisible(true);
                break;
            case HOME:
                homeFrm = new HomeFrm();
                homeFrm.setLocation(x, y);
                homeFrm.updateChatArea(); // cập nhật chat thế giới
                homeFrm.setVisible(true);
                break;
            case DETAILS:
                detailsFrm = new DetailsFrm();
                detailsFrm.setLocation(x, y);
                detailsFrm.setVisible(true);
                break;
            case RULESGAME:
                rulesGameFrm = new RulesGameFrm();
                rulesGameFrm.setLocation(x, y);
                rulesGameFrm.setVisible(true);
                break;
            case FIND_ROOM:
                findRoomFrm = new FindRoomFrm();
                findRoomFrm.setLocation(x, y);
                findRoomFrm.setVisible(true);
                break;
            case GAME_ROOM:
                // Open GAME_ROOM view logic
                gameRoomFrm = new GameRoomFrm();
                                System.out.println("chạy đến đây");
                gameRoomFrm.setLocation(x, y);
                gameRoomFrm.setVisible(true);
                break;
            case MATCH_HISTORY:
                // Open MATCH_HISTORY view logic
                matchHistoryFrm = new MatchHistoryFrm();
                matchHistoryFrm.setLocation(x, y);
                matchHistoryFrm.setVisible(true);
                break;
            case RANK:
                rankFrm = new RankFrm();
                rankFrm.setLocation(x, y);
                rankFrm.setVisible(true);
                break;
            default:
                throw new AssertionError("View not found: " + viewName);
        }
    }

    public static void CloseView(View viewName) { // Đóng một view và lưu lại tọa độ hiện tại
        switch (viewName) {
            case LOGIN:
//                if (loginFrm != null) {
//                    location = loginFrm.getLocation(); // Lưu tọa độ trước khi đóng
//                    loginFrm.dispose();
//                }
                if (loginFrm != null) {
                    location = loginFrm.getLocation();
                    loginFrm.dispose();
                }
                break;
            case REGISTER:
                if (registerFrm != null) {
                    location = registerFrm.getLocation(); // Lưu tọa độ trước khi đóng
                    registerFrm.dispose();
                }
                break;
            case DELETE_ACCOUNT:
                if (deleteAccountFrm != null) {
                    location = deleteAccountFrm.getLocation(); // Lưu tọa độ trước khi đóng
                    deleteAccountFrm.dispose();
                }
                break;
            case CHANGE_PASSSWORD:
                if (changePasswordFrm != null) {
                    location = changePasswordFrm.getLocation(); // Lưu tọa độ trước khi đóng
                    changePasswordFrm.dispose();
                }
                break;
            case HOME:
                if (homeFrm != null) {
                    location = homeFrm.getLocation(); // Lưu tọa độ trước khi đóng
                    homeFrm.dispose();
                }
                break;
            case DETAILS:
                if (detailsFrm != null) {
                    location = detailsFrm.getLocation(); // Lưu tọa độ trước khi đóng
                    detailsFrm.dispose();
                }
                break;
            case RULESGAME:
                if (rulesGameFrm != null) {
                    location = rulesGameFrm.getLocation(); // Lưu tọa độ trước khi đóng
                    rulesGameFrm.dispose();
                }
                break;
            case FIND_ROOM:
                if (findRoomFrm != null) {
                    location = findRoomFrm.getLocation(); // Lưu tọa độ trước khi đóng
                    findRoomFrm.dispose();
                }
                break;
            case GAME_ROOM:
//             Close GAME_ROOM view logic
                if (gameRoomFrm != null) {
                    location = gameRoomFrm.getLocation(); // Lưu tọa độ trước khi đóng
                    gameRoomFrm.dispose();
                }
                break;
            case MATCH_HISTORY:
                // Close MATCH_HISTORY view logic
                if (matchHistoryFrm != null) {
                    location = matchHistoryFrm.getLocation(); // Lưu tọa độ trước khi đóng
                    matchHistoryFrm.dispose();
                }
                break;
            case RANK:
                if (rankFrm != null) {
                    location = rankFrm.getLocation(); // Lưu tọa độ trước khi đóng
                    rankFrm.dispose();
                }
                break;
            default:
                throw new AssertionError("View not found: " + viewName);
        }
    }

    public static void main(String[] args) {
        new Client();
    }

}
