package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.util.Deque;
import java.util.ArrayDeque;

/**
 * утилита для поиска длинных путей
 */
public class Main {
  private static final int MAX_PATH_LENGTH = 250;
  private static final String stringPathToFolder = "C:\\";

  public static void main(String[] args) {
    Path pathToFolder = Paths.get(stringPathToFolder);
    Deque<Path> stack = new ArrayDeque<>();
    stack.push(pathToFolder);

    while (!stack.isEmpty()) {
      Path currentDir = stack.pop();

      try(DirectoryStream<Path> stream = Files.newDirectoryStream(currentDir)) {
        for (Path entry : stream) {
          if (Files.isDirectory(entry)) {
            stack.push(entry);
          } else if (Files.isRegularFile(entry)) {
            if (entry.toString().length() > MAX_PATH_LENGTH) {
              System.out.println(entry);
            }
          }
        }
      } catch (AccessDeniedException e) {
        System.err.println(e.getMessage());
      } catch (IOException e) {
        System.err.println("Ошибка доступа к директории: " + currentDir);
        throw new RuntimeException(e);
      }
    }
  }
}