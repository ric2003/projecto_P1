package pt.ulusofona.aed.rockindeisi2023;

 public class SongDetails {
    String tema;
    int duracao;
    int explicita;
    int popularidade;
    float dancabilidade;
    float vivacidade;
    float volumeM;

    public SongDetails(String tema, int duracao, int explicita, int popularidade, float dancabilidade, float vivacidade, float volumeM) {
        this.tema = tema;
        this.duracao = duracao;
        this.explicita = explicita;
        this.popularidade = popularidade;
        this.dancabilidade = dancabilidade;
        this.vivacidade = vivacidade;
        this.volumeM = volumeM;
    }

    public SongDetails() {

    }

    @Override
    public String toString() {
        return tema + " @ " + duracao + " @ " + explicita + " @ " + popularidade +
                " @ " + dancabilidade + " @ " + vivacidade + " @ " + volumeM;
    }
}
