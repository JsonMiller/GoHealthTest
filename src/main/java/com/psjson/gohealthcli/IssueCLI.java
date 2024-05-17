package com.psjson.gohealthcli;

import com.psjson.gohealthcli.controller.IssueController;
import com.psjson.gohealthcli.model.Issue;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class IssueCLI implements CommandLineRunner {

    private IssueController issueController;

    public IssueCLI(IssueController issueController) {
        this.issueController = issueController;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String usage = "type 'exit' to quit";

        System.out.println(usage);
        while (createOrCloseIssue(scanner)) {
            System.out.println("Remember you can type 'exit' at any time to quit.\n\n");
        }

        System.out.println("Exiting the application.");
        scanner.close();
    }

    /**
     * Let's chat about why I implemented things the way I did. First I made the input multiple prompts. I did this
     * because when you're entering a description you want to be able to use spaces in it, I could have made the inputs
     * quoted or comma seperated or something, but I thought this was just cleaner.
     * @param scanner - Scanner for reading inputs
     * @return - True if we want to keep getting more inputs, false to exit
     * @throws Exception - Something bad happened
     */
    private boolean createOrCloseIssue(Scanner scanner) throws Exception {
        System.out.println("Do you want to create a new issue or close an existing one? (enter 'create' or 'close')");

        String response = scanner.nextLine().trim();

        if("exit".equalsIgnoreCase(response)) {
            return false;
        }

        if("close".equalsIgnoreCase(response)) {
            return closeIssue(scanner);
        }

        if("create".equalsIgnoreCase(response)) {
            return createIssue(scanner);
        }

        return true;

    }

    private boolean closeIssue(Scanner scanner) {
        System.out.println("Enter the ID of the issue you want to close: ");

        String id = scanner.nextLine();

        if("exit".equalsIgnoreCase(id.trim())) {
            return false;
        }

        try {
            issueController.closeIssue(id);
        } catch (Exception ex) {
            System.out.println("Failed to close issue.\n\n");
            return true;
        }

        System.out.println("Issue was successfully closed!\n\n");
        return true;
    }

    private boolean createIssue(Scanner scanner) throws Exception {
        String description;
        String link;

        System.out.println("Enter a parent issue ID, or press Enter if there is no parent:");
        String id = scanner.nextLine();
        if("exit".equalsIgnoreCase(id)) {
            return false;
        }

        System.out.println("Enter a description:");
        description = scanner.nextLine();
        if("exit".equalsIgnoreCase(description)) {
            return false;
        }

        System.out.println("Enter a link:");
        link = scanner.nextLine();
        if("exit".equalsIgnoreCase(link)) {
            return false;
        }

        System.out.printf("New issue id: %s\n\n", issueController.createIssue(new Issue(id, description, link)));

        return true;
    }
}