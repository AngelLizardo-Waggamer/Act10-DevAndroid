package aahl.apiconsumerapp.models;

import java.util.List;

// Sirve para /api/character
public class CharacterCompleteList {

    private PageInfo info;
    private List<Character> results;

    public PageInfo getInfo() {
        return info;
    }

    public List<Character> getResults() {
        return results;
    }

}
