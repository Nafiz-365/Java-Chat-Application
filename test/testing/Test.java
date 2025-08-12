package testing;

import com.nafiz.swing.blurHash.BlurHash;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Test{

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("E:\\Java-Chat-Application\\test\\testing\\to-olivia.jpg"));
            String blurhashStr = BlurHash.encode(image);
            System.out.println(blurhashStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
