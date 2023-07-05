package sfu.cmpt276.project.chatGptApi;

import java.util.List;

public class ChatResponse {

    private List<Choice> choices;

    public ChatResponse(List choices) {
        this.choices = choices;
    }

    public static class Choice {

        private int index;
        private Query query;

        public Choice(Query query, int index) {
            this.query = query;
            this.index = index;
        }
    }
}
