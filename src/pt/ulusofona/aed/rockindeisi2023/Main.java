package pt.ulusofona.aed.rockindeisi2023;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.List;



public class Main {

    static int LinhasOK_songs ,LinhasOK_SongArtist ,LinhasOK_SongDetails,
            LinhasNOK_songs,LinhasNOK_SongArtist,LinhasNOK_SongDetails,
            PLinhaNOK_songs,PLinhaNOK_SongArtist,PLinhaNOK_SongDetails;
    static ArrayList<String> namesArtist;
    static ArrayList<Songs> SongsList;
    static ArrayList<SongArtist> SongArtistList;
    static ArrayList<SongDetails> SongDetailsList;
    static ArrayList<Songs> TempValidSongs = new ArrayList<>();
    static ArrayList<Songs> ValidSongs = new ArrayList<>();
    static ArrayList<SongArtist> ValidArtists = new ArrayList<>();
    public static boolean loadSongs(String fileName)  {
        SongsList = new ArrayList<>();
        int lineNumber=0;
        LinhasOK_songs=0;
        LinhasNOK_songs=0;
        PLinhaNOK_songs=-1;

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();
                if(!line.isEmpty()){
                    int count = 0;
                    for(int i = 0;i<line.length();i++) {
                        if(line.charAt(i) == '@'){
                            count++;
                        }
                    }
                        String[] parts = line.split("@");

                    if (parts.length == 3&&count==2) {
                        String id = parts[0].trim();
                        String name = parts[1].trim();
                        int year = Integer.parseInt(parts[2].trim());

                        boolean duplicate = false;
                        for (int i = 0; i < SongsList.size(); i++) {
                            Songs songs = SongsList.get(i);
                            if (songs.id.equals(id)) {
                                duplicate = true;
                                break;
                            }
                        }
                        if (!duplicate) {
                            SongsList.add(new Songs(id, name, year));
                            LinhasOK_songs++;
                        }else {
                            LinhasNOK_songs++;
                            if (LinhasNOK_songs == 1) {
                                PLinhaNOK_songs = lineNumber;
                            }
                        }

                    } else {
                        LinhasNOK_songs++;
                        if (LinhasNOK_songs == 1) {
                            PLinhaNOK_songs = lineNumber;
                        }
                    }
                }else{
                    LinhasNOK_songs++;
                    if (LinhasNOK_songs == 1) {
                        PLinhaNOK_songs = lineNumber;
                    }
                }
            }

            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static boolean loadSongArtists(String fileName) {
        SongArtistList = new ArrayList<>();
        namesArtist = new ArrayList<>();
        int lineNumber = 0;
        LinhasOK_SongArtist = 0;
        LinhasNOK_SongArtist = 0;
        PLinhaNOK_SongArtist = -1;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                boolean  ta_errado=true;
                String line = scanner.nextLine().trim();

                if (line != null && !line.trim().isEmpty()) {
                    try {
                        int count = 0;
                        for(int i = 0;i<line.length();i++) {
                            if(line.charAt(i) == '@'){
                                count++;
                            }
                        }
                        String[] parts = line.split("@");
                        if (parts.length == 2&&count==1) {
                            String songId = parts[0].trim();
                            String artistsStr = parts[1].trim();
                            if (artistsStr.startsWith("[")&&artistsStr.endsWith("]")) {
                                ta_errado = false;
                                artistsStr = artistsStr.substring(1, artistsStr.length() - 1).trim();
                                String[] artistArray = artistsStr.split(",");
                                if (artistArray.length > 1 || artistArray[0].equals("''")) {
                                    ta_errado = true;
                                    LinhasNOK_SongArtist++;
                                    if (LinhasNOK_SongArtist == 1) {
                                        PLinhaNOK_SongArtist = lineNumber;
                                    }
                                }
                                artistsStr=artistsStr.trim();
                            } else if (artistsStr.startsWith("\"")&&artistsStr.endsWith("\"")) {
                                ta_errado = false;
                                artistsStr = artistsStr.substring(1, artistsStr.length() - 1).trim();
                                artistsStr =artistsStr.trim().substring(1, artistsStr.length() - 1).trim();
                                String[] artistArray = artistsStr.split(",");
                                if(artistArray.length < 2 ){
                                    ta_errado = true;
                                    LinhasNOK_SongArtist++;
                                    if (LinhasNOK_SongArtist == 1) {
                                        PLinhaNOK_SongArtist = lineNumber;
                                    }
                                }
                            }
                            if (!ta_errado) {
                                String[] artistArray = artistsStr.split(",");
                                List<String> artists = new ArrayList<>();
                                for (String artist : artistArray) {
                                    artist = artist.trim();//check later
                                    if (artist.startsWith("'") && artist.endsWith("'")) {
                                        artist = "[" + artist.substring(1, artist.length() - 1).trim() + "]";
                                        artists.add(artist);

                                    } else if ((artist.startsWith(" '") && artist.endsWith("'"))) {
                                        artist = "[" + artist.substring(2, artist.length() - 1).trim() + "]";
                                        artists.add(artist);
                                    }
                                }

                                boolean duplicate = false;
                                for (int i = 0; i < SongArtistList.size(); i++) {
                                    SongArtist songArtist = SongArtistList.get(i);
                                    if (songArtist.tema.equals(songId)) {
                                        duplicate = true;
                                        break;
                                    }
                                }
                                if (!duplicate) {
                                    SongArtistList.add(new SongArtist(songId, artists));
                                    namesArtist.addAll(artists);
                                    LinhasOK_SongArtist++;
                                } else {
                                    LinhasNOK_SongArtist++;
                                    if (LinhasNOK_SongArtist == 1) {
                                        PLinhaNOK_SongArtist = lineNumber;
                                    }
                                }
                            }
                        }else{
                            LinhasNOK_SongArtist++;
                            if (LinhasNOK_SongArtist == 1) {
                                PLinhaNOK_SongArtist = lineNumber;
                            }
                        }

                    } catch (Exception e) {
                        LinhasNOK_SongArtist++;
                        if (LinhasNOK_SongArtist == 1) {
                            PLinhaNOK_SongArtist = lineNumber;
                        }
                        System.out.println("Error parsing line " + lineNumber + ": " + line);
                    }
                }else{
                    LinhasNOK_SongArtist++;
                    if (LinhasNOK_SongArtist == 1) {
                        PLinhaNOK_SongArtist = lineNumber;
                    }
                }
            }
            return true;
        } catch (FileNotFoundException noFile) {
            System.out.println("file doesnt exist");
            return false;
        }
    }

    public static boolean loadSongDetails(String fileName) {
        SongDetailsList = new ArrayList<>();
        int lineNumber=0;
        LinhasOK_SongDetails=0;
        LinhasNOK_SongDetails=0;
        PLinhaNOK_SongDetails=-1;



        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();
                if(line != null && !line.trim().isEmpty()) {
                    int count = 0;
                    for(int i = 0;i<line.length();i++) {
                        if(line.charAt(i) == '@'){
                            count++;
                        }
                    }
                    String[] parts = line.split("@");

                    if (parts.length == 7&&count==6) {
                        String tema = parts[0].trim();
                        int duracao = Integer.parseInt(parts[1].trim());
                        int explicita = Integer.parseInt(parts[2].trim());
                        int popularidade = Integer.parseInt(parts[3].trim());
                        float dancabilidade = Float.parseFloat(parts[4].trim());
                        float vivacidade = Float.parseFloat(parts[5].trim());
                        float volumeM = Float.parseFloat(parts[6].trim());

                        boolean duplicate = false;
                        for (int i = 0; i < SongDetailsList.size(); i++) {
                            SongDetails SongDetails = SongDetailsList.get(i);
                            if (SongDetails.tema.equals(tema)) {
                                duplicate = true;
                                break;
                            }
                        }
                        if (!duplicate) {
                            SongDetailsList.add(new SongDetails(tema, duracao, explicita, popularidade, dancabilidade, vivacidade, volumeM));
                            LinhasOK_SongDetails++;
                        }else {
                            LinhasNOK_SongDetails++;
                            if (LinhasNOK_SongDetails == 1) {
                                PLinhaNOK_SongDetails = lineNumber;
                            }
                        }

                    } else {
                        LinhasNOK_SongDetails++;
                        if (LinhasNOK_SongDetails == 1) {
                            PLinhaNOK_SongDetails = lineNumber;
                        }
                    }
                }else{
                    LinhasNOK_SongDetails++;
                    if (LinhasNOK_SongDetails == 1) {
                        PLinhaNOK_SongDetails = lineNumber;
                    }
                }
            }

            return true;
        } catch (FileNotFoundException noFile) {
            return false;
        }
    }

    public static int musicas_colaborado(String nameToFInd) {

        int count = 0;
        for (String name : namesArtist) {
            if (name.equals(nameToFInd)) {
                count++;
            }
        }
        return count;
    }

    public static EstatisticasArquivo calcEstatisticasArquivo(String filename){
        switch(filename){
            case "songs.txt":
                return new EstatisticasArquivo("songs.txt",LinhasOK_songs,LinhasNOK_songs,PLinhaNOK_songs);
            case "song_artists.txt":
                return new EstatisticasArquivo("song_artists.txt",LinhasOK_SongArtist,LinhasNOK_SongArtist,PLinhaNOK_SongArtist);
            case "song_details.txt":
                return new EstatisticasArquivo("song_details.txt",LinhasOK_SongDetails,LinhasNOK_SongDetails,PLinhaNOK_SongDetails);
            default:
                return null;
        }
    }


    public static boolean loadFiles(File folder) {
        TempValidSongs.clear();
        ValidSongs.clear();
        ValidArtists.clear();

        File file1 = new File(folder, "songs.txt");
        File file2 = new File(folder, "song_artists.txt");
        File file3 = new File(folder, "song_details.txt");

        if (!(file1.exists() && file2.exists() && file3.exists())) {
            return false;
        }

        return loadSongs(file1.getPath()) &&loadSongArtists(file2.getPath()) &&
                loadSongDetails(file3.getPath());
    }
    public static void removeFicheirosComTemasErrados() {

        for(int i = 0; i < SongsList.size(); i++) {
            for(int j = 0; j < SongDetailsList.size(); j++) {
                if(SongsList.get(i).id.equals(SongDetailsList.get(j).tema)) {
                    SongsList.get(i).duracao = SongDetailsList.get(j).duracao;
                    SongsList.get(i).explicita = SongDetailsList.get(j).explicita;
                    SongsList.get(i).popularidade = SongDetailsList.get(j).popularidade;
                    SongsList.get(i).dancabilidade = SongDetailsList.get(j).dancabilidade;
                    SongsList.get(i).vivacidade = SongDetailsList.get(j).vivacidade;
                    SongsList.get(i).volumeM = SongDetailsList.get(j).volumeM;
                    TempValidSongs.add(SongsList.get(i));
                    break;
                }
            }
        }

        for(int i = 0; i < TempValidSongs.size(); i++) {
            for(int j = 0; j < SongArtistList.size(); j++) {
                if(TempValidSongs.get(i).id.equals(SongArtistList.get(j).tema)) {
                    TempValidSongs.get(i).artistas = SongArtistList.get(j).artistas;
                    ValidSongs.add(TempValidSongs.get(i));
                    break;
                }
            }
        }

        for(int i = 0; i < SongArtistList.size(); i++) {
            for(int j = 0; j < ValidSongs.size(); j++) {
                if(SongArtistList.get(i).tema.equals(ValidSongs.get(j).id)) {
                    ValidArtists.add(SongArtistList.get(i));
                    break;
                }
            }
        }
    }


    public static ArrayList getObjects(TipoEntidade tipo) {
        removeFicheirosComTemasErrados();
        ArrayList finalList = new ArrayList();
        switch(tipo){
            case TEMA:
                if (ValidSongs != null && ValidSongs.size() > 0) {
                    for(int i = 0;i<ValidSongs.size();i++) {
                        if (ValidSongs.get(i).year < 1995) {
                            finalList.add(ValidSongs.get(i).id + " | " + ValidSongs.get(i).name + " | " + ValidSongs.get(i).year);

                        } else if (ValidSongs.get(i).year < 2000) {
                            int duracao_secs = ValidSongs.get(i).duracao/1000;
                            String duracao_MinSec = (duracao_secs / 60) + ":" + String.format("%02d", duracao_secs % 60);
                            finalList.add(ValidSongs.get(i).id + " | " + ValidSongs.get(i).name + " | " + ValidSongs.get(i).year +
                                        " | " + duracao_MinSec + " | " + ValidSongs.get(i).popularidade);
                        } else if (ValidSongs.get(i).year >= 2000) {
                            int duracao_secs = ValidSongs.get(i).duracao/1000;
                            String duracao_MinSec = (duracao_secs / 60) + ":" + String.format("%02d", duracao_secs % 60);
                            List<String> artistList = ValidSongs.get(i).artistas;
                            int numArtistas = 0;

                            for (int j = 0; j < artistList.size(); j++) {
                                String currentArtist = artistList.get(j);
                                if (!artistList.subList(0, j).contains(currentArtist)) {
                                    numArtistas++;
                                }
                            }
                            finalList.add(ValidSongs.get(i).id+" | "+ValidSongs.get(i).name+" | "+ValidSongs.get(i).year+
                                    " | "+duracao_MinSec+" | "+ValidSongs.get(i).popularidade+" | "+numArtistas );

                        }

                    }
                }
                return finalList;
            case ARTISTA:
                if (ValidArtists != null && ValidArtists.size() > 0) {
                    for (int i = 0; i < ValidArtists.size(); i++) {
                        for (int j = 0; j < ValidArtists.get(i).artistas.size(); j++) {
                            String artista_individual = ValidArtists.get(i).artistas.get(j);
                            if (artista_individual.charAt(1) == 'A' || artista_individual.charAt(1) == 'B'
                                    || artista_individual.charAt(1) == 'C' || artista_individual.charAt(1) == 'D') {
                                if (!finalList.contains("Artista: " + artista_individual)) {
                                    finalList.add("Artista: " + artista_individual);
                                }
                            } else {
                                if (!finalList.contains("Artista: " + artista_individual + " | "
                                        + musicas_colaborado(artista_individual))) {
                                    finalList.add("Artista: " + artista_individual + " | "
                                            + musicas_colaborado(artista_individual));
                                }
                            }
                        }
                    }
                }
                return finalList;

            case INPUT_INVALIDO:

                ArrayList<EstatisticasArquivo> estatisticasList = new ArrayList<EstatisticasArquivo>();
                estatisticasList.add(calcEstatisticasArquivo("songs.txt"));
                estatisticasList.add(calcEstatisticasArquivo("song_details.txt"));
                estatisticasList.add(calcEstatisticasArquivo("song_artists.txt"));

                return estatisticasList;
            default:
                return null;

        }
    }


    public static void main(String[] args){

    }

}