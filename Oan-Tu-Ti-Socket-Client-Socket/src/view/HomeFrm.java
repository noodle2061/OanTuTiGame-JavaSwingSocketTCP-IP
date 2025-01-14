/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.Client;
import helper.ImageHandle;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author vankh
 */
public class HomeFrm extends javax.swing.JFrame {

    /**
     * Creates new form HomeFrm
     */
    private int avtSize = 50;
    private StyledDocument doc;

    public HomeFrm() {
        initComponents();
        // thêm avatar vào
        Image avtImage = ImageHandle.StringToImage(Client.user.getAvatar());
        avtImage = ImageHandle.resizeImage(avtImage, avtSize);
        avtImage = ImageHandle.cropImageToCircle(avtImage);
        avatarContainer.setIcon(new ImageIcon(avtImage));
        // userName.setText(Client.user.getName());
        userNameClick.setText(Client.user.getName());
        userPoints.setText(Integer.toString(Client.user.getPoints()));

    }

    public void notify(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addMessage(String message) {

//        chatMessage.setText(message);
    }

    public void updateChatArea() {
        chatMessage.setText("");
        for (String chat : Client.globalChatList) {
            SimpleAttributeSet attributeSet = new SimpleAttributeSet();
            if (chat.startsWith("Bạn")) {
                StyleConstants.setForeground(attributeSet, Color.BLUE);
            } else if (chat.startsWith("Server")) {
                StyleConstants.setForeground(attributeSet, Color.RED);
            } else if(chat.startsWith("Thông báo hệ thống")) {
                StyleConstants.setForeground(attributeSet, Color.ORANGE);
            }else {
                StyleConstants.setForeground(attributeSet, Color.BLACK);
            }
            try {
                doc.insertString(doc.getLength(), " - " + chat + "\n", attributeSet);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
//        Client.globalChatList.clear();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        avatarContainer = new javax.swing.JLabel();
        logOutBtn = new javax.swing.JButton();
        messageTxt = new javax.swing.JTextField();
        sendMsgBtn = new javax.swing.JToggleButton();
        chooseRoomMatchBtn = new javax.swing.JButton();
        Rules = new javax.swing.JButton();
        userPoints = new javax.swing.JLabel();
        userNameClick = new javax.swing.JButton();
        history = new javax.swing.JButton();
        playNow = new javax.swing.JButton();
        rankUser = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        chatMessage = new javax.swing.JTextPane();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(avatarContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 50, 50));

        logOutBtn.setText("Đăng xuất");
        logOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBtnActionPerformed(evt);
            }
        });
        getContentPane().add(logOutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));

        messageTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageTxtActionPerformed(evt);
            }
        });
        getContentPane().add(messageTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 450, 189, -1));

        sendMsgBtn.setText("Gửi");
        sendMsgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMsgBtnActionPerformed(evt);
            }
        });
        getContentPane().add(sendMsgBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 450, -1, -1));

        chooseRoomMatchBtn.setText("Chọn phòng");
        chooseRoomMatchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseRoomMatchBtnActionPerformed(evt);
            }
        });
        getContentPane().add(chooseRoomMatchBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 250, -1));

        Rules.setText("Luật chơi");
        Rules.setMaximumSize(new java.awt.Dimension(95, 23));
        Rules.setMinimumSize(new java.awt.Dimension(95, 23));
        Rules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RulesActionPerformed(evt);
            }
        });
        getContentPane().add(Rules, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 250, -1));

        userPoints.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        userPoints.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userPoints.setText("điểm");
        userPoints.setToolTipText("");
        userPoints.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        userPoints.setName("userPoints"); // NOI18N
        getContentPane().add(userPoints, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));
        userPoints.getAccessibleContext().setAccessibleName("point");

        userNameClick.setBackground(new java.awt.Color(242, 242, 242));
        userNameClick.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        userNameClick.setText("name");
        userNameClick.setBorder(null);
        userNameClick.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        userNameClick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameClickActionPerformed(evt);
            }
        });
        getContentPane().add(userNameClick, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 152, -1));
        userNameClick.getAccessibleContext().setAccessibleName("nameClick");

        history.setText("Lịch sử đấu");
        history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyActionPerformed(evt);
            }
        });
        getContentPane().add(history, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 250, -1));

        playNow.setText("Chơi ngay");
        playNow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playNowActionPerformed(evt);
            }
        });
        getContentPane().add(playNow, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 250, -1));

        rankUser.setText("Bảng xếp hạng");
        rankUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rankUserActionPerformed(evt);
            }
        });
        getContentPane().add(rankUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 250, -1));

        jLabel1.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        jLabel1.setText("global chat");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, -1, -1));

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("home");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 120));
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 500, 10, 10));

        jScrollPane3.setViewportView(chatMessage);
        doc = chatMessage.getStyledDocument();

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 182, 250, 250));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyActionPerformed
        // TODO add your handling code here:
        Client.CloseView(Client.View.HOME);
        Client.OpenView(Client.View.MATCH_HISTORY);
        Client.socketHandle.write("get-history-request;" + Client.user.getId());
    }//GEN-LAST:event_historyActionPerformed

    private void playNowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playNowActionPerformed
        // TODO add your handling code here:
