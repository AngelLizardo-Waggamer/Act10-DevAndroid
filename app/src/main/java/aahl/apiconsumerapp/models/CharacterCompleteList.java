package aahl.apiconsumerapp.models;

import java.util.List;

// Sirve para /api/character
public class CharacterCompleteList {

    private Object info; // {count, pages, next, prev}
    private List<Character> results; // Lista de personajes hasta la página indicada

    public Object getInfo() {
        return info;
    }

    public List<Character> getResults() {
        return results;
    }

}
