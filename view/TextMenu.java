package view;

import view.command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    public void addCommand(Command c) {
        this.commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command c : this.commands.values()) {
            String line = String.format("%4s: %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu();
                System.out.println("Input the option: ");
                String key = scanner.nextLine();
                Command command = this.commands.get(key);
                if (command == null) {
                    System.out.println("Invalid option");
                }
                else{
                    command.execute();
                }
            }
        }
    }
}
