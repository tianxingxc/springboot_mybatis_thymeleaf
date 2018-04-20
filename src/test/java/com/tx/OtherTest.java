package com.tx;

import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class OtherTest {

    @Test
    public void test() {

        FileSystemView fsv = FileSystemView.getFileSystemView();

        File[] files = File.listRoots();

        String userHome = System.getProperty("user.home");
        System.out.println(userHome);
        for(File file : files) {
            System.out.println(fsv.getSystemDisplayName(file));
            System.out.println(file.getFreeSpace() / (1024 * 1024.0));
            System.out.println(file.getAbsolutePath());
        }
    }
}
