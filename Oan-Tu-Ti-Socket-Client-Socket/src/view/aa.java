import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;

public class aa extends JComponent {
    private BufferedImage bufferedImage; // Ảnh được xử lý
    private Image originalImage;         // Ảnh gốc

    public aa(String imagePath) {
        // Load ảnh từ đường dẫn
        originalImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        createBlurredImage();
    }

    private void createBlurredImage() {
        // Xử lý tạo ảnh mờ từ ảnh gốc
        if (originalImage != null) {
            int width = getWidth();
            int height = getHeight();

            // Nếu chưa khởi tạo hoặc kích thước thay đổi
            if (width > 0 && height > 0) {
                bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                Graphics2D g2 = bufferedImage.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setComposite(AlphaComposite.SrcOver.derive(0.5f)); // Đặt độ mờ 50%
                g2.drawImage(originalImage, 0, 0, width, height, null); // Vẽ ảnh lên bufferedImage
                g2.dispose();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ ảnh đã làm mờ nếu có
        if (bufferedImage != null) {
            g.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

    public static void main(String[] args) {
        // Tạo giao diện mẫu
        JFrame frame = new JFrame("Blurred Background Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Tạo component với ảnh làm mờ
        aa blurredBackground = new aa("/image/avatar.jpg"); // Đường dẫn ảnh
        blurredBackground.setLayout(new BorderLayout());

        // Thêm nội dung lên giao diện
        JLabel label = new JLabel("Blurred Background Example", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        label.setForeground(Color.WHITE);

        blurredBackground.add(label);

        frame.setContentPane(blurredBackground);
        frame.setVisible(true);
    }
}
