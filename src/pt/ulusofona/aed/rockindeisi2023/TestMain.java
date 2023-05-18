package pt.ulusofona.aed.rockindeisi2023;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;

public class TestMain {

    @Test
    public void toStringBefore1995() {
        Main.loadFiles(new File("test-files/Before1995"));
        String obtained = Main.getObjects(TipoEntidade.TEMA).toString();

       String expected = "[4Cna7QxOOTNnylVHLtShCi | Only For You | 1980, 2Sy1r4fGfq4Lslfc0gHkbk | Loops & Tings - Radio Edit | 1990]";
        Assertions.assertEquals(expected,obtained,"erro nas musicas antes de 1995");
    }

    @Test
    public void toStringBefore2000() {
        Main.loadFiles(new File("test-files/Before2000"));
        String obtained = Main.getObjects(TipoEntidade.TEMA).toString();

        String expected = "[4Cna7QxOOTNnylVHLtShCi | Only For You | 1999 | 3:37 | 57, 2Sy1r4fGfq4Lslfc0gHkbk | Loops & Tings - Radio Edit | 1996 | 4:44 | 45]";
        Assertions.assertEquals(expected,obtained,"erro nas musicas antes de 2000");
    }
    @Test
    public void toStringAfter2000() {
        Main.loadFiles(new File("test-files/After2000"));
        String obtained = Main.getObjects(TipoEntidade.TEMA).toString();

        String expected = "[4Cna7QxOOTNnylVHLtShCi | Only For You | 2000 | 3:37 | 57 | 1, 2Sy1r4fGfq4Lslfc0gHkbk | Loops & Tings - Radio Edit | 2003 | 4:44 | 45 | 2]";
        Assertions.assertEquals(expected,obtained,"erro nas musicas depois de 2000");
    }
    @Test
    public void toStringInfoDeArtistas() {
        Main.loadFiles(new File("test-files/InfoDeArtistas"));
        String obtained = Main.getObjects(TipoEntidade.ARTISTA).toString();

        String expected = "[Artista: [Madonna] | 1, Artista: [La Sonora Matancera] | 1, Artista: [Carlos Argentino]]";
        Assertions.assertEquals(expected,obtained,"erro nos Artistas");
    }
    @Test
    public void loadTest_fileCertos() {
        boolean received = Main.loadFiles(new File("test-files/certos"));
        if (!received) {
            Assertions.fail("Failed to load files from test-files/certos directory.");
        }
        Assertions.assertTrue(received);
    }
    @Test
    public void loadTest_fileErrado() {
        boolean received = Main.loadFiles(new File("test-files/erros"));
        if (!received) {
            Assertions.fail("Failed to load files from test-files/erros directory.");
        }
        Assertions.assertTrue(received);
    }
}