/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author admin
 */
public class UserDAO extends DBContext {

    // Create new user
    public void createUser(String name, String password, String avatar) {
        String sql = "INSERT INTO User (name, password, match_count, win_count, draw_count, lose_count, points, gameStatus, avatar) "
                + "VALUES (?, ?, 0, 0, 0, 0, 0, 0, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, avatar);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read user by id
    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("match_count"),
                        rs.getInt("win_count"),
                        rs.getInt("draw_count"),
                        rs.getInt("lose_count"),
                        rs.getInt("points"),
                        rs.getString("avatar"),
                        rs.getInt("gameStatus")
                );
            } else {
                System.out.println("Không tìm thấy người dùng với ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi thực hiện truy vấn getUserById với ID: " + id);
        }
        return null;
    }

    // Verify user by name and password
    public User verifyUser(String name, String password) {
        String sql = "SELECT * FROM User WHERE name = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("match_count"),
                        rs.getInt("win_count"),
                        rs.getInt("draw_count"),
                        rs.getInt("lose_count"),
                        rs.getInt("points"),
                        rs.getString("avatar"),
                        rs.getInt("gameStatus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Read all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("match_count"),
                        rs.getInt("win_count"),
                        rs.getInt("draw_count"),
                        rs.getInt("lose_count"),
                        rs.getInt("points"),
                        rs.getString("avatar"),
                        rs.getInt("gameStatus")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Update user
    public void updateUser(User user) {
        String sql = "UPDATE User SET name = ?, password = ?, match_count = ?, win_count = ?, draw_count = ?, lose_count = ?, points = ?, avatar = ?, gameStatus = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getMatchCount());
            ps.setInt(4, user.getWinCount());
            ps.setInt(5, user.getDrawCount());
            ps.setInt(6, user.getLoseCount());
            ps.setInt(7, user.getPoints());
            ps.setString(8, user.getAvatar());
            ps.setInt(9, user.getGameStatus());
            ps.setInt(10, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete user by id
    public void deleteUser(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức lấy dữ liệu xếp hạng
    public List<User> getRankingList() {
        List<User> rankingList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM user ORDER BY points DESC";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPoints(rs.getInt("points"));
                user.setGameStatus(rs.getInt("gameStatus"));
                rankingList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rankingList;
    }

    // Lấy danh sách người dùng đang online
    public String getOnlineUsers() {
        StringBuilder onlineUsers = new StringBuilder();
        String sql = "SELECT name, gameStatus FROM User WHERE gameStatus > 0";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name"); // Lấy tên người dùng
                int state = rs.getInt("gameStatus"); // Lấy trạng thái của người dùng

                // Thêm người dùng vào chuỗi kết quả
                if (onlineUsers.length() > 0) {
                    onlineUsers.append(";"); // Thêm dấu chấm phẩy giữa các người dùng
                }
                onlineUsers.append(name).append(",").append(state); // Thêm tên và trạng thái
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }

        return onlineUsers.toString(); // Trả về chuỗi kết quả
    }

    /**
     * // Lấy bảng xếp hạng người dùng public List<User> getRanking() {
     * List<User> rankingList = new ArrayList<>(); String sql = "SELECT name,
     * points, gameStatus FROM User ORDER BY points DESC"; // Truy vấn lấy dữ
     * liệu
     *
     * try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet
     * rs = ps.executeQuery()) { while (rs.next()) { String name =
     * rs.getString("name"); // Lấy tên người dùng int points =
     * rs.getInt("points"); // Lấy điểm người dùng String gameStatus =
     * rs.getString("gameStatus"); // Lấy trạng thái trò chơi
     * rankingList.add(new User(name, points, gameStatus)); // Thêm người dùng
     * vào danh sách } } catch (SQLException e) { e.printStackTrace(); // Xử lý
     * ngoại lệ }
     *
     * return rankingList; // Trả về danh sách bảng xếp hạng }
     */
}
