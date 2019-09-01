package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;

@Entity
public class Dictionary extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "word", unique = true)
    private String word;

    public Dictionary() {
    }

    public Dictionary(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public static final Finder<Integer, Dictionary> find = new Finder<>(Dictionary.class);
}