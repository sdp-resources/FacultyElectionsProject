package webserver;

import org.junit.Test;

import java.io.*;
import java.nio.file.*;

public class SamplePageGenerator extends ServerTest {

  private String testPageDir = System.getProperty("user.dir") + "/htmlTestPages/";
  private FileSystem fileSystem = FileSystems.getDefault();

  @Test
  public void createPages() throws IOException {
    copyOverAssets();
    createDirectoryIfNotExists("admin");
    writePageToFile("login.html",
                    WebClient.get(Path.login()));
    writePageToFile("userHome.html",
                    WebClients.normalLoggedInClient().followGet(Path.user()));
    writePageToFile("adminHome.html",
                    WebClients.adminLoggedInClient().followGet(Path.admin()));
    writePageToFile("admin/committees.html",
                    WebClients.adminLoggedInClient().followGet(Path.adminAllCommittees()));
    writePageToFile("admin/namedQueries.html",
                    WebClients.adminLoggedInClient().followGet(Path.queryAll()));
    writePageToFile("admin/adminProfiles.html",
                    WebClients.adminLoggedInClient().followGet(Path.adminProfile()));
    writePageToFile("voting.html",
                    WebClients.getActiveElectionClient());
  }

  private void copyOverAssets() throws IOException {
    copyAssetsForDirectory("css");
    copyAssetsForDirectory("js");
  }

  private void copyAssetsForDirectory(String directoryName) throws IOException {
    java.nio.file.Path sourcePath = fileSystem.getPath(testPageDir,
                                                       "..", "assets", "public",
                                                       directoryName);

    createDirectoryIfNotExists(directoryName);

    File source = new File(String.valueOf(sourcePath));
    for (File file : source.listFiles()) {
      Files.copy(file.toPath(),
                 fileSystem.getPath(testPageDir, directoryName, file.getName()),
                 StandardCopyOption.REPLACE_EXISTING);
    }
  }

  private void createDirectoryIfNotExists(String directoryName) throws IOException {
    java.nio.file.Path path = fileSystem.getPath(testPageDir, directoryName);
    if (!Files.exists(path)) {
      Files.createDirectory(path);
    }
  }

  private void writePageToFile(String filename, WebClient client) throws FileNotFoundException {
    String contents = client.document.toString();
    try (PrintWriter out = new PrintWriter(testPageDir + filename)) {
      out.println(contents);
    }
  }

}
