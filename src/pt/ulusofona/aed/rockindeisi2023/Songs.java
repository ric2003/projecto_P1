package pt.ulusofona.aed.rockindeisi2023;

import java.util.List;

public class Songs {
    String id;
    String name;
    int year;
    int duracao;
    int explicita;
    int popularidade;
    float dancabilidade;
    float vivacidade;
    float volumeM;
    List<String> artistas;
    public Songs(String id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.duracao = 0;
        this.explicita = 0;
        this.popularidade = 0;
        this.dancabilidade = 0.0f;
        this.vivacidade = 0.0f;
        this.volumeM = 0.0f;
        this.artistas = null;

    }

    public Songs() {

    }

    @Override
    public String toString() {
        return id + " @ " + name + " @ " + year;
    }
}
