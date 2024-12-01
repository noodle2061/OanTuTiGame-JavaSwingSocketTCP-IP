/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.Client;
import helper.ImageHandle;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import model.User;

/**
 *
 * @author ThanhHai
 */
public class GameRoomFrm extends javax.swing.JFrame {

    /**
     * Creates new form GameRoomFrmd
     */
    private StyledDocument doc;

    ImageIcon buaIcon = new ImageIcon("src/image/gameAsset/bua.png");
    ImageIcon keoIcon = new ImageIcon("src/image/gameAsset/keo.png");
    ImageIcon baoIcon = new ImageIcon("src/image/gameAsset/bao.png");
    ImageIcon userThinkingIcon = new ImageIcon("src/image/gameAsset/suynghi.png");
    ImageIcon competitorThinkingIcon = new ImageIcon("src/image/gameAsset/suynghi2.png");
    ImageIcon dauhangIcon = new ImageIcon("src/image/gameAsset/dauhang.png");
    ImageIcon dauhangflipIcon = new ImageIcon("src/image/gameAsset/dauhangflip.png");

//    private boolean isReadyPlayer1 = false;
//    private boolean isReadyPlayer2 = false;
    private String currentSet = "0";
    private String userChose = "no-action";
    private String comptitorChose = "no-action";
    private String userWinCount;
    private String competitorWinCount;
    private String currentWinner;

    public GameRoomFrm() {
        initComponents();
        setupGameRoom();
    }

    public void updateRoom(String[] res) {
        String state = res[1];
        if (state.equals("waiting")) { // chưa chơi
            CardLayout c1 = (CardLayout) mainGamePanel.getLayout();
            c1.show(mainGamePanel, "waiting");
            waitingStateHandle(res);
        } else if (state.equals("playing")) { // đang chơi
            CardLayout c1 = (CardLayout) mainGamePanel.getLayout();
            c1.show(mainGamePanel, "playing");
            playingStateHandle(res);
        } else { // kết thúc trận đầu
            CardLayout c1 = (CardLayout) mainGamePanel.getLayout();
            c1.show(mainGamePanel, "ending");
            endingStateHandle(res);
        }
        // hiển thị
        updatePlayer1Info(Client.user);
        updatePlayer2Info(Client.competitor);
    }

    private void waitingStateHandle(String[] res) {
        System.out.println("--------------------------------hiện tại là waiting");
        // cập nhật trạng thái sẵn sàng hay chưa
        currentSet = "0";
        String ready1 = (res[2].equals("0")) ? "Chưa sẵn sàng" : "Sẵn sàng";
        String ready2 = (res[3].equals("0")) ? "Chưa sẵn sàng" : "Sẵn sàng";

        // xem cả hai đã vào phòng chưa
        if (!res[4].equals("null") && !res[5].equals("null")) {
            waitingLabel.setText("");
        } else {
            waitingLabel.setText("Đang đợi người khác vào");
        }

        // cập nhật người chơi và in ra trạng thái tương ứng
        User u = new User(res[4]);
        if (u.getId() == Client.user.getId()) {
            Client.competitor = new User(res[5]);
        } else {
            Client.competitor = u;
            String tmp = ready1;
            ready1 = ready2;
            ready2 = tmp;
        }
        player1Points.setText(ready1);
        player2Points.setText(ready2);

        readyButton.setText((ready1.equals("Chưa sẵn sàng")) ? "Sẵn sàng" : "Huỷ sẵn sàng");
    }

    public void playingStateHandle(String[] res) {
        // type;state; User1ID; User2ID; player1WinCount;player2WinCount;currentWinner;currentSet;player1Chose;player2Chose
        try {
            // chuyen response tu server sang cac bien co y nghia
            int id1 = Integer.parseInt(res[2]);
            if (id1 == Client.user.getId()) {
                userWinCount = res[4];
                userChose = res[8];
                competitorWinCount = res[5];
                comptitorChose = res[9];
                switch (res[6]) {
                    case "0" -> // hoa
                        currentWinner = "   DRAW";
                    case "1" -> // player1 win
                        currentWinner = "   WIN";
                    case "2" -> // player2 win
                        currentWinner = "   LOSS";
                    default -> {
                        break;
                    }
                }
            } else {
                userWinCount = res[5];
                userChose = res[9];
                competitorWinCount = res[4];
                comptitorChose = res[8];
                currentWinner = switch (res[6]) {
                    case "0" -> "   DRAW";
                    case "1" -> "   LOSS";
                    default -> "   WIN";
                }; // hoa
                // player1 win
                // player2 win
            }
            currentSet = res[7];
            player1Points.setText("Điểm: " + userWinCount);
            player2Points.setText("Điểm: " + competitorWinCount);
        } catch (NumberFormatException e) {
        }
        setPlayingView();
    }

