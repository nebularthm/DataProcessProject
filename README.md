data
====

This project uses data about [baby names from the US Social Security Administration](https://www.ssa.gov/oact/babynames/limits.html) to answer specific questions. 


Name:  Michael Williams

### Timeline

Start Date:  1/12/2020

Finish Date: 

Hours Spent:

### Resources Used

https://stackoverflow.com/questions/17729228/java-read-many-txt-files-on-a-folder-and-process-them for reading all the files

https://stackoverflow.com/questions/2574604/number-out-of-string-in-java for convering years in the fileNames

https://stackoverflow.com/questions/36115069/ranking-an-arrays-values-from-1-to-10-java for ranking the names

https://docs.oracle.com/javase/8/docs/api/java/io/FileFilter.html For building a FileNameFilter

http://www.regexplanet.com/advanced/java/index.html For testing out Regex

http://www.avajava.com/tutorials/lessons/how-do-i-use-a-filenamefilter-to-display-a-subset-of-files-in-a-directory.html also for FileNameFilter
### Running the Program

Main class: Main is the main class, the main method calls all of the other classes/methods
Simply Run main, and modify the name, gender, start year, endyear, and fname depending on the question that you want to answer

Data files needed: 
Have a local directory in the data folder called "ssa_complete", and within that directory make sure that you have Census files that contain a 4 digit contiguous year,
for instance you can have files that are called yob1993.txt, boy1993.txt, girl1993.txt, so long as there is a 4 digit string that corresponds to the year somewhere in the file name

For testing, the test files in the tests directory in the data folder provide very simple test cases where Helen is the most popular Girl's name, with the highest rank, and as such was very easy to test
Key/Mouse inputs:

Cheat keys:

Known Bugs:
If a name is selected that is not present in Census files, this will cause an error for questions 1 and 2. If a year is selected that is not present in any of the file names, this will cause errors for question 3 and 4

Extra credit:


### Notes/Assumptions


### Impressions~~~~

