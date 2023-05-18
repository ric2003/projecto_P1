package pt.ulusofona.aed.rockindeisi2023;

import java.util.List;

public class SongArtist {
    String tema;
    List<String> artistas;

    public SongArtist(String tema, List<String> artistas) {
        this.tema = tema;
        this.artistas = artistas;
    }

    public SongArtist() {

    }

    @Override
    public String toString() {
        return tema + " @ " + artistas.toString();
    }

}
