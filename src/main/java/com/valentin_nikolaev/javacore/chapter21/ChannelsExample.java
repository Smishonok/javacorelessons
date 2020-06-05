package com.valentin_nikolaev.javacore.chapter21;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ChannelsExample {

    public static void main(String[] args) {

        Path appRootPath = Paths.get(ClassLoader.getSystemResource("").getPath()
                                                .replaceFirst("/", ""));
        Path NIOResourcesDirectory = appRootPath.resolve("NIOResources");
        Path fileForWritingFromChannel = NIOResourcesDirectory.resolve(
                "fileForWritingFromChannel.txt");

        String textForWriting = "This is the text for writing in the file using the file channel." +
                " Firstly, the text will be transformed into a byte`s array and then writes into the file.";

        byte       byteArray[] = textForWriting.getBytes();
        ByteBuffer buffer      = ByteBuffer.wrap(byteArray);
        buffer.rewind();

        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(fileForWritingFromChannel,
                                                                          StandardOpenOption.CREATE,
                                                                          StandardOpenOption.WRITE,
                                                                          StandardOpenOption.READ)) {
            fileChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String textFromFile = "";
//        try (SeekableByteChannel channel = Files.newByteChannel(fileForWritingFromChannel,
//                                                                StandardOpenOption.READ)) {
//            if (channel.read(buffer) != - 1) {
//                buffer.rewind();
//                textFromFile += new String(buffer.array());
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try (FileChannel channel = (FileChannel) Files.newByteChannel(fileForWritingFromChannel,
                                                        StandardOpenOption.READ)) {
            long fileSize = channel.size();
            MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);

            mappedBuffer.flip();
            System.out.println(mappedBuffer.hasArray());
            textFromFile += new String(mappedBuffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(textFromFile);
    }
}
