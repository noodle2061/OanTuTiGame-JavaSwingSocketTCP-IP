package dal;

import java.time.LocalDateTime;
import model.MatchHistory;

public class test {
    public static void main(String[] args) {
        // Tạo đối tượng DAO
        MatchHistoryDAO mdb = new MatchHistoryDAO();

        // Tạo một đối tượng MatchHistory để thêm vào cơ sở dữ liệu
        MatchHistory matchHistory = new MatchHistory(
            0,              // ID tự động tăng
            6,              // ID của người chơi 1
            8,              // ID của người chơi 2
            1,              // Trạng thái: 1 là thắng, 0 là thua, 2 là hòa
            LocalDateTime.now() // Thời gian hiện tại
        );

        // Gọi hàm insertMatchHistory để thêm vào cơ sở dữ liệu
        System.out.println("Testing insertMatchHistory...");
        mdb.insertMatchHistory(matchHistory);

        // Kiểm tra lại trong cơ sở dữ liệu để đảm bảo rằng bản ghi đã được thêm
        System.out.println("Fetching match history to verify insert...");
//        MatchHistory insertedMatch = mdb.getMatchHistoryById(matchHistory.getId());
//        if (insertedMatch != null) {
//            System.out.println("Insert successful! Retrieved match history:");
//            System.out.println(insertedMatch);
//        } else {
//            System.out.println("Insert failed! Match history not found.");
//        }
    }
}
