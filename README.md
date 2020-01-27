data
====

This project uses data about [baby names from the US Social Security Administration](https://www.ssa.gov/oact/babynames/limits.html) to answer specific questions. 


Name:  Michael Williams

NEtid mw376

### Timeline

Start Date:  1/12/2020

Finish Date: 1/27/2020

Hours Spent: 30

### Resources Used

https://stackoverflow.com/questions/17729228/java-read-many-txt-files-on-a-folder-and-process-them for reading all the files

https://stackoverflow.com/questions/2574604/number-out-of-string-in-java for convering years in the fileNames

https://stackoverflow.com/questions/36115069/ranking-an-arrays-values-from-1-to-10-java for ranking the names

https://docs.oracle.com/javase/8/docs/api/java/io/FileFilter.html For building a FileNameFilter

http://www.regexplanet.com/advanced/java/index.html For testing out Regex

http://www.avajava.com/tutorials/lessons/how-do-i-use-a-filenamefilter-to-display-a-subset-of-files-in-a-directory.html also for FileNameFilter

https://stackoverflow.com/questions/52292572/match-a-word-from-beginning-of-line-in-java

https://www.w3schools.com/jsref/prop_loc_href.asp

https://stackoverflow.com/questions/4917327/how-to-get-html-links-from-a-url

https://stackoverflow.com/questions/5120171/extract-links-from-a-web-page

https://www.codeproject.com/Questions/398241/how-to-open-url-in-java

http://tutorials.jenkov.com/java-io/inputstream.html

https://www.tutorialspoint.com/java/java_url_processing.htm
### Running the Program

Main class: Main is the main class, the main method calls all of the other classes/methods
Simply Run main, and modify the name, gender, start year, endyear, and fname depending on the question that you want to answer


Within the main class, the methods allRanks() answers question 1, sameRank answers question 2, commonNameRange answers question 3, commonLetterRange answers question 4

For the complete Specification:
ranksRange() corresponds to question 1

firstLastDiff() corresponds to question 2




# Data files needed: 
Have a local directory in the data folder called "ssa_complete", and within that directory make sure that you have Census files that contain a 4 digit contiguous year,
for instance you can have files that are called yob1993.txt, boy1993.txt, girl1993.txt, so long as there is a 4 digit string that corresponds to the year somewhere in the file name.

At the moment, this program is unable to process data from a URL directory, but this can be worked around by simply downloading that directory to the local machine running this program and specifying the path that leads to the data containing directories

For the JUNIT tests, that data can be located in the data/tests folder

For testing, the test files in the tests directory in the data folder provide very simple test cases where Helen is the most popular Girl's name, with the highest rank, and as such was very easy to test

Files in this program are accessed by a relative path from the data directory, so when cloning this project make sure to have the src and data folders
Key/Mouse inputs:

Cheat keys:

# Known Bugs:
This program handles the following known potential erros without crashing:


an invalid or empty data source (i.e., a non-existent file name or URL or one that exists but contains no data)- If an empty File is present in the directory, this program will simply skip over that file when scanning the directory for files that are compatable with the question, this is done by checking
if the length of the file is 0. If the file length is 0 then it is empty and will be promply skipped over. The details of this check can be seen in the fileCheck() method within Main.java. This method also checks if the given file is really a file as well.
In the event of an empty directory, all directory accesing methods will simply return an empty datatype that is compatable with that they usually return. This can be seen in the ranksRange method where an empty arrayList is returned.

ranges of years that are empty, do not fit completely within the years in the given source of data, or are otherwise nonsensical- This is safeguarded against by the checkYear() method in Main.Java, which checks to see if the year is between
1880 and 2017, and if the year is not, then year accessing methods will simply return an empty data type that is compatable with what they normally return. This can be seen in the ranksRange method.
If the year is valid but the file that corresponds to the year is not, then this is handled by the safeguards specified above for invalid files

names that do not match the exact case of those in the various data files- To prevent this, in methods that are name reliant, the names themslves are converted to lowercase characters only, in order to prevent Helen, helen, and helEN from being seen as different names.
This can be seen in the validName method, as well as the babyRank method, and also in the MostCommonMeaning methods.

genders that are not either M or F (the only ones given in the data files)- This safeguarding  is done by checking all gender-accessing methods at the start to see if the quieried gendier is either M or F, and if not then an empty data type for that method is returned

Also, be cautioned that for Question 9 on the complete dataset, the runtime is extremely long, so it is recommeded to shorten the duration of years to minimizze that.

# JUNIT explained
For the complete specification, each JUNIT test is split in 3-4 subtests, which either check that the data type returned is correct, or it checks that the potential error cases are correctly safeguarded against.

Extra credit:
Not done in this program, however some methods are robust enough to handle multiple cases not specified in the assignment writeup

### Notes/Assumptions
Assumptions made are that all files that this program will access can be relatively/locally pathed to from the data folder
This program does not make the assumption that this data is sorted becuase this program automatically sorts baby names by thier frequency

This program assumes that all files that this program uses will contain a 4 digit year in the file name, otherwise this file will not work, so stick to a yearly naming scheme

This program assumes that the order of the files is sorted chronologically however

Another thing to note is that this program cannot access the url directory located here https://www2.cs.duke.edu/courses/spring20/compsci307d/assign/01_data/data/ssa_complete/

So running this program will require dowwloading that directory to your local machine,

however this url that contains the meanings for various names https://www2.cs.duke.edu/courses/compsci307d/spring20/assign/01_data/data/baby_name_meanings.txt

can and is accessed by this program



### Impressions~~~~
This assignment was really fun, using interfaces like FileNameFilter that I do not normally use was prety fun. Also making design optimizations is interesting as seeing how "small I can go" is interesting. 
