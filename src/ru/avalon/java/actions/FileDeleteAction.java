package ru.avalon.java.actions;

import ru.avalon.java.Lab3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDeleteAction implements Action {

    Path originPath;

    public FileDeleteAction(String originFileName) {
        this.originPath = Paths.get(originFileName);
    }

    @Override
    public void run() {
        synchronized (Lab3.monitor) {
            try {
                delete();
            } catch (IOException e) {
                System.out.println("File haven't been deleted..");
                e.printStackTrace();
            }
            Lab3.monitor.notifyAll();
        }
    }

    private void delete() throws IOException {

        if (!Files.exists(originPath)) {
            System.out.println("File not found!..");
        } else {
            Files.delete(originPath);
            System.out.println("File have been deleted!..");
        }
    }

    @Override
    public void close() throws Exception {

    }
}
