<h1>Image Renaming App</h1>

<p>This is a simple Java application that renames image files in a given directory based on the date and time the photo was taken, as specified in the image's EXIF metadata. The app currently supports the JPEG, PNG, and MOV file formats.</p>

<p>Basicly, who is start using Samsung phone and wants use Iphone's photo/video library in the Samsung with same generated name.</p>

<h2>How to Use</h2>

<ol>
<li>Download the source code and compile it using a Java compiler such as <code>javac</code>.</li>
<li>Run the compiled <code>.class</code> file using the command <code>java ImageRenamer &lt;directory_path&gt;</code>, where <code>&lt;directory_path&gt;</code> is the path to the directory containing the image files to be renamed.</li>
<li>The app will scan the specified directory (and its subdirectories) for image files, and rename them according to the date and time the photo was taken. The new file names will have the format <code>yyyyMMdd_HHmmss.&lt;extension&gt;</code>, where <code>yyyyMMdd_HHmmss</code> is the date and time the photo was taken (in the format year-month-day hour-minute-second), and <code>&lt;extension&gt;</code> is the original file extension (e.g. <code>.jpg</code>, <code>.png</code>, or <code>.mov</code>).</li>
</ol>

<h2>Dependencies</h2>

<p>The app uses the following external libraries:</p>

<ul>
<li><a href="https://github.com/drewnoakes/metadata-extractor">Metadata Extractor</a> (version 2.16.0) for reading EXIF metadata from image files.</li>
</ul>