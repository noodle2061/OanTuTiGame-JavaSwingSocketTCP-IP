package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Comparator;
import model.MatchHistory;

public class MatchHistoryDAO extends DBContext {

    // Tạo một lịch sử đấu mới
    public void createMatchHistory(int player1Id, int player2Id, int state, LocalDateTime dateTime) {
        String sql = "INSERT INTO match_history (user_id1, user_id2, state, date_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, player1Id);
            ps.setInt(2, player2Id);
            ps.setInt(3, state);
            ps.setTimestamp(4, Timestamp.valueOf(dateTime)); // Chuyển đổi LocalDateTime sang Timestamp
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hàm thêm một lịch sử đấu vào cơ sở dữ liệu
    public void insertMatchHistory(MatchHistory matchHistory) {
        String sql = "INSERT INTO match_history (userid1, userid2, state, date_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Gán giá trị từ đối tượng MatchHistory
            ps.setInt(1, matchHistory.getPlayer1Id());
            ps.setInt(2, matchHistory.getPlayer2Id());
            ps.setInt(3, matchHistory.getState());
            ps.setTimestamp(4, Timestamp.valueOf(matchHistory.getDateTime())); // Chuyển đổi LocalDateTime sang Timestamp

            // Thực thi câu lệnh
            ps.executeUpdate();

            // Lấy ID tự động tăng
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    matchHistory.setId(rs.getInt(1)); // Cập nhật ID vào đối tượng MatchHistory
                }
            }
            System.out.println("Match history inserted successfully with ID: " + matchHistory.getId());
        } catch (SQLException e) {
            System.err.println("Error inserting match history: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lấy lịch sử đấu theo id
//    public MatchHistory getMatchHistoryById(int id) {
//    String sql = "SELECT * FROM match_history WHERE id = ?";
//    try (PreparedStatement ps = connection.prepareStatement(sql)) {
//        ps.setInt(1, id);
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            return new MatchHistory(
//                rs.getInt("id"),              // Sử dụng đúng tên cột
//                rs.getInt("userid1"),         // Đổi từ user_id1 -> userid1
//                rs.getInt("userid2"),         // Đổi từ user_id2 -> userid2
//                rs.getInt("state"),           // Đổi từ state -> state (không cần thay đổi)
//                rs.getTimestamp("date_time").toLocalDateTime() // Chuyển datetime từ SQL sang LocalDateTime
//            );
//        }
//    } catch (SQLException e) {
//        System.err.println("Error fetching match history by ID: " + e.getMessage());
//        e.printStackTrace();
//    }
//    return null; // Trả về null nếu không tìm thấy bản ghi
//}
    // Lấy tất cả lịch sử đấu
    public List<MatchHistory> getAllMatchHistories() {
        List<MatchHistory> matchHistories = new ArrayList<>();
        String sql = "SELECT * FROM match_history";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                matchHistories.add(new MatchHistory(
                        rs.getInt("id"),
                        rs.getInt("user_id1"),
                        rs.getInt("user_id2"),
                        rs.getInt("state"),
                        rs.getTimestamp("date_time").toLocalDateTime() // Chuyển đổi thành LocalDateTime
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchHistories;
    }

    // Cập nhật trạng thái đấu
    public void updateMatchHistoryState(int id, int state) {
        String sql = "UPDATE match_history SET state = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, state);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy lịch sử đấu của một người theo id
    public List<MatchHistory> getMatchHistoryById(int id) {
        List<MatchHistory> matchHistories = new ArrayList<>();
        String sql = "SELECT * FROM match_history WHERE userid1 = ? OR userid2 = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                matchHistories.add(new MatchHistory(
                        rs.getInt("id"),
                        rs.getInt("userid1"),
                        rs.getInt("userid2"),
                        rs.getInt("state"),
                        rs.getTimestamp("date_time").toLocalDateTime() // Chuyển đổi thành LocalDateTime
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Sắp xếp danh sách theo ngày giờ giảm dần
        matchHistories.sort(Comparator.comparing(MatchHistory::getDateTime).reversed());

        return matchHistories;
    }

    // Xóa lịch sử đấu theo id
    public void deleteMatchHistory(int id) {
        String sql = "DELETE FROM match_history WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
