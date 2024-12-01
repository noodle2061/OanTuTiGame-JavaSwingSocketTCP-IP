/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.Server;
import controller.ServerThread;
import dal.UserDAO;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.User;

/**
 *
 * @author vankh
 */
public class MainFrm extends javax.swing.JFrame {

    /**
     * Creates new form main
     */
    public MainFrm() {
        initComponents();
//        addWindowCloseListener(this);
    }

    public void updateTable() {
        System.out.println("đã chạy đến đây");
        String[] onlineUsers = ServerThread.udb.getOnlineUsers().split(";");
        int size = onlineUsers.length;
        System.out.println(size);
        DefaultTableModel tableModel = (DefaultTableModel) onlineUserTable.getModel();
        tableModel.setRowCount(0);
        
        if (size == 0 || onlineUsers[0].equals("")) {
            System.out.println("ko có ai online");
            return;
        } else {
            System.out.println("người onl là: " + onlineUsers[0]);
        }
        
        // Thêm các dòng dữ liệu vào bảng
        for (int i = 0; i < size; i++) {
            String[] userData = onlineUsers[i].split((","));
            // Kiểm tra để tránh lỗi ArrayIndexOutOfBounds
            tableModel.addRow(new Object[]{userData[0], (userData[1].equals("1") ? "Online" : "Playing")});
        }

        // Điều chỉnh kích thước bảng cho phù hợp với số hàng
        onlineUserTable.setPreferredScrollableViewportSize(onlineUserTable.getPreferredSize());
        // điều chỉnh dữ liệu nằm giữa
        centerTableData();
    }

    private void centerTableData() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        for (int i = 0; i < onlineUserTable.getColumnCount(); i++) {
            onlineUserTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public void setNumberOfClient(int num) {
        numberOfClient.setText(String.valueOf(num));
    }

    public void addMessage(String message) {
        messageArea.setText(messageArea.getText() +" - " +  message + "\n");
    }

    public static void addWindowCloseListener(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Ngăn việc đóng cửa sổ ngay lập tức
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Hiện hộp thoại để người dùng chọn Yes hoặc No
                int result = JOptionPane.showConfirmDialog(frame,
                        "Tắt Server rất nguy hiểm. Bạn có muốn tắt server không?", // Câu hỏi
                        "Xác nhận", // Tiêu đề hộp thoại
                        JOptionPane.YES_NO_OPTION, // Tùy chọn YES và NO
                        JOptionPane.QUESTION_MESSAGE); // Kiểu thông báo

                // Lấy kết quả của người dùng
                if (result == JOptionPane.YES_OPTION) {
                    Server.threadBus.boardCast(0, "Máy chủ dừng hoạt động, vui lòng thoát ứng dụng.");
                    // tắt trạng thái hoạt động của toàn bộ người chơi
                    UserDAO udb = new UserDAO();
                    for (ServerThread s : Server.threadBus.getListSocket()) {
                        User u = s.getUser();
                        u.setGameStatus(0);
                        udb.updateUser(u);
                    }

                    frame.dispose();
                    System.exit(0);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chatPanel = new javax.swing.JPanel();
        messageTxt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        sendMessage = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        numberOfClient = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        onlineUserTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        chatPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        chatPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        messageTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageTxtActionPerformed(evt);
            }
        });
        chatPanel.add(messageTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 230, 30));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel2.setText("CHAT TOÀN SERVER");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(39, 39, 39))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        chatPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 340, 90));

        sendMessage.setText("Gửi");
        sendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageActionPerformed(evt);
            }
        });
        chatPanel.add(sendMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 100, -1));

        messageArea.setEditable(false);
        messageArea.setColumns(20);
        messageArea.setRows(5);
        jScrollPane1.setViewportView(messageArea);

        chatPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 340, 180));

        getContentPane().add(chatPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 360, 350));

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("THÔNG TIN QUẢN LÝ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(jLabel1)
                .addContainerGap(155, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 120));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Số lượng máy đang kết nối");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        numberOfClient.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        numberOfClient.setText("0");
        jPanel3.add(numberOfClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 260, 90));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel4.setText("Danh sách người chơi online");

        onlineUserTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "Đang chơi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        onlineUserTable.setShowGrid(true);
        jScrollPane2.setViewportView(onlineUserTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(22, 22, 22))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, 260, 230));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 480, 50, 10));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void messageTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_messageTxtActionPerformed

    private void sendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageActionPerformed
        // TODO add your handling code here:
        String msg = messageTxt.getText();
        if (msg != "") {
            Server.threadBus.boardCast(0, "Server: " + msg);
            addMessage(msg);
            messageTxt.setText("");
        }
    }//GEN-LAST:event_sendMessageActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chatPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea messageArea;
    private javax.swing.JTextField messageTxt;
    private javax.swing.JLabel numberOfClient;
    private javax.swing.JTable onlineUserTable;
    private javax.swing.JToggleButton sendMessage;
    // End of variables declaration//GEN-END:variables
}
