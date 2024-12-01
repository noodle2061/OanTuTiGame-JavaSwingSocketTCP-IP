/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.Client;
import helper.ImageHandle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author admin
 */
public class LoginFrm extends javax.swing.JFrame {

    /**
     * Creates new form Login2
     */
    private String encodeImg;
    private int imgSize = 100;

    public LoginFrm() {
        initComponents();
//        blurBackground(oantutiLabel);
        registerPanel.setVisible(false);

//        BufferedImage image;
//        try {
//            image = ImageIO.read(new File("src/image/avatar.jpg"));
//            image = ImageHandle.resizeImage(image, imgSize, imgSize);
//            ImageIcon resizedIcon = new ImageIcon(image);
//            avatarImg.setIcon(resizedIcon);
//            System.out.println("đã chạydd");
//            registerPanel.add(avatarImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 100, 100));
//
//        } catch (IOException ex) {
//            Logger.getLogger(Login2.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void notify(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void dichuyen() {
        new Thread(() -> {
            int y = backgroundPanel.getLocation().y;
            int x = backgroundPanel.getLocation().x;

            if (x == 0) {
                registerPanel.setVisible(true);
                for (int i = 0; i <= 330; i += 5) {
                    final int newX = x + i; // Giá trị x mới

                    // Chỉ cập nhật UI bên trong SwingUtilities.invokeLater
                    SwingUtilities.invokeLater(() -> {
                        backgroundPanel.setLocation(newX, y);
                    });

//                    System.out.println(newX + " " + y);
                    // Tạm dừng luồng phụ
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoginFrm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    loginPanel.setVisible(false);
                    login_register.setText("REGISTER");
                }
            } else {
                loginPanel.setVisible(true);
                for (int i = 330; i >= 0; i -= 5) {
                    final int newX = i; // Giá trị x mới

                    // Chỉ cập nhật UI bên trong SwingUtilities.invokeLater
                    SwingUtilities.invokeLater(() -> {
                        backgroundPanel.setLocation(newX, y);
                    });

                    // Tạm dừng luồng phụ
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoginFrm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                registerPanel.setVisible(false);
                login_register.setText("LOGIN");
            }

        }).start(); // Khởi chạy luồng phụ
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        backgroundPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        login_register = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        registerPanel = new javax.swing.JPanel();
        avatarImg = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        userNameR = new javax.swing.JTextField();
        password2Value = new javax.swing.JPasswordField();
        passwordValueR = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        loginPanel = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        register = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        userName = new javax.swing.JTextField();
        passwordValue = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backgroundPanel.setBackground(new java.awt.Color(0, 204, 51));
        backgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(204, 204, 204, 180));

        jLabel2.setFont(new java.awt.Font("Sitka Text", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setText("OẲN TÙ TÌ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel2)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        backgroundPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 340, 90));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204, 180));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });

        login_register.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        login_register.setForeground(new java.awt.Color(102, 255, 102));
        login_register.setText("LOGIN");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(login_register)
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(login_register)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        backgroundPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 340, 60));

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/trau.jpg"))); // NOI18N
        backgroundPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 450));

        mainPanel.add(backgroundPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 452));

        registerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        avatarImg.setBackground(new java.awt.Color(51, 25, 255));
        avatarImg.setForeground(new java.awt.Color(153, 255, 255));
        try {
            BufferedImage image = ImageIO.read(new File("src/image/avatar.jpg"));
            image = ImageHandle.resizeImage(image, imgSize, imgSize);
            ImageIcon resizedIcon = new ImageIcon(image);
            avatarImg.setIcon(resizedIcon);
            System.out.println("đã chạy đến đây");
        } catch (Exception e) {
            System.out.print(e);
        }
        avatarImg.setOpaque(true);
        registerPanel.add(avatarImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 100, 100));

        jLabel6.setText("Nhập tên");
        registerPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jLabel7.setText("Nhập password:");
        registerPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel8.setText("Nhập lại password:");
        registerPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jButton1.setText("Chọn ảnh đại diện");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        registerPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jButton3.setText("Đăng ký");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        registerPanel.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 170, -1));

        jButton5.setText("Đăng nhập");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        registerPanel.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 170, -1));
        registerPanel.add(userNameR, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 160, -1));
        registerPanel.add(password2Value, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 160, -1));
        registerPanel.add(passwordValueR, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 160, -1));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("ĐĂNG KÝ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jLabel9)
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel9)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        registerPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 90));

        mainPanel.add(registerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 450));

        loginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setText("Đăng Nhập");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        loginPanel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 344, 175, -1));

        register.setText("Đăng ký");
        register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerMouseExited(evt);
            }
        });
        register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerActionPerformed(evt);
            }
        });
        loginPanel.add(register, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 385, 175, -1));

        jLabel4.setText("Tên đăng nhập:");
        loginPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 142, -1, -1));

        jLabel5.setText("Mật khẩu:");
        loginPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 216, -1, -1));

        userName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameActionPerformed(evt);
            }
        });
        loginPanel.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 176, 160, -1));

        passwordValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordValueActionPerformed(evt);
            }
        });
        loginPanel.add(passwordValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 150, -1));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ĐĂNG NHẬP");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(68, 68, 68))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel3)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        loginPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 300, 90));

        mainPanel.add(loginPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(336, 0, 330, 450));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerActionPerformed
        // TODO add your handling code here:
        dichuyen();
    }//GEN-LAST:event_registerActionPerformed

    private void registerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseEntered
        // TODO add your handling code here:
