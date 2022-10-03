package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileReader {
    private final Profile profile = new Profile();
    public Profile getDataFromFile(File file) {
        Path path = Paths.get(file.getAbsolutePath());
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(String.valueOf(path),"r");
            FileChannel inChannel = randomAccessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            StringBuilder k = new StringBuilder();
            while (0 < (inChannel.read(byteBuffer))) {
                byteBuffer.flip();
                for (int i = 0; i < byteBuffer.limit(); i++) {
                    k.append((char) byteBuffer.get());
                }
                byteBuffer.clear();
            }
            String[] profileList = k.toString().split("[ ,\\n]");
            profile.setName(profileList[1]);
            profile.setAge(Integer.valueOf(profileList[3]));
            profile.setEmail(profileList[5]);
            profile.setPhone(Long.valueOf(profileList[7]));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (randomAccessFile != null){
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return profile;
    }
}
