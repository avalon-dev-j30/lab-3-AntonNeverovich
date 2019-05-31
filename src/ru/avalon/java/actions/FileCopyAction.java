package ru.avalon.java.actions;


import ru.avalon.java.Lab3;

import java.io.*;
import java.nio.file.*;

/**
 * Действие, которое копирует файлы в пределах дискового
 * пространства.
 */
public class FileCopyAction implements Action {

    private String destinationFileName;
    private Path originPath, destinationPath;

    public FileCopyAction(String originFileName, String targetDirectoryName) {
        String[] splitingName = originFileName.split("/");
        this.destinationFileName = splitingName[splitingName.length - 1];
        originPath = Paths.get(originFileName);
        destinationPath = Paths.get(targetDirectoryName + "/" + destinationFileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        /*
         * TODO №2 Реализуйте метод run класса FileCopyAction
         */
        synchronized (Lab3.monitor) {
            try {
                copy();
            } catch (IOException e) {
                System.out.println("File haven't been copied..");
                e.printStackTrace();
            }
           Lab3.monitor.notifyAll();
        }
    }

    private void copy() throws IOException {

        if (!Files.exists(originPath)) {
            System.out.println("File not found!..");
        } else {
            Files.copy(originPath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File have been copied!..");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws Exception {
        /*
         * TODO №3 Реализуйте метод close класса FileCopyAction
         */
        //throw new UnsupportedOperationException("Not implemented yet!");
    }
}