//        Client.CloseView(Client.View.HOME);
//        Client.OpenView(Client.View.GAME_ROOM);
        // Gửi yêu cầu 'Chơi ngay' tới Server
        Client.socketHandle.write("play-now-request;" + Client.user.getId());
        System.out.println("chạy đến đây");
    }//GEN-LAST:event_playNowActionPerformed

    private void rankUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rankUserActionPerformed
        // TODO add your handling code here:
        Client.CloseView(Client.View.HOME);
        Client.OpenView(Client.View.RANK);
        Client.socketHandle.write("get-ranking-request");
    }//GEN-LAST:event_rankUserActionPerformed

    private void logOutBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_logOutBtnActionPerformed
        // TODO add your handling code here:
        Client.socketHandle.write("log-out-request");
    }// GEN-LAST:event_logOutBtnActionPerformed

    //private void changePasswordBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_changePasswordBtnActionPerformed
    // TODO add your handling code here:
    //    Client.CloseView(Client.View.HOME);
    //    Client.OpenView(Client.View.CHANGE_PASSSWORD);
    // }// GEN-LAST:event_changePasswordBtnActionPerformed
    //private void deleteAccBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteAccBtnActionPerformed
    // TODO add your handling code here:
    //    Client.CloseView(Client.View.HOME);
    //    Client.OpenView(Client.View.DELETE_ACCOUNT);
    // }// GEN-LAST:event_deleteAccBtnActionPerformed
    private void messageTxtActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_messageTxtActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_messageTxtActionPerformed

    private void chooseRoomMatchBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chooseRoomMatchBtnActionPerformed
        // TODO add your handling code here:
        Client.socketHandle.write("get-list-room;1");
    }// GEN-LAST:event_chooseRoomMatchBtnActionPerformed

    private void sendMsgBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sendMsgBtnActionPerformed
        // TODO add your handling code here:
        String msgToSend = messageTxt.getText();
        Client.socketHandle.write("boardcast-request;" + msgToSend);
        Client.globalChatString += "Tôi: " + msgToSend + "\n";
        Client.globalChatList.add("Bạn: " + msgToSend);
        updateChatArea();
        messageTxt.setText("");
    }// GEN-LAST:event_sendMsgBtnActionPerformed

    private void RulesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_RulesActionPerformed
        // TODO add your handling code here:
        Client.OpenView(Client.View.RULESGAME);
        Client.CloseView(Client.View.HOME);
    }// GEN-LAST:event_RulesActionPerformed

    private void userNameClickActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_userNameClickActionPerformed
        // TODO add your handling code here:
        Client.OpenView(Client.View.DETAILS);
        Client.CloseView(Client.View.HOME);
    }// GEN-LAST:event_userNameClickActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Rules;
    private javax.swing.JLabel avatarContainer;
    private javax.swing.JTextPane chatMessage;
    private javax.swing.JButton chooseRoomMatchBtn;
    private javax.swing.JButton history;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton logOutBtn;
    private javax.swing.JTextField messageTxt;
    private javax.swing.JButton playNow;
    private javax.swing.JButton rankUser;
    private javax.swing.JToggleButton sendMsgBtn;
    private javax.swing.JButton userNameClick;
    private javax.swing.JLabel userPoints;
    // End of variables declaration//GEN-END:variables

}
