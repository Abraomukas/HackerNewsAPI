package com.example.hackernewsapi.service;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Service
public class APIService {

    private final String DELIMETER = "*************";
    private final String HACKER_NEWS_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";

    public void launchApp() {
        Scanner scanner = new Scanner(System.in);
        showMenu();
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                fetchStories(scanner);
                break;
            case 0:
                exitApp();
                break;
        }

    }

    private void showMenu() {
        System.out.println(DELIMETER + "\tMENU\t" + DELIMETER);
        System.out.println("PLEASE SELECT AN OPTION");
        System.out.println("PRESS [1] TO FETCH THE LATEST STORIES");
        System.out.println("PRESS [0] TO EXIT");
        System.out.println();
    }

    private void fetchStories(Scanner scanner) {
        System.out.println(DELIMETER + "\tFETCH STORIES\t" + DELIMETER);
        System.out.println("HOW MANY STORIES WOULD YOU LIKE TO FETCH? (MAXIMUM 50)");
        System.out.println();

        int storiesToFetch = scanner.nextInt();

        if (storiesToFetch > 0 && storiesToFetch < 50) {
            System.out.println("HERE ARE THE TOP " + storiesToFetch + " STORIES");
            fetchStories();
        } else {
            System.out.println("INCORRECT INPUT!");
            fetchStories(scanner);
        }
    }

    private void exitApp() {
        System.exit(0);
    }

    private void fetchStories() {
        try {
            URL url = new URL(HACKER_NEWS_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
