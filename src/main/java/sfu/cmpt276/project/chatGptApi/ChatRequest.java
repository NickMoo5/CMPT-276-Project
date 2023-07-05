package sfu.cmpt276.project.chatGptApi;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {
    private String model;
    private List<Query> queries;
    private int n = 1;
    private double temperature = 1;

    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.queries = new ArrayList<>();
        this.queries.add(new Query("user", prompt));
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
