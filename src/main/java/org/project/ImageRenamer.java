package org.project;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

public class ImageRenamer {

    public static void renameImages(File directory) {
        File[] files = directory.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                renameImages(file);
            } else {
                String fileName = file.getName();
                String extension = "";

                int i = fileName.lastIndexOf('.');
                if (i > 0 && i < fileName.length() - 1) {
                    extension = fileName.substring(i + 1).toLowerCase();
                }

                if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("mov")) {
                    try {
                        Metadata metadata = ImageMetadataReader.readMetadata(file);

                        ExifIFD0Directory exifDirectory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                        Date date = null;
                        if (exifDirectory != null) {
                            date = exifDirectory.getDate(ExifIFD0Directory.TAG_DATETIME_ORIGINAL);
                            if (date == null) {
                                date = exifDirectory.getDate(ExifIFD0Directory.TAG_DATETIME);
                            }
                        }
                        if (date == null) {
                            date = new Date(file.lastModified());
                        }

                        String dateString = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(date);

                        String newFileName = dateString + "." + extension;

                        File newFile = new File(file.getParent(), newFileName);

                        if (!newFile.exists()) {
                            file.renameTo(newFile);
                        }
                    } catch (ImageProcessingException e) {
                        System.err.println("Error processing " + file.getAbsolutePath() + ": " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Error renaming " + file.getAbsolutePath() + ": " + e.getMessage());
                    }
                }
            }
        }
    }
}


