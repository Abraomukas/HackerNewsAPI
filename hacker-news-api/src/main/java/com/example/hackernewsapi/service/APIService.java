package com.example.hackernewsapi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class APIService {

    private final String HEADER = "*************";

    public List<String> launchApp() {
        Scanner scanner = new Scanner(System.in);
        showMenu();
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                return fetchStoriesFeature(scanner);
            break;
            case 0:
                return exitApp();
            break;
        }
    }

    private void showMenu() {
        System.out.println(HEADER + "\tMENU\t" + HEADER);
        System.out.println("PLEASE SELECT AN OPTION");
        System.out.println("PRESS [1] TO FETCH THE LATEST STORIES");
        System.out.println("PRESS [0] TO EXIT");
        System.out.println();
    }

    private List<String> fetchStoriesFeature(Scanner scanner) {
        List<String> output = new ArrayList<>();

        System.out.println(HEADER + "\tFETCH STORIES\t" + HEADER);
        System.out.println("HOW MANY STORIES WOULD YOU LIKE TO FETCH? (MAXIMUM 50)");
        System.out.println();

        int storiesToFetch = scanner.nextInt();

        if (storiesToFetch > 0 && storiesToFetch < 50) {
            System.out.println("HERE ARE THE TOP " + storiesToFetch + " STORIES");
            try {
                output = fetch_X_stories(storiesToFetch);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("INCORRECT INPUT!");
            fetchStoriesFeature(scanner);
        }

        return output;
    }

    private List<String> fetch_X_stories(int storiesToFetch) throws IOException {
        List<String> output = new ArrayList<>();
        String newAndTopStoriesURL = "https://hacker-news.firebaseio.com/v0/topstories.json";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(newAndTopStoriesURL)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        List<String> storyIDs = fetchStoryIDs(responseBody, storiesToFetch);
        for (String s : storyIDs) {
            output.add(fetchStory(s));
        }

        return output;
    }

    private List<String> fetchStoryIDs(String body, int storiesToFetch) {
        List<String> storyIDs = new ArrayList<>();

        body = body.replace("{\"mode\":\"full\",\"isActive\":false}", "");
        body = body.replace("[", "");
        body = body.replace("]", "");

        String[] split = body.split(",");
        for (int i = 0; i < storiesToFetch; i++) {
            storyIDs.add(split[i]);
        }

        return storyIDs;
    }

    private String fetchStory(String storyId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String storyByIdURL = "https://hacker-news.firebaseio.com/v0/item/" + storyId + ".json";

        Request storyRequest = new Request.Builder()
                .url(storyByIdURL)
                .get()
                .build();

        Response story = client.newCall(storyRequest).execute();
        String responseBody = story.body().string();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(responseBody);
    }

    private List<String> exitApp() {
        List<String> output = new ArrayList<>();
        output.add("EXIT");
        return output;
    }
}
