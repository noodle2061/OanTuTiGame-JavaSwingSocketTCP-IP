/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class User {

    private int id;
    private String name;
    private String password;
    private int matchCount; // số trận đã chơi
    private int winCount; //số trận thắng
    private int drawCount; // số trận hòa
    private int loseCount; // số trận thua
    private int points; // điểm số của người chơi
    private String avatar; // ảnh đại diện của người chơi
    private int gameStatus; // 0 là offline, 1 là online, 2 là đang chơi

    public User() {
    }
    public User(String name, int points, int gameStatus) {
        this.name = name;
        this.points = points;
        this.gameStatus = gameStatus;
    }
    
    public User(String s) {
        String[] res = s.split(",");
        try {
            if (res.length < 9) {
                return;
            }
            id = Integer.parseInt(res[1]);
            name = res[2];
            password = ""; // khong lưu pass ở Client
            matchCount = Integer.parseInt(res[3]);
            winCount = Integer.parseInt(res[4]);
            drawCount = Integer.parseInt(res[5]);
            loseCount = Integer.parseInt(res[6]);
            points = Integer.parseInt(res[7]);
            avatar = res[8];
            gameStatus = Integer.parseInt(res[9]);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public User(int id, String name, String password, int matchCount, int winCount, int drawCount, int loseCount, int points, String avatar, int gameStatus) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.matchCount = matchCount;
        this.winCount = winCount;
        this.drawCount = drawCount;
        this.loseCount = loseCount;
        this.points = points;
        this.avatar = avatar;
        this.gameStatus = gameStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String toString2() {
        return id + "," + name + "," + matchCount + "," + winCount + "," + drawCount + "," + loseCount + "," + points + "," + avatar + "," + gameStatus;
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + matchCount + ";" + winCount + ";" + drawCount + ";" + loseCount + ";" + points + ";" + avatar + ";" + gameStatus;
    }
}