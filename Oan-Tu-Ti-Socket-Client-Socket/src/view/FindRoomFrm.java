/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.Client;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vankh
 */
public class FindRoomFrm extends javax.swing.JFrame {

    /**
     * Creates new form FindRoomFrm
     */
    private String tenPhong, soNguoi;
    private int stt = 1;
    boolean isActionPerformed = false;

    public FindRoomFrm() {
        initComponents();

        // Thêm ActionListener cho nút "Vào phòng"
        enterRoomBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openRoom();  // Gọi phương thức openRoom() khi nhấn nút
            }
        });
    }

    public void notify(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // Biến để lưu chỉ số hàng đã được chọn lần trước
    private int lastSelectedRow = -1;

    public void updateTable(String[] lst) {
        idTableTxt.setText(stt + "/20");
        DefaultTableModel model = (DefaultTableModel) lstRoomTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        int size = lst.length;

        if (size < 2) { // Kiểm tra nếu không có đủ dữ liệu
            roomChoseTxt.setText("Không có phòng nào hợp lệ.");
            return;
        }
        System.out.println(size);

        // Thêm các dòng dữ liệu vào bảng
        for (int i = 1; i <= size; i += 2) {
            // Kiểm tra để tránh lỗi ArrayIndexOutOfBounds
            if (i + 2 <= size) {
                model.addRow(new Object[]{"Phòng " + lst[i], lst[i + 1]});
            }
        }

        // Điều chỉnh kích thước bảng cho phù hợp với số hàng
        lstRoomTable.setPreferredScrollableViewportSize(lstRoomTable.getPreferredSize());

        // Lắng nghe sự kiện chọn hàng để lấy dữ liệu khi người dùng nhấp vào
        ListSelectionModel selectionModel = lstRoomTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = lstRoomTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Lấy dữ liệu từ hàng đã chọn
                        tenPhong = (String) lstRoomTable.getValueAt(selectedRow, 0);
                        soNguoi = (String) lstRoomTable.getValueAt(selectedRow, 1);
                        roomChoseTxt.setText("Bạn vừa chọn phòng " + tenPhong);
                    }
                }
            }
        });

        // Lắng nghe sự kiện double click vào hàng của bảng
        lstRoomTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !isActionPerformed) { // Kiểm tra nếu là double click và chưa thực hiện thao tác
                    isActionPerformed = true; // Đánh dấu là thao tác đã được thực hiện
                    int selectedRow = lstRoomTable.getSelectedRow();
                    if (selectedRow != -1) {
                        tenPhong = (String) lstRoomTable.getValueAt(selectedRow, 0);
                        soNguoi = (String) lstRoomTable.getValueAt(selectedRow, 1);
                        openRoom();
                    }
                }
            }
        });

        // Đặt lại cờ khi người dùng di chuyển chuột ra khỏi bảng (hoặc thực hiện các thao tác khác)
        lstRoomTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Reset flag khi người dùng bắt đầu thao tác khác
                isActionPerformed = false;
            }
        });
        centerTableData();
    }

    private void centerTableData() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        for (int i = 0; i < lstRoomTable.getColumnCount(); i++) {
            lstRoomTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void openRoom() {
        if (tenPhong == null) {
            notify("Hãy chọn một phòng.");
            return;
        }
        if (Integer.parseInt(soNguoi) == 2) {
            notify("Phòng đã đầy, vui lòng chọn phòng khác.");
            return;
        }

        // Gửi yêu cầu tham gia phòng
        int roomId = Integer.parseInt(tenPhong.split(" ")[1]);  // `tenPhong` cần là ID của phòng đã chọn
        int playerId = Client.user.getId();       // Lấy ID người chơi hiện tại từ `Client.user`

        // Gọi hàm sendJoinRoomRequest() trong ClientSocketHandle
        Client.socketHandle.sendJoinRoomRequest(roomId, playerId);
        System.out.println("Đang gửi yêu cầu vào phòng " + tenPhong + " với ID người chơi: " + playerId);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstRoomTable = new javax.swing.JTable();
        nextBtn = new javax.swing.JToggleButton();
        preBtn = new javax.swing.JToggleButton();
        idTableTxt = new javax.swing.JLabel();
        roomChoseTxt = new javax.swing.JLabel();
        enterRoomBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lstRoomTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Phòng", "Số người chơi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(lstRoomTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 113, 288, 100));

        nextBtn.setText("Next");
        nextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBtnActionPerformed(evt);
            }
        });
        getContentPane().add(nextBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 319, -1, -1));

        preBtn.setText("Pre");
        preBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preBtnActionPerformed(evt);
            }
        });
        getContentPane().add(preBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 319, -1, -1));

        idTableTxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        idTableTxt.setText("1/20");
        getContentPane().add(idTableTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 322, -1, -1));

        roomChoseTxt.setText("Bạn chưa chọn phòng nào.");
        getContentPane().add(roomChoseTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 274, -1, -1));

        enterRoomBtn.setText("Vào phòng");
        enterRoomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterRoomBtnActionPerformed(evt);
            }
        });
        getContentPane().add(enterRoomBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 374, -1, -1));

        jButton1.setText("Quay lại");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 374, -1, -1));

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Find room");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 80));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enterRoomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterRoomBtnActionPerformed
        // TODO add your handling code here:
        openRoom();
    }//GEN-LAST:event_enterRoomBtnActionPerformed

    private void preBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preBtnActionPerformed
        // TODO add your handling code here:
        if (stt == 1) {
            return;
        }
        stt -= 1;
        Client.socketHandle.write("get-list-room;" + stt);

    }//GEN-LAST:event_preBtnActionPerformed

    private void nextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBtnActionPerformed
        // TODO add your handling code here:
        if (stt == 20) {
            return;
        }
        stt += 1;
        Client.socketHandle.write("get-list-room;" + stt);
    }//GEN-LAST:event_nextBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Client.CloseView(Client.View.FIND_ROOM);
        Client.OpenView(Client.View.HOME);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(FindRoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FindRoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FindRoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FindRoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FindRoomFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton enterRoomBtn;
    private javax.swing.JLabel idTableTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable lstRoomTable;
    private javax.swing.JToggleButton nextBtn;
    private javax.swing.JToggleButton preBtn;
    private javax.swing.JLabel roomChoseTxt;
    // End of variables declaration//GEN-END:variables
}
