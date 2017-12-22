package ru.taskmanager.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardCopyOption.*;

public class TreeCopier implements FileVisitor<Path> {

    private final Path source;
    private final Path target;

    public TreeCopier(Path source, Path target) throws IOException {
        this.source = source;
        this.target = target;

        deleteFilesInDir(target);

        if(!Files.exists(this.target)) {
            Files.createDirectories(this.target);
        }
    }

    private void deleteFilesInDir(Path targetDir) throws IOException {
        if(Files.exists(targetDir)){
            Files.walkFileTree(targetDir, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    public void copy() throws IOException {
        Files.walkFileTree(source, this);
    }

    private boolean isRootTargetDir(Path p){
        return p.equals(target);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

        Path targetDir = target.resolve(source.relativize(dir));
        if(isRootTargetDir(targetDir)){
            return FileVisitResult.CONTINUE;
        }

        try {
            Files.copy(dir, targetDir, new CopyOption[] { REPLACE_EXISTING });
        } catch (FileAlreadyExistsException x) {
        } catch (IOException x) {
            return FileVisitResult.SKIP_SUBTREE;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.copy(file, target.resolve(source.relativize(file)), new CopyOption[] { REPLACE_EXISTING });

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }
}