//        System.out.println("có con chim bành khuyên nhỏ");
    }//GEN-LAST:event_registerMouseEntered

    private void registerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseExited
        // TODO add your handling code here:
//        System.out.println("hót chi mà đau tai thế");
    }//GEN-LAST:event_registerMouseExited

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        // TODO add your handling code here:
//        jPanel4.setBackground(new java.awt.Color(204, 204, 204, 225));
//        jPanel4.repaint();
//        System.out.println("yes");
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        // TODO add your handling code here:

//        jPanel4.setBackground(new java.awt.Color(204, 204, 204, 190));
//        jPanel4.repaint();
//        System.out.println("nô");
    }//GEN-LAST:event_jPanel4MouseExited

    private void userNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userNameActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            String name = userName.getText();
            String password = String.copyValueOf(passwordValue.getPassword());
            if (name.equals("")) {
                notify("Hãy nhập tên!!");
            } else if (password.equals("")) {
                notify("Hãy nhập mật khẩu!!");
            } else {
                Client.socketHandle.write("login-request;" + name + ";" + password);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void passwordValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordValueActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        dichuyen();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh đại diện");
        String fileNameExtension;

        // Chỉ cho phép chọn file
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Bộ lọc chỉ cho phép chọn các định dạng ảnh
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Kiểm tra xem tệp được chọn có phải là ảnh hay không
            String fileName = selectedFile.getName().toLowerCase();
            System.out.println(selectedFile);

            if (fileName.endsWith(".jpg")) {
                fileNameExtension = "jpg";
            } else if (fileName.endsWith(".png")) {
                fileNameExtension = "png";
            } else if (fileName.endsWith(".jpeg")) {
                fileNameExtension = "jpeg";
            } else {
                notify("Tệp được chọn không phải là định dạng ảnh hợp lệ.\n Hãy chọn ảnh có định dạng jpg, png, jpeg");
                return;
            }

            // Kiểm tra kích thước tệp (ví dụ: không quá 2MB)
            long fileSizeInMB = selectedFile.length() / (1024 * 1024);
            if (fileSizeInMB > 2) {
                notify("Kích thước tệp quá lớn. Vui lòng chọn tệp nhỏ hơn 2MB.");
                return;
            }

            // Nếu tệp đạt yêu cầu, tiến hành chỉnh kích thước ảnh
            try {
                BufferedImage originalImage = ImageIO.read(selectedFile);
//                encodeImg = ImageHandle.imageToString(originalImage, fileNameExtension);

                encodeImg = ImageHandle.resizeImageAndConvertToString(originalImage, 200, 200, fileNameExtension);
                System.out.println(encodeImg);
                // Tạo ImageIcon từ ảnh đã chỉnh kích thước
                originalImage = ImageHandle.resizeImage(originalImage, imgSize, imgSize);
                ImageIcon icon = new ImageIcon(originalImage);
                // Đặt icon đã chỉnh kích thước làm icon cho JTextField có tên "avatarImg"
                avatarImg.setIcon(icon);
                System.out.println("đã gán avt vào");

            } catch (IOException ex) {
                notify("Đã xảy ra lỗi khi tải ảnh.");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String name = userNameR.getText();
        String pass = String.copyValueOf(passwordValueR.getPassword());
        String pass2 = String.copyValueOf(password2Value.getPassword());

        if (!pass.equals(pass2)) {
            notify("Mật khẩu nhập không khớp!");
            return;
        }
        if (encodeImg == null) {
            try {
                BufferedImage image = ImageIO.read(new File("src/image/avatar.jpg"));
                encodeImg = ImageHandle.imageToString(image, "jpg");
            } catch (IOException ex) {
                Logger.getLogger(RegisterFrm.class.getName()).log(Level.SEVERE, null, ex);
            }
//            encodeImg = ImageHandle.imageToString(resizedIcon.getImage(), "jpg");
        }
        if (name.equals("")) {
            notify("Hãy nhập tên vào!!!");
        } else if (pass.equals("")) {
            notify("Hãy nhập mật khẩu vào!!!");
        } else {
//            System.out.println(encodeImg.length());
            Client.socketHandle.write("register-request;" + name + ";" + pass + ";" + encodeImg);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(LoginFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avatarImg;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel login_register;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPasswordField password2Value;
    private javax.swing.JPasswordField passwordValue;
    private javax.swing.JPasswordField passwordValueR;
    private javax.swing.JButton register;
    private javax.swing.JPanel registerPanel;
    private javax.swing.JTextField userName;
    private javax.swing.JTextField userNameR;
    // End of variables declaration//GEN-END:variables
}
