### names of all people who worked on the project
Michael Williams mw376
### each person's role in developing the project
Michael Williams- As the lead developor of this project, implemented and designed every single class and method.

### what are the project's design goals, specifically what kinds of new features did you want to make easy to add
The goal of this project was to build the backend for an app that could parse files/resources containing information about baby names in order to tell users statistics about baby names such as which ones were te most popular over a period of time, which names changed the most over a period of time, the average rank of a name over a period of time. For this project, I wanted to make it easy to scan file directories containing this baby information and analyze those files to provide various statistics on baby names for a year to year basis.

### describe the high-level design of your project, focusing on the purpose and interaction of the core classes

On a high level, this program essentially does 3 tasks in tandem: Processing baby name data from files, putting that data into data structures that hold baby name objects, then conducting data analysis using those data structures. To do this, my program is split into 4 classes: Baby, YearFileFilter, FreqSort, and Main, with main holding all of the methods that answer all possible questions while also implementing methods from the other 3 classes.

For processing baby name data from files, the class YearFileFilter, which implements the FileNameFilter, is used to select files that contain a year in thier file name, because those files contain the baby name data

Once the files are filtered, the files are then read line by line, with each line being used to create a Baby class objct

The Baby class is constructed using the frequency that a baby name occurs, the baby name itself, and the gender of this name

Each baby object is then stored in a data structure that is sorted using the FreqSort comparator class, which timply sorts baby objects based on their frequency.

This is the high level pattern that happens in every question based method, the actual answers to questions are simply pulled algorithmically from the data structure generating process outlined above 


### what assumptions or decisions were made to simplify your project's design, especially those that affected adding required features
To simplify how this project was ran, I assumed that all data  would ultimately be read in from a directory of files, as that is
a key component of pretty much every method I wrote. Even if a url was read in as a data source, it's file contents would be downloaded to form a local directory. This is because iterating through, validating, and utiziling
files from a directory is very efficient in Java. This ultimately made adding in the required feature of using  a url based directory very difficult because I wanted to be able to download the contents of that directory to a local file directory and then go from there, but I did not properly implement that feature.

### describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline
To add nnew features to this project simply go to src/names/Main.java and edit methods in the main class. One incomplete feature in this project was the urlReader() 
method, which is supposed to read a url from its parameter, find all of the href links contained in that url's corresponding webpage, and then finally download files from those href links to the url data folder located in the data directory. This process of URL decoding would allow me to preserve the geneeral structure of my code, which is file based, without copying every method over to make corresponding URL based methods. I ultimately could not implement this feature in time for the deadline, but a good approach would be to use regex grouping to extract the href tag itself, then append that tag to the directory url to access the file. From there, the code in meaningList would be extremely helpful for reading in the data from the actual text file, and then downloading that data to the urldata directory
would not be too difficult. This was the main feature that I failed to implement.

To add new tests to test any existing feature, simply go the the data/ directory, and then add your own test directory. All you have to do to make any question method run data from this created directory is specify the relative path to that directory, and then every specified question will be answered and the output for your directory will be printed to the screen.