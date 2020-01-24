package names;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * This is the Baby class which, for the Basics portion, will merely store the name, Gender, and frequency of a given baby name
 * There will also be a toString method here of course
 */
class Baby{
    int frequency;
    String name;
    String gender;
    /**
     * This constructor creates a Baby object, which holds the name, gender, and frequency for a particular baby. This class can be modified further to also store the meaning for a baby
     * @param freq the frequency of this baby name
     * @param nam the actual baby name
     * @param gen the gender of this baby name
     */
    public Baby(int freq, String nam, String gen){
        this.frequency = freq;
        this.name = nam;
        this.gender = gen;

    }
    public String strinConv(){
        return this.name + ", "  + this.gender;
    }
    public String getName(){
        return this.name;
    }
    public String getGender(){
        return this.gender;
    }

    public int getFrequency() {
        return frequency;
    }
}

/**
 * This class is used to filter out files so that only files that contain 4 digit years are read in, this prevents reading in README's
 */
class YearFileFilter implements  FilenameFilter{
    @Override
    public boolean accept(File dir, String fname){
        return fname.matches("(.*)[0-9]{4}(.*)");
    }
}
class GenderSort implements Comparator<Baby>{
    public int compare(Baby favorite, Baby middle){
        String favGender = favorite.gender;
        String midGender = middle.gender;
        return favGender.compareTo(midGender);

    }
}
class NameSort implements Comparator<Baby>{
    public int compare(Baby favorite, Baby middle){
        String favName = favorite.name;
        String midName = middle.name;
        return favName.compareTo(midName);

    }
}
/**
 * This class is used to sort baby names based on thier frequency, this is useful just in case the data does not come in pre-sorted
 */