    public void setPlayingView() {
        System.out.println("---------------------------hiện tại là playing set " + currentSet);
        new Thread(() -> {
            SwingUtilities.invokeLater(() -> {
                // thêm lựa chọn của đối thủ
                switch (comptitorChose) {
                    case "Búa":
                        competitorChooseImg.setIcon(buaIcon);
                        break;
                    case "Kéo":
                        competitorChooseImg.setIcon(keoIcon);
                        break;
                    case "Bao":
                        competitorChooseImg.setIcon(baoIcon);
                        break;
                    case "no-action":
                        // không chọn gì cả
                        competitorChooseImg.setIcon(dauhangIcon);
                        break;
                    default:
                        // mở dầu trận đấu
                        competitorChooseImg.setIcon(competitorThinkingIcon);
                        break;
                }

                if (userChose.equals("no-action")) {
                    userChooseImg.setIcon(dauhangflipIcon);
                }

                if (currentSet.equals("1")) {
                    setLabel.setText("READY!");
                } else {
                    setLabel.setText(currentWinner);
                }
            });

            if (currentSet.equals("4")) {
                String contentString = "Đang tổng kết Game, vui lòng chờ";
                String[] dots = {".", "..", "...", "....", "....."};
                for (int i = 0; i < 10; i++) {
                    selectionStatusLabel.setText(contentString + dots[i % 5]);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameRoomFrm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // gọi yêu cầu hiển thị kết quả cuối cùng của game
                Client.socketHandle.write("finish-game-request;");
            } else {
                for (int i = 3; i >= 0; i--) {
                    final int count = i;

                    String contentString = "              Chờ trận đấu bắt đầu: ";
//                SwingUtilities.invokeLater(() -> selectionStatusLabel.setText(contentString + count + "s..."));
                    selectionStatusLabel.setText(contentString + count + "s.");
                    try {
                        Thread.sleep(1000);  // Đợi 1 giây
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //------------------------------cập nhật lại một chút trước khi chính thức vào hiệp tiếp theo
                userChooseImg.setIcon(userThinkingIcon);
                competitorChooseImg.setIcon(competitorThinkingIcon);
                userChose = "no-action";
                selectionStatusLabel.setText("         Hãy cân nhắc khi lựa chọn đáp án...");
                setLabel.setText("ROUND " + currentSet + "/3");
                competitorChooseImg.setIcon(competitorThinkingIcon);
                userChooseImg.setIcon(userThinkingIcon);

                // ===============================chính thức vào hiệp mới
                for (int i = 10; i >= 0; i--) {
                    final int count = i;
                    SwingUtilities.invokeLater(() -> countNumber.setText(count + "S"));
                    try {
                        Thread.sleep(1000);  // Đợi 1 giây
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // hiển thị lại chữ "VS"
                selectionStatusLabel.setText("Hết giờ,vui lòng chờ kết quả trong giây lát!");
                countNumber.setText("VS");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameRoomFrm.class.getName()).log(Level.SEVERE, null, ex);
                }
                Client.socketHandle.write("send-game-action;" + userChose);
            }
        }).start();
    }
    //----------------------------------------------------xử lý giao diện tổng kết

    public void endingStateHandle(String[] res) {
        System.out.println("-----------------------hiện tại là ending");

        new Thread(() -> {
            final int id1 = Integer.parseInt(res[1]);

            System.out.println(id1);

            // Cập nhật GUI trên EDT
            SwingUtilities.invokeLater(() -> {
                if (res[3].equals("0")) {
                    winnerLabel.setText("    kẾT QUẢ NGANG TÀI NGANG SỨC!!");
                } else if (id1 == Client.user.getId()) {
                    if (res[3].equals("1")) {
                        winnerLabel.setText("        KẾT QUẢ: BẠN CHIẾN THẮNG");
                    } else {
                        winnerLabel.setText("       KẾT QUẢ: ĐỐI THỦ CHIẾN THẮNG");
                    }
                } else {
                    if (res[3].equals("2")) {
                        winnerLabel.setText("        KẾT QUẢ: BẠN CHIẾN THẮNG");
                    } else {
                        winnerLabel.setText("       KẾT QUẢ: ĐỐI THỦ CHIẾN THẮNG");
                    }
                }
            });

            for (int i = 3; i >= 0; i--) {
                final int finalI = i;
                SwingUtilities.invokeLater(() -> countDownLabel.setText("Chờ " + finalI + "s để bắt đầu ván mới"));
                try {
                    Thread.sleep(1000); // Dừng 1 giây
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Xử lý gửi yêu cầu làm mới phòng, nếu 1 thì là muốn thoát phòng, 0 thì là không muốn thoát phòng
            if (exitCheckBox.isSelected()) {
                Client.socketHandle.write("refresh-room-request;1");
            } else {
                Client.socketHandle.write("refresh-room-request;0");
            }
        }).start();
    }

    private void setupGameRoom() {
        // Đặt countdownLabel trống ban đầu
        countdownLabel.setText("");
        roomName.setText("Phòng " + Client.roomId);

        // xử lý lắng nghe chatText
        chatInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageHandle(chatInput.getText());
            }
        });

        // lắng nghe phím enter trong chat
        quickChatComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra nếu mục được chọn không phải là mục đầu tiên
                if (quickChatComboBox.getSelectedIndex() != 0) {
                    sendMessageHandle((String) quickChatComboBox.getSelectedItem());

                    // Đặt lại về mục đầu tiên sau khi thực hiện hành động
                    quickChatComboBox.setSelectedIndex(0);
                }
            }
        });

