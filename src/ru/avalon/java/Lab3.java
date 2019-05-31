package ru.avalon.java;

import ru.avalon.java.actions.FileContentAction;
import ru.avalon.java.actions.FileCopyAction;
import ru.avalon.java.actions.FileDeleteAction;
import ru.avalon.java.actions.FileMoveAction;
import ru.avalon.java.console.ConsoleUI;

import java.io.*;


/**
 * Лабораторная работа №3
 * <p>
 * Курс: "Программирование на платформе Java. Разработка
 * многоуровневых приложений"
 * <p>
 * Тема: "Потоки исполнения (Threads) и многозадачность" 
 *
 * @author Daniel Alpatov <danial.alpatov@gmail.com>
 */
public class Lab3 extends ConsoleUI<Commands> {

    public final static Object monitor = new Object();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String originFileName, targetDirectory;

    /**
     * Точка входа в приложение.
     * 
     * @param args 
     */
    public static void main(String[] args) {

        System.out.println("Enter command `help` to see other commands..");
        new Lab3().run();
    }
    /**
     * Конструктор класса.
     * <p>
     * Инициализирует экземпляр базового типа с использоавнием
     * перечисления {@link Commands}.
     */
    Lab3() {
        super(Commands.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCommand(Commands command) throws IOException {
        try {
            synchronized (monitor) {
                switch (command) {
                    case copy:
                        /*
                         * TODO №6 Обработайте команду copy
                         */
                        System.out.println("Enter path to source file to copy");
                        originFileName = reader.readLine();
                        System.out.println("Enter path to target folder");
                        targetDirectory = reader.readLine();

                        FileCopyAction copy = new FileCopyAction(originFileName, targetDirectory);
                        copy.start();
                        monitor.wait();
                        break;
                    case move:
                        /*
                         * TODO №7 Обработайте команду move
                         */
                        System.out.println("Enter path to source file to move");
                        originFileName = reader.readLine();
                        System.out.println("Enter path to target folder");
                        targetDirectory = reader.readLine();

                        FileMoveAction move = new FileMoveAction(originFileName, targetDirectory);
                        move.start();
                        monitor.wait();
                        break;
                    /**
                     * Добавил команду delete
                     */
                    case delete:
                        System.out.println("Enter path to source file to delete");
                        originFileName = reader.readLine();

                        FileDeleteAction delete = new FileDeleteAction(originFileName);
                        delete.start();
                        monitor.wait();
                        break;
                    /**
                     * Добавил команду `content` для вывода списка файлов в директории
                     */
                    case content:
                        System.out.println("Enter path to source file to look content");
                        originFileName = reader.readLine();

                        FileContentAction ls = new FileContentAction(originFileName);
                        ls.start();
                        monitor.wait();
                        break;
                    /**
                     * Добавил конмаду `help` для вывода всех команд
                     */
                    case help:
                        System.out.println("copy \t\t- copies file to a specified directory with the same name,\n" +
                                "\t\t\t  the original file is SAVED in source directory\n");
                        System.out.println("move \t\t- copies file to a specified directory with the same name,\n" +
                                "\t\t\t  the original file is DELETED in source directory\n");
                        System.out.println("delete \t\t- removes file from directory\n");
                        System.out.println("content \t- shows all files & directories in a specified directory");
                        System.out.println("exit \t\t- closes the application");
                        break;
                    case exit:
                        close();
                        break;
                    /*
                     * TODO №9 Обработайте необработанные команды
                     */
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
