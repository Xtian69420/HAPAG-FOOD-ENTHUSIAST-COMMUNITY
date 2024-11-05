package com.example.hapag;

public class Post {
    private String username;
    private String date;
    private String time;
    private String title;
    private String ingredients;
    private String steps;
    private int upvotes;
    private int downvotes;

    // Constructor
    public Post(String username, String date, String time, String title, String ingredients, String steps, int upvotes, int downvotes) {
        this.username = username;
        this.date = date;
        this.time = time;
        this.title = title;
        this.ingredients = ingredients;
        this.steps = steps;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
}
