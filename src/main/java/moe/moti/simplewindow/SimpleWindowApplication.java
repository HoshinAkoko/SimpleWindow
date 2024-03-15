package moe.moti.simplewindow;

import moe.moti.simplewindow.service.SwingDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class SimpleWindowApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        SpringApplication.run(SimpleWindowApplication.class, args);
        SwingUtilities.invokeLater(SwingDemo::new);
    }

}
