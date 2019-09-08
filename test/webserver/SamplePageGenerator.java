package webserver;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class SamplePageGenerator extends ServerTest {

  private String testPageDir = System.getProperty("user.dir") + "/htmlTestPages/";

  @Test
  public void createPages() throws IOException {
    createSymbolicLinksIfNotExisting();
    writePageToFile("login.html",
                    WebClient.get(Path.login()));
    writePageToFile("userHome.html",
                    WebClients.normalLoggedInClient().followGet(Path.user()));
    writePageToFile("adminHome.html",
                    WebClients.adminLoggedInClient().followGet(Path.admin()));
    writePageToFile("committees.html",
                    WebClients.adminLoggedInClient().followGet(Path.adminCommittee()));
    writePageToFile("namedQueries.html",
                    WebClients.adminLoggedInClient().followGet(Path.queryAll()));
    writePageToFile("adminProfiles.html",
                    WebClients.adminLoggedInClient().followGet(Path.adminProfile()));
    writePageToFile("voting.html",
                    WebClients.getActiveElectionClient());
  }

  private void createSymbolicLinksIfNotExisting() throws IOException {
    ensureSymbolicLink("css");
    ensureSymbolicLink("js");
  }

  private void ensureSymbolicLink(String directoryName) throws IOException {
    FileSystem fileSystem = FileSystems.getDefault();
    java.nio.file.Path path = fileSystem.getPath(testPageDir, directoryName);
    java.nio.file.Path targetPath = fileSystem.getPath(testPageDir,
                                                       "..", "assets", "public",
                                                       directoryName);
    if (!Files.isSymbolicLink(path)) {
      Files.createSymbolicLink(path, targetPath);
    }
  }

  private void writePageToFile(String filename, WebClient client) throws FileNotFoundException {
    String contents = client.document.toString();
    try (PrintWriter out = new PrintWriter(testPageDir + filename)) {
      out.println(contents);
    }
  }

}