        // Thêm ActionListener vào JCheckBox
        exitCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (exitCheckBox.isSelected()) {
                    if (currentSet.equals("0")) {
                        Client.socketHandle.write("out-room-request");
                    }
                } else {
                    System.out.println("Checkbox is not selected.");
                    // Thực hiện hành động khi checkbox bị bỏ chọn
                }
            }
        });
    }

    public void updatePlayer1Info(User player) {
        if (player == null) {
            return;
        }
        player1Label.setText(player.getName() + " (bạn)");
        Image avtImage = ImageHandle.StringToImage(Client.user.getAvatar());
        int avtSize = 50;
        avtImage = ImageHandle.resizeImage(avtImage, avtSize);
        avtImage = ImageHandle.cropImageToCircle(avtImage);
        player1Avatar.setIcon(new ImageIcon(avtImage)); // Cập nhật avatar nếu có
    }

    public void updatePlayer2Info(User player) {
        player2Label.setText(player.getName());
        Image avtImage = ImageHandle.StringToImage(Client.competitor.getAvatar());
        int avtSize = 50;
        avtImage = ImageHandle.resizeImage(avtImage, avtSize);
        avtImage = ImageHandle.cropImageToCircle(avtImage);
        player2Avatar.setIcon(new ImageIcon(avtImage)); // Cập nhật avatar nếu có
    }

    public void setChatArea(String newMessage) {
//        chatArea.setText(chatArea.getText() + newMessage + "\n");

        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        if (newMessage.startsWith("bạn")) {
            StyleConstants.setForeground(attributeSet, Color.BLUE);
        } else {
            StyleConstants.setForeground(attributeSet, Color.BLACK);
        }

        try {
            doc.insertString(doc.getLength(), " - " + newMessage + "\n", attributeSet);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendMessageHandle(String chatMessage) {
//        String chatMessage = chatInput.getText();
        chatInput.setText("");
        if (chatMessage != "" && Client.competitor != null) {
            Client.socketHandle.write("room-chat-send;" + chatMessage);
            setChatArea("bạn: " + chatMessage);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exitCheckBox = new javax.swing.JCheckBox();
        chatPanel = new javax.swing.JPanel();
        chatInput = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        quickChatComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        mainGamePanel = new javax.swing.JPanel();
        playingPanel = new javax.swing.JPanel();
        scissorsButton = new javax.swing.JButton();
        rockButton = new javax.swing.JButton();
        paperButton = new javax.swing.JButton();
        selectionStatusLabel = new javax.swing.JLabel();
        countdownLabel = new javax.swing.JLabel();
        setLabel = new javax.swing.JLabel();
        waitingPanel = new javax.swing.JPanel();
        waitingLabel = new javax.swing.JLabel();
        readyButton = new javax.swing.JButton();
        endingPanel = new javax.swing.JPanel();
        winnerLabel = new javax.swing.JLabel();
        countDownLabel = new javax.swing.JLabel();
        infoPanel = new javax.swing.JPanel();
        player1Avatar = new javax.swing.JLabel();
        player1Label = new javax.swing.JLabel();
        player1Points = new javax.swing.JLabel();
        roomName = new javax.swing.JLabel();
        player2Label = new javax.swing.JLabel();
        player2Points = new javax.swing.JLabel();
        player2Avatar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        countNumber = new javax.swing.JLabel();
        competitorChooseImg = new javax.swing.JLabel();
        userChooseImg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        exitCheckBox.setText("Thoát");
        exitCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitCheckBoxActionPerformed(evt);
            }
        });

        sendButton.setText("Gửi");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        quickChatComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chat nhanh", "Quá gà!", "Chào đằng ấy.", "Vui quá đi!", "May mắn thôi!", "Hãy đợi đó!" }));
        quickChatComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quickChatComboBoxActionPerformed(evt);
            }
        });

        chatArea.setEditable(false);
        jScrollPane1.setViewportView(chatArea);
        doc = chatArea.getStyledDocument();

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Trò Chuyện");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(68, 68, 68))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
        chatPanel.setLayout(chatPanelLayout);
        chatPanelLayout.setHorizontalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatPanelLayout.createSequentialGroup()
                .addGroup(chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, chatPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(quickChatComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chatInput, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        chatPanelLayout.setVerticalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chatInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quickChatComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addContainerGap())
        );

        mainGamePanel.setLayout(new java.awt.CardLayout());

        scissorsButton.setText("Kéo");
        scissorsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scissorsButtonActionPerformed(evt);
            }
        });

        rockButton.setText("Búa");
        rockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rockButtonActionPerformed(evt);
            }
        });

        paperButton.setText("Bao");
        paperButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paperButtonActionPerformed(evt);
            }
        });

        selectionStatusLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        selectionStatusLabel.setText("Hết giờ,vui lòng chờ kết quả trong giây lát!");

        countdownLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        setLabel.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        setLabel.setForeground(new java.awt.Color(0, 51, 255));
        setLabel.setText("Hiệp ?/3");

        javax.swing.GroupLayout playingPanelLayout = new javax.swing.GroupLayout(playingPanel);
        playingPanel.setLayout(playingPanelLayout);
        playingPanelLayout.setHorizontalGroup(
            playingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playingPanelLayout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(setLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countdownLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playingPanelLayout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addGroup(playingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playingPanelLayout.createSequentialGroup()
                        .addComponent(scissorsButton)
                        .addGap(26, 26, 26)
                        .addComponent(rockButton)
                        .addGap(32, 32, 32)
                        .addComponent(paperButton)
                        .addGap(95, 95, 95))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playingPanelLayout.createSequentialGroup()
                        .addComponent(selectionStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))))
        );
        playingPanelLayout.setVerticalGroup(
            playingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(playingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(countdownLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectionStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(playingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scissorsButton)
                    .addComponent(rockButton)
                    .addComponent(paperButton))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        mainGamePanel.add(playingPanel, "playing");

        waitingLabel.setText("Chờ người chơi khác vào phòng");

        readyButton.setText("Sẵn sàng");
        readyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout waitingPanelLayout = new javax.swing.GroupLayout(waitingPanel);
        waitingPanel.setLayout(waitingPanelLayout);
        waitingPanelLayout.setHorizontalGroup(
            waitingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, waitingPanelLayout.createSequentialGroup()
                .addContainerGap(129, Short.MAX_VALUE)
                .addGroup(waitingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(waitingPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(readyButton))
                    .addComponent(waitingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(140, 140, 140))
        );
        waitingPanelLayout.setVerticalGroup(
            waitingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, waitingPanelLayout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(waitingLabel)
                .addGap(18, 18, 18)
                .addComponent(readyButton)
                .addGap(48, 48, 48))
        );

        mainGamePanel.add(waitingPanel, "waiting");

        winnerLabel.setFont(new java.awt.Font("Sitka Small", 3, 18)); // NOI18N
        winnerLabel.setText("       KẾT QUẢ: ĐỐI THỦ CHIẾN THẮNG");

        countDownLabel.setBackground(new java.awt.Color(0, 153, 153));
        countDownLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        countDownLabel.setForeground(new java.awt.Color(0, 153, 153));
        countDownLabel.setText("Chờ ?s để bắt đầu ván mới");

        javax.swing.GroupLayout endingPanelLayout = new javax.swing.GroupLayout(endingPanel);
        endingPanel.setLayout(endingPanelLayout);
        endingPanelLayout.setHorizontalGroup(
            endingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(endingPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(countDownLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
            .addGroup(endingPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(winnerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        endingPanelLayout.setVerticalGroup(
            endingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(endingPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(winnerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(countDownLabel)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        mainGamePanel.add(endingPanel, "ending");

        infoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        infoPanel.add(player1Avatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 37, 60, 60));

        player1Label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        player1Label.setForeground(new java.awt.Color(0, 153, 153));
        player1Label.setText("Name 1");
        infoPanel.add(player1Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 103, 97, -1));

        player1Points.setText("Điểm: ");
        infoPanel.add(player1Points, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 125, 97, -1));

        roomName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        roomName.setText("Phòng: ??");
        infoPanel.add(roomName, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 6, -1, -1));

        player2Label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        player2Label.setForeground(new java.awt.Color(51, 0, 255));
        player2Label.setText("Name 2");
        infoPanel.add(player2Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 103, 81, -1));

        player2Points.setText("Điểm:");
        infoPanel.add(player2Points, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 125, 103, -1));
        infoPanel.add(player2Avatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 37, 60, 60));

        countNumber.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        countNumber.setText("VS");

        competitorChooseImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/gameAsset/suynghi2.png"))); // NOI18N

        userChooseImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/gameAsset/suynghi.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userChooseImg)
                .addGap(30, 30, 30)
                .addComponent(countNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(competitorChooseImg)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(userChooseImg)
                    .addComponent(competitorChooseImg)
                    .addComponent(countNumber))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        infoPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 63, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(exitCheckBox)
                    .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainGamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(mainGamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(exitCheckBox)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //mainGamePanel.setLayout(c1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void scissorsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scissorsButtonActionPerformed
        // TODO add your handling code here:
        // Tạo đối tượng ImageIcon từ đường dẫn hình ảnh
        userChooseImg.setIcon(keoIcon);
        userChose = "Kéo";
    }//GEN-LAST:event_scissorsButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
        sendMessageHandle(chatInput.getText());
    }//GEN-LAST:event_sendButtonActionPerformed

    private void quickChatComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quickChatComboBoxActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_quickChatComboBoxActionPerformed

    private void readyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readyButtonActionPerformed
        // TODO add your handling code here:
        Client.socketHandle.write("player-ready");
    }//GEN-LAST:event_readyButtonActionPerformed

    private void exitCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exitCheckBoxActionPerformed

    private void rockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rockButtonActionPerformed
        // TODO add your handling code here:
        userChooseImg.setIcon(buaIcon);
        userChose = "Búa";
    }//GEN-LAST:event_rockButtonActionPerformed

    private void paperButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paperButtonActionPerformed
        // TODO add your handling code here:
        userChooseImg.setIcon(baoIcon);
        userChose = "Bao";
    }//GEN-LAST:event_paperButtonActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GameRoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GameRoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GameRoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GameRoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new GameRoomFrm().setVisible(true);
