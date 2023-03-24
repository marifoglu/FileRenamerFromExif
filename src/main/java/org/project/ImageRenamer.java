package org.project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

public class ImageRenamer {

    private static final Logger logger = LoggerFactory.getLogger(ImageRenamer.class);

    public static void main(String[] args) {
        if (args.length == 0) {
            logger.error("Please provide a valid directory path.");
            return;
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            logger.error("The specified directory does not exist or is not a directory.");
            return;
        }

        renameImages(directory);
    }

    public static void renameImages(File directory) {
        try (Stream<Path> paths = Files.walk(Paths.get(directory.getPath()))) {
            paths.filter(Files::isRegularFile)
                    .forEach(file -> {
                        String fileName = file.getFileName().toString();
                        String extension = "";

                        int i = fileName.lastIndexOf('.');
                        if (i > 0 && i < fileName.length() - 1) {
                            extension = fileName.substring(i + 1).toLowerCase();
                        }

                        if (isSupportedFileType(extension)) {
                            try {
                                Metadata metadata = ImageMetadataReader.readMetadata(file.toFile());
                                Date date = null;

                                ExifIFD0Directory exifDirectory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                                if (exifDirectory != null) {
                                    date = exifDirectory.getDate(ExifIFD0Directory.TAG_DATETIME_ORIGINAL);
                                    if (date == null) {
                                        date = exifDirectory.getDate(ExifIFD0Directory.TAG_DATETIME);
                                    }
                                }

                                if (date == null) {
                                    date = new Date(file.toFile().lastModified());
                                }

                                String dateString = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(date);

                                String newFileName = dateString + "." + extension;

                                File newFile = new File(file.getParent().toString(), newFileName);

                                int count = 0;
                                while (newFile.exists()) {
                                    count++;
                                    newFileName = dateString + "_" + count + "." + extension;
                                    newFile = new File(file.getParent().toString(), newFileName);
                                }

                                if (file.toFile().renameTo(newFile)) {
                                    logger.info("Renamed file: {}", newFile.getAbsolutePath());
                                } else {
                                    logger.error("Failed to rename file: {}", file.toFile().getAbsolutePath());
                                }
                            } catch (ImageProcessingException e) {
                                logger.error("Error processing file: {}", file.toFile().getAbsolutePath(), e);
                            } catch (IOException e) {
                                logger.error("IO error occurred while processing file: {}", file.toFile().getAbsolutePath(), e);
                            } catch (Exception e) {
                                logger.error("Unexpected error occurred while processing file: {}", file.toFile().getAbsolutePath(), e);
                            }
                        }
                    });
        } catch (IOException e) {
            logger.error("Error processing directory: {}", directory.getAbsolutePath(), e);
        }
    }

    private static boolean isSupportedFileType(String extension) {
        try {
            SupportedFileType fileType = SupportedFileType.valueOf(extension.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private enum SupportedFileType {
        JPG("jpg"),
        JPEG("jpeg"),
        PNG("png"),
        MOV("mov");

        private final String extension;

        SupportedFileType(String extension) {
            this.extension = extension;
        }

        public String getExtension() {
            return extension;
        }
    }
}