package model;

import java.time.LocalDateTime;

public class MatchHistory {
    private int id;
    private int player1Id;
    private int player2Id;
    private int state;
    private LocalDateTime dateTime;

    // Default Constructor
    public MatchHistory() {
    }

    // Constructor with dateTime
    public MatchHistory(int id, int player1Id, int player2Id, int state, LocalDateTime dateTime) {
        this.id = id;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.state = state; // 1 is win, 0 is lose, 2 is draw
        this.dateTime = dateTime;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(int player1Id) {
        this.player1Id = player1Id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(int player2Id) {
        this.player2Id = player2Id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "MatchHistory{" +
                "id=" + id +
                ", player1Id=" + player1Id +
                ", player2Id=" + player2Id +
                ", state=" + state +
                ", dateTime=" + dateTime +
                '}';
    }
}