//            }
//        });
//    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                User currentUser = new User(1, "Tên người chơi", "password", 10, 5, 2, 3, 100, "avatarPath", 1);
                boolean isPlayer1 = true; // Hoặc false, tùy vào việc người chơi là player1 hay player2
                new GameRoomFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane chatArea;
    private javax.swing.JTextField chatInput;
    private javax.swing.JPanel chatPanel;
    private javax.swing.JLabel competitorChooseImg;
    private javax.swing.JLabel countDownLabel;
    private javax.swing.JLabel countNumber;
    private javax.swing.JLabel countdownLabel;
    private javax.swing.JPanel endingPanel;
    private javax.swing.JCheckBox exitCheckBox;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainGamePanel;
    private javax.swing.JButton paperButton;
    private javax.swing.JLabel player1Avatar;
    private javax.swing.JLabel player1Label;
    private javax.swing.JLabel player1Points;
    private javax.swing.JLabel player2Avatar;
    private javax.swing.JLabel player2Label;
    private javax.swing.JLabel player2Points;
    private javax.swing.JPanel playingPanel;
    private javax.swing.JComboBox<String> quickChatComboBox;
    private javax.swing.JButton readyButton;
    private javax.swing.JButton rockButton;
    private javax.swing.JLabel roomName;
    private javax.swing.JButton scissorsButton;
    private javax.swing.JLabel selectionStatusLabel;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel setLabel;
    private javax.swing.JLabel userChooseImg;
    private javax.swing.JLabel waitingLabel;
    private javax.swing.JPanel waitingPanel;
    private javax.swing.JLabel winnerLabel;
    // End of variables declaration//GEN-END:variables
}
