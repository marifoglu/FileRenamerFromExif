# Image Renaming App

This is a simple Java application that renames image files in a given directory based on the date and time the photo was taken, as specified in the image's EXIF metadata. The app currently supports the JPEG, PNG, and MOV file formats.

Basicly, who is start using Samsung phone and wants use Iphone's photo/video library in the Samsung with same generated name.

## How to Use

1. Download the source code and compile it using a Java compiler such as `javac`.
2. Run the compiled `.class` file using the command `java ImageRenamer <directory_path>`, where `<directory_path>` is the path to the directory containing the image files to be renamed.
3. The app will scan the specified directory (and its subdirectories) for image files, and rename them according to the date and time the photo was taken. The new file names will have the format `yyyyMMdd_HHmmss.<extension>`, where `yyyyMMdd_HHmmss` is the date and time the photo was taken (in the format year-month-day hour-minute-second), and `<extension>` is the original file extension (e.g. `.jpg`, `.png`, or `.mov`).

## Dependencies

The app uses the following external libraries:

- [Metadata Extractor](https://github.com/drewnoakes/metadata-extractor) (version 2.16.0) for reading EXIF metadata from image files.
