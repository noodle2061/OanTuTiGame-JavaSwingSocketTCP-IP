/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

/**
 *
 * @author admin
 */
public class ImageHandle {

    public static String resizeImageAndConvertToString(BufferedImage originalImage, int targetWidth, int targetHeight, String format) {
    try {
        // Tạo một đối tượng BufferedImage mới với kích thước mong muốn
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        
        // Vẽ ảnh gốc lên ảnh mới đã thay đổi kích thước
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        // Chuyển đổi BufferedImage thành byte array và sau đó mã hóa Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, format, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
        
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}


    
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        // Tạo đối tượng BufferedImage mới với kích thước mong muốn
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        
        // Sử dụng Graphics2D để vẽ lại ảnh với kích thước mới
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose(); // Dọn dẹp tài nguyên

        return resizedImage;
    }
    
    public static String imageToString(BufferedImage image, String format) {
        // Tạo output stream để chuyển BufferedImage thành mảng byte
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // Ghi BufferedImage vào output stream dưới dạng byte
            ImageIO.write(image, format, baos);
            byte[] imageBytes = baos.toByteArray();

            // Mã hóa mảng byte thành chuỗi Base64
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Image StringToImage(String base64String) {
        try {
            // Giải mã chuỗi Base64 thành mảng byte
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);

            // Đọc mảng byte thành BufferedImage
            ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
            BufferedImage bufferedImage = ImageIO.read(bais);


            // Chuyển BufferedImage thành Image
            return bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight(), Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image cropImageToCircle(Image img) {
        // Chuyển Image thành BufferedImage để có thể thao tác
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Tạo đối tượng Graphics2D để vẽ ảnh lên BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        // Tạo một BufferedImage mới với kênh alpha (ARGB) để chứa ảnh cắt hình tròn
        BufferedImage circleBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Tạo đối tượng Graphics2D để vẽ hình tròn và cắt ảnh
        Graphics2D g2 = circleBufferedImage.createGraphics();

        // Tạo hình tròn với kích thước dựa trên chiều rộng và chiều cao của ảnh
        g2.setClip(new Ellipse2D.Float(0, 0, width, height));

        // Vẽ ảnh lên vùng hình tròn
        g2.drawImage(bufferedImage, 0, 0, null);
        g2.dispose();

        // Trả về ảnh đã cắt hình tròn
        return circleBufferedImage;
    }

    public static Image resizeImage(Image image, int size) {
        // Kiểm tra nếu ảnh là null
        if (image == null) {
            return null;
        }

        // Lấy chiều rộng và chiều cao gốc của ảnh
        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);

        // Kiểm tra nếu không lấy được kích thước của ảnh
        if (originalWidth == -1 || originalHeight == -1) {
            return null;
        }

        // Tạo BufferedImage mới với kích thước mong muốn
        BufferedImage resizedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        // Tạo đối tượng Graphics2D để vẽ ảnh với kích thước mới
        Graphics2D g2d = resizedImage.createGraphics();

        // Vẽ ảnh với kích thước mới (giữ nguyên tỉ lệ, có thể thay đổi phương thức SCALE_SMOOTH tùy theo yêu cầu)
        g2d.drawImage(image.getScaledInstance(size, size, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose(); // Dọn dẹp tài nguyên Graphics2D

        // Trả về ảnh đã thay đổi kích thước
        return resizedImage;
    }
}
