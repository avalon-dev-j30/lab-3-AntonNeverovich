package ru.avalon.java.actions;

import ru.avalon.java.Lab3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Действие, которое перемещает файлы в пределах дискового
 * пространства.
 */
public class FileMoveAction implements Action {

    private String destinationFileName;
    private Path originPath, destinationPath;

    public FileMoveAction(String originFileName, String targetDirectoryName) {
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
         * TODO №4 Реализуйте метод run класса FileMoveAction
         */
        synchronized (Lab3.monitor) {
            try {
                move();
            } catch (IOException e) {
                System.out.println("File haven't been moved..");
                e.printStackTrace();
            }
            Lab3.monitor.notifyAll();
        }
    }


    private void move() throws IOException {

        if (!Files.exists(originPath)) {
            System.out.println("File not found!..");
        } else {
            Files.move(originPath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File have been moved!..");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws Exception {
        /*
         * TODO №5 Реализуйте метод close класса FileMoveAction
         */
        //throw new UnsupportedOperationException("Not implemented yet!");
    }

}