class FreqSort implements  Comparator<Baby>{
    @Override
    public int compare(Baby favorite, Baby middle){
        int favFreq = favorite.getFrequency();
        int midFreq = middle.getFrequency();
        return Integer.compare(favFreq, midFreq);

    }//Sorts by frequencyor coungt of a given baby name, just in case the names themselves are not sorted. Not necessary for basics, but good for the future
}
public class Main {
    final static String endYear = "2017";
    final static String START = "1880";
    final static String male = "M";
    final static String female = "F";
    /**
     *
     * @param fname either a filename or a string representation of a year
     * @return integer represenation of a year
     */
    private static int convertYear(String fname){
        return Integer.parseInt(fname.replaceAll("\\D", ""));
    }
    private static boolean validName(String name, String gender, ArrayList<Baby> babyList){

        for(Baby babe: babyList){
            if(babe.getGender().equals(gender) && babe.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public static int firstLastDiff(String name, String gender, String startYear, String endYear,String fname) throws FileNotFoundException{
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        int startRank = 0;
        int endRank = 0;
        assert years != null;
        for(File year: years) {
            int thisYear = convertYear(year.getName());
            if (thisYear == lowerYear) { //inclusive range check
                ArrayList<Baby> tempList = new ArrayList<>();
                updateBabyList(year, tempList);
                tempList.sort(new FreqSort());
                Collections.reverse(tempList);// get descending order
                startRank = babyNameRank(name, gender, tempList);// get the rank fro this year
            }
            else if(thisYear == upperyear){
                ArrayList<Baby> tempList = new ArrayList<>();
                updateBabyList(year, tempList);
                tempList.sort(new FreqSort());
                Collections.reverse(tempList);// get descending order
                endRank = babyNameRank(name,gender,tempList);
                break;
            }
        }
        return startRank - endRank;
    }
    public static ArrayList<Integer> recentRanks(String name, int numYears, String fname) throws FileNotFoundException{
        ArrayList<Integer> ourName = new ArrayList<>();
        File nameDir = new File(fname);
        File[] temp = nameDir.listFiles( new YearFileFilter());
        File [] years = new File[numYears];
        int counter = 0;
        for(int i = temp.length - numYears; i < temp.length; i++){
            years[counter] = temp[i];
            counter +=1;
        }
        assert years != null;
        for(File year: years) {
            ArrayList<Baby> tempList = new ArrayList<>();
            updateBabyList(year, tempList);
            tempList.sort(new FreqSort());
            Collections.reverse(tempList);// get descending order
            int ourRank = babyNameRank(name, male, tempList);// get the rank fro this year
            int ourSecondRank = babyNameRank(name,female,tempList);
            ourName.add(ourRank);
            ourName.add(ourSecondRank);
        }
        return ourName;
    }
    /**
     *This method returns the rank of the given name and gender
     * @param name represents the baby name that we are trying to rank
     * @param gender gender for the baby name
     * @param temp the sorted(descending) list of possible baby names
     * @return an int that is the rank of our baby name
     */

    private static int babyNameRank(String name, String gender, ArrayList<Baby> temp){
        int nameIndex = findName(name,gender,temp); // find the index of our name in the original baby array list
        if(!validName(name,gender,temp)){
            return 0;
        }
        int [] ranks = new int[temp.size()];//the rank array
        ranks[0] = 1;// the first rank will always be 1
        for(int i = 1; i < ranks.length; i++){// iterate through ranks array and update
            Baby prev = temp.get(i-1);//get previous rank
            Baby curr = temp.get(i);// get current rank
            if(curr.getFrequency() != prev.getFrequency()){
                ranks[i] = i + 1;  //if these two baby objects have different frequency, then the current object must have a lower rank, so increment
            }
            else {
                ranks[i] = ranks[i - 1];//otherwise, it has the same rank as what came previous
            }
        }
        return ranks[nameIndex];//return our rank
    }
    /**
     * This method creates a  rank array that provides a ranking at an index that corresponds to a name in the babyList
     * @param name name of interest
     * @param gender desired gender
     * @param temp list of baby objects
     * @return returns the actual array of ranks
     */
    private static int[] rankArray(String name, String gender,ArrayList<Baby> temp){
        int [] ranks = new int[temp.size()];

        ranks[0] = 1;
        for(int i = 1; i < ranks.length; i++){
            Baby curr = temp.get(i);//current baby
            Baby prev = temp.get(i-1);

            if(curr.getFrequency() != prev.getFrequency()){
                ranks[i] = i + 1;  //if these two baby objects have different frequency, then the current object must have a lower rank, so increment
            }
            else {
                ranks[i] = ranks[i - 1]; //otherwise, it has the same rank as what came previous
            }
        }
        return ranks;
    }

    /**
     *
     * @param name name of interest
     * @param gender  gendr of said name
     * @param temp list of baby objects
     * @return returns the index of a partciular name
     */
    private static int findName(String name, String gender, ArrayList<Baby> temp){
        for(Baby babe:temp){
            if(babe.getName().equals(name) && babe.getGender().equals(gender)){
                return temp.indexOf(babe); //if the name and gender are the same , this this name must be our name
            }
        }
        return 0;
    }

    /**
     * This method returns the proper string representation of a list of baby names
     * @param babyList an arrayList of babies
     * @return returns a string arraylist that holds a certain thot
     */
    public static ArrayList<String> babyListToString(ArrayList<Baby> babyList){
        ArrayList<String> ret = new ArrayList<>();
        for(Baby baby: babyList){
            String literal = baby.strinConv();
            ret.add(literal);
        }
        return ret;
    }

    /**
     * this method reads in a file and puts all the baby names from that file into a stucture that stores Baby objects
     * @param year represents the year in file
     * @param babyList holds list of babies
     * @throws FileNotFoundException in case we don't have hte file
     */
    public static void updateBabyList(File year, ArrayList<Baby> babyList) throws FileNotFoundException {
        Scanner yearFile = new Scanner(year);
        while (yearFile.hasNextLine()) {
            String nameLine = yearFile.nextLine();
            String[] babyParts = nameLine.split(",");
            int freq = Integer.parseInt(babyParts[2]);
            Baby daBaby = new Baby(freq, babyParts[0], babyParts[1]);
            babyList.add(daBaby);

        }

        yearFile.close();
    }
    public static ArrayList<Integer> ranksRangeNoGender(String name, String startYear, String endYear, String fname) throws FileNotFoundException{
        ArrayList<Integer> ourName = new ArrayList<>();
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        assert years != null;
        for(File year: years) {
            int thisYear = convertYear(year.getName());
            if (lowerYear <= thisYear && thisYear <= upperyear) { //inclusive range check
                ArrayList<Baby> tempList = new ArrayList<>();
                updateBabyList(year, tempList);
                tempList.sort(new FreqSort());
                Collections.reverse(tempList);// get descending order
                int ourRank = babyNameRank(name, male, tempList);// get the rank fro this year
                int ourSecondRank = babyNameRank(name,female,tempList);
                ourName.add(ourRank);
                ourName.add(ourSecondRank);
            }
        }
        return ourName;
    }
    /**
     * This method returns the ranks for this name for a given range of yyears, this is an advanced version of the allRanks method
     * @param name baby name
     * @param gender gender of the baby
     * @param startYear start of the year  range
     * @param endYear  end of the year range
     * @param fname     the file path/ URL for the directory of babyNames
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<Integer> ranksRange(String name, String gender, String startYear, String endYear, String fname) throws FileNotFoundException{
        ArrayList<Integer> ourName = new ArrayList<>();
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        assert years != null;
        for(File year: years) {
            int thisYear = convertYear(year.getName());
            if (lowerYear <= thisYear && thisYear <= upperyear) { //inclusive range check
                ArrayList<Baby> tempList = new ArrayList<>();
                updateBabyList(year, tempList);
                tempList.sort(new FreqSort());
                Collections.reverse(tempList);// get descending order
                int ourRank = babyNameRank(name, gender, tempList);// get the rank fro this year
                ourName.add(ourRank);
            }
        }
        return ourName;
    }

    public static int averageRank(ArrayList<Integer> ranks) throws FileNotFoundException {
        int sum = 0;
        for(int rank:ranks){
            sum += rank;
        }
        return sum / ranks.size();
    }

    /**
     *
     * @param name name of the baby
     * @param gender gender of the baby
     * @param fname name of our file
     * @return returns a list that contains the ranks of this name through the years
     * @throws FileNotFoundException in case w edon't have the file
     */
    public static ArrayList<Integer> allRanks(String name, String gender, String fname) throws FileNotFoundException {
        ArrayList<Integer> ourName = new ArrayList<>();
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());

        assert years != null;
        for(File year: years) {
            ArrayList<Baby> tempList = new ArrayList<>();
            updateBabyList(year, tempList);
            tempList.sort(new FreqSort());
            Collections.reverse(tempList);// get descending order
            int ourRank = babyNameRank(name, gender, tempList);// get the rank fro this year
            ourName.add(ourRank);
        }
        return ourName;
    }


    /**
     *
     * @param name baby name
     * @param gender baby gender
     * @param year year we are interested in
     * @param fname name of directory
     * @return Returns a baby that has the same rank as the one provided
     * @throws FileNotFoundException in case we don't find the file
     */
    public static Baby sameRank(String name, String gender, String year, String fname ) throws FileNotFoundException {
        File targetYear = new File(fname);
        File [] allYears = targetYear.listFiles( new YearFileFilter());
        ArrayList<Baby> tempList = new ArrayList<>();
        assert allYears != null;
        for(File file :allYears ){
            if(file.getName().contains(year)){
                updateBabyList(file,tempList);
                break;
            }

        }

        tempList.sort(new FreqSort());
        Collections.reverse(tempList);
        int ourRank = babyNameRank(name,gender,tempList);
        ArrayList<Baby> recentList = new ArrayList<>();
        for(File file:allYears){
            if(file.getName().contains(endYear)){
                updateBabyList(file,recentList);
                break;
            }
        }
        recentList.sort(new FreqSort());
        Collections.reverse(recentList);
        int [] ranks = rankArray(name,gender,recentList);
        int matchIndex = 0;
        for(int i = 0; i < ranks.length;i++){
            if(ranks[i] == ourRank){ // this block does not check for the same gender as that is not the goal of this questio
                matchIndex = i;
                break;
            }
        }
        return recentList.get(matchIndex);
    }

    /**
     *
     * @param startYear start of range
     * @param endYear end of range
     * @param gender gender of interest
     * @param fname name of the file of interest
     * @return returns an arrayList of the most popular name(s) over this time period, in he actual main method this list will be parsed to find the most popular name(s) over that span
     * @throws FileNotFoundException in case we don't find the file
     */
    public static ArrayList<Baby> commonNameRange(String startYear, String endYear, String gender ,String fname) throws FileNotFoundException {
        File targetYear = new File(fname);
        File [] allYears = targetYear.listFiles(new YearFileFilter());
        ArrayList<Baby> topName = new ArrayList<>();
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        assert allYears != null;
        for(File year: allYears){
            int thisYear = convertYear(year.getName());
            ArrayList<Baby> tempList = new ArrayList<>();
            if( lowerYear<= thisYear && thisYear <= upperyear){
                updateBabyList(year, tempList);
                tempList.sort(new FreqSort());
                Collections.reverse(tempList);
            }
            for(Baby name:tempList){
                if(name.getGender().equals(gender)){
                    topName.add(name);
                    break;
                }
            }
        }
        //System.out.println(babyListString(topName));
        return topName;
    }

    /**
     *
     * @param startYear The beginning and end of the r
     * @param endYear end of the range
     * @param gender Gender of interest, lets you switch between displaying most popular  names for not just girls, but boys as well
     * @param fname path to the directory that holds all of these diles
     * @return returns an arraylist of all the baby names that start with the most common letter
     * @throws FileNotFoundException in case we don't find the file
     */
    public static ArrayList<Baby> commonLetterRange(String startYear, String endYear, String gender,String fname) throws FileNotFoundException {
        ArrayList<Baby> topNames = commonNameRange(startYear,endYear,gender,fname);
        ArrayList<String> seenChar = new ArrayList<>();
        ArrayList<String> startingChars = new ArrayList<>();
        for(Baby babe:topNames){
            startingChars.add(babe.getName().substring(0,1));
        }
        int maxStart = -10000;//this index objectively won't exist
        String maxStartingChar = "";
        for(String chara: startingChars){
            if(!seenChar.contains(chara)){
                seenChar.add(chara);
                int temp = Collections.frequency(startingChars,chara);
                if(temp > maxStart){
                    maxStart = temp;
                    maxStartingChar = chara;
                }

            }
        }
        File targetYear = new File(fname);
        File [] allYears = targetYear.listFiles( new YearFileFilter());
        ArrayList<Baby> keptList = new ArrayList<>();
        assert allYears != null;
        for(File file :allYears ){
            ArrayList<Baby> tempList = new ArrayList<>();
            int bounds = convertYear(file.getName());
            int lowerYear = convertYear(startYear);
            int upperYear = convertYear(endYear);
                if(lowerYear <= bounds && bounds <= upperYear) {
                    updateBabyList(file, tempList);
                    for (Baby babe : tempList) {
                        if (babe.getName().startsWith(maxStartingChar) && babe.getGender().equals(gender)) {
                            keptList.add(babe);
                        }
                    }

                }
        }

        return keptList;

    }

    /**
     * This method simply prints the counts of the most popular baby names over a certain time frame
     * @param babies list of babies
     */
    public static void topBabyFreq(ArrayList<Baby> babies){
        HashMap<String,Integer> babyFreq = new HashMap<>();
        ArrayList<String> babyNames = babyListToString(babies);
        for(String babe:babyNames){
            babyFreq.putIfAbsent(babe,Collections.frequency(babyNames,babe));
        }
        for(String name: babyFreq.keySet()){
            System.out.printf("For name %s ", name);
            System.out.printf(" The count was %d %n", babyFreq.get(name));
        }
    }

    /**
     * Start of the program.
     */
    public static void main (String[] args) throws FileNotFoundException {
            String name = "Helen";//these values are just for me to test main on the real dataset
            String gender = female;
            String startYear = "1990";
            String endYear = "2001";
            String fname = "data/ssa_complete";
            ArrayList<Integer> allNameRanks = allRanks(name,gender,fname);
        int start = convertYear(START);
        for (Integer allNameRank : allNameRanks) {
            System.out.printf("For year %d ", start);
            System.out.printf("The rank was %d", allNameRank);
            System.out.printf(" for name %s%n", name);
            start++;
        }
            Baby justAsGood = sameRank(name,gender, startYear,fname);
        System.out.println(justAsGood.strinConv());
            ArrayList<Baby> topNames = commonNameRange(startYear, endYear, gender,fname);
        topBabyFreq(topNames);
            ArrayList<Baby> allNameStartWith = commonLetterRange(startYear,endYear,gender,fname);
        System.out.println(babyListToString(allNameStartWith));
    }
}
