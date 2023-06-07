import java.nio.file.*;
import java.io.*;
import java.nio.file.attribute.*;

public class FileVisitorDemo
{
    public static void main(String[] args)
    {
        Path start = Paths.get("c:\\Windows\\System32");	
        MyFileVisitor visitor = new MyFileVisitor();	
        try
        {
            Files.walkFileTree(start, visitor);	
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static class MyFileVisitor extends SimpleFileVisitor <Path>
    {
        public FileVisitResult visitFile(Path file, 	
            BasicFileAttributes attr)
        {
            System.out.println(file.toString());
            return FileVisitResult.CONTINUE;	
        }

        public FileVisitResult visitFileFailed(Path file,	
            BasicFileAttributes attr)
        {
            System.out.println(file.toString() + " COULD NOT ACCESS!");
            return FileVisitResult.CONTINUE;
        }

        public FileVisitResult preVisitDirectoryFailed(Path dir,	
            IOException e)
        {
            System.out.println(dir.toString() + " COULD NOT ACCESS!");
            return FileVisitResult.CONTINUE;
        }
    }
}
