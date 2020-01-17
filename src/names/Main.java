package names;


/**
 * Feel free to completely change this code or delete it entirely. 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
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
        return this.name + ", "  + this.gender + ", " + this.frequency;
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
        int favFreq = favorite.frequency;
        int midFreq = middle.frequency;
        if(favFreq == midFreq){
            return 0;
        }
        else if(favFreq > midFreq){
            return  1;
        }
        else{
            return -1;
        }

    }//Sorts by frequencyor coungt of a given baby name, just in case the names themselves are not sorted. Not necessary for basics, but good for the future
}
public class Main {
    final static String endYear = "2017";
    final static int START = 1880;
    /**
     *
     * @param fname either a filename or a string representation of a year
     * @return integer represenation of a year
     */
    private static int convertYear(String fname){
        return Integer.parseInt(fname.replaceAll("\\D", ""));
    }
    /**
     *This method returns the rank of the given name and gender
     * @param name represents the baby name that we are trying to rank
     * @param gender gender for the baby name
     * @param temp the sorted(descending) list of possible baby names
     * @return an int that is the rank of our baby name
     */

    private static int rank(String name, String gender, ArrayList<Baby> temp){
        int nameIndex = findName(name,gender,temp); // find the index of our name in the original baby array list
        int [] ranks = new int[temp.size()];//the rank array

        ranks[0] = 1;// the first rank will always be 1
        for(int i = 1; i < ranks.length; i++){// iterate through ranks array and update
            Baby secon = temp.get(i-1);//get previous rank
            Baby first = temp.get(i);// get current rank
            if(first.getFrequency() != secon.getFrequency()){
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
            Baby first = temp.get(i);
            Baby secon = temp.get(i-1);

            if(first.frequency != secon.frequency){
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
            if(babe.name.equals(name) && babe.gender.equals(gender)){
                return temp.indexOf(babe); //if the name and gender are the same , this this name must be our name
            }
        }
        return 0;
    }

    /**
     * This method returns the proper string representation of a list of baby names
     * @param temp
     * @return returns a string arraylist that holds a certain thot
     */
    public static ArrayList<String> babyListString(ArrayList<Baby> temp){
        ArrayList<String> ret = new ArrayList<>();
        for(Baby baby: temp){
            String literal = baby.strinConv();
            ret.add(literal);
        }
        return ret;
    }

    /**
     * this method reads in a file and puts all the baby names from that file into a stucture that stores Baby objects
     * @param year
     * @param tempList
     * @throws FileNotFoundException
     */
    public static void updateBabyList(File year, ArrayList<Baby> tempList) throws FileNotFoundException {
        Scanner s = new Scanner(year);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] babyParts = line.split(",");
            int freq = Integer.parseInt(babyParts[2]);
            Baby daBaby = new Baby(freq, babyParts[0], babyParts[1]);
            tempList.add(daBaby);

        }

        s.close();
    }

    /**
     *
     * @param name
     * @param gender
     * @param fname
     * @return returns a list that contains the ranks of this name through the years
     * @throws FileNotFoundException
     */
    public static ArrayList<Integer> allRanks(String name, String gender, String fname) throws FileNotFoundException {


        ArrayList<ArrayList<Baby>> nameList = new ArrayList<ArrayList<Baby>>();
        ArrayList<Integer> ourName = new ArrayList<>();
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());

        for(File year: years) {
            ArrayList<Baby> tempList = new ArrayList<>();
            updateBabyList(year, tempList);
            tempList.sort(new FreqSort());
            Collections.reverse(tempList);// get descending order
            int ourRank = rank(name, gender, tempList);// get the rank fro this year
            ourName.add(ourRank);
        }
        return ourName;

//        int startYear = START;
//        for (int i = 0; i < ourName.size(); i++) {
//            System.out.printf("For year %d ", startYear);
//            System.out.printf("The rank was %d %n", ourName.get(i));
//            startYear++;
//        }
    }

    /**
     *
     * @param name
     * @param gender
     * @param year
     * @param fname
     * @return Returns a baby that has the same rank as the one provided
     * @throws FileNotFoundException
     */
    public static Baby sameRank(String name, String gender, String year, String fname ) throws FileNotFoundException {
        File targetYear = new File(fname);
        File [] allYears = targetYear.listFiles( new YearFileFilter());
        ArrayList<Baby> tempList = new ArrayList<>();
        for(File file :allYears ){
            if(file.getName().contains(year)){
                updateBabyList(file,tempList);
                break;
            }

        }

        tempList.sort(new FreqSort());
        Collections.reverse(tempList);
        int ourRank = rank(name,gender,tempList);
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
        int matchRank = 0;
        int matchIndex = 0;
        for(int i = 0; i < ranks.length;i++){
            if(ranks[i] == ourRank){ // this block does not check for the same gender as that is not the goal of this question
                matchRank = ranks[i];
                matchIndex = i;
                break;
            }
        }
        Baby matchBaby = recentList.get(matchIndex);

        return matchBaby;
    }

    /**
     *
     * @param startYear
     * @param endYear
     * @param gender
     * @param fname
     * @return returns an arrayList of the most popular name(s) over this time period, in he actual main method this list will be parsed to find the most popular name(s) over that span
     * @throws FileNotFoundException
     */
    public static ArrayList<Baby> commonNameRange(String startYear, String endYear, String gender ,String fname) throws FileNotFoundException {
        File targetYear = new File(fname);
        File [] allYears = targetYear.listFiles(new YearFileFilter());
        ArrayList<Baby> topName = new ArrayList<>();
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
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
     * @param endYear
     * @param gender Gender of interest, lets you switch between displaying most popular  names for not just girls, but boys as well
     * @param fname path to the directory that holds all of these diles
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<Baby> commonLetterRange(String startYear, String endYear, String gender,String fname) throws FileNotFoundException {
        ArrayList<Baby> topNames = commonNameRange(startYear,endYear,gender,fname);
        ArrayList<String> seenChar = new ArrayList<>();
        ArrayList<String> startingChars = new ArrayList<>();
        for(Baby babe:topNames){
            startingChars.add(babe.getName().substring(0,1));
        }
        int maxStart = -10000;
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
        ArrayList<Baby> tempList = new ArrayList<>();
        ArrayList<Baby> keptList = new ArrayList<>();
        for(File file :allYears ){

                updateBabyList(file,tempList);
                for(Baby babe:tempList){
                    if(babe.getName().startsWith(maxStartingChar) && babe.getGender().equals(gender)){
                        keptList.add(babe);
                    }
                }
        }
        return keptList;

    }

    /**
     * Start of the program.
     */
    public static void main (String[] args) throws FileNotFoundException {
            String name = "Helen";
            String gender = "F";
            String startYear = "1990";
            String endYear = "2001";
            String fname = "data/ssa_complete";
            ArrayList<Integer> allNameRanks = allRanks(name,gender,fname);
        System.out.println(allNameRanks.toString());
            Baby justAsGood = sameRank(name,gender, startYear,fname);
        System.out.println(justAsGood.strinConv());
            ArrayList<Baby> topNames = commonNameRange(startYear, endYear, gender,fname);
        System.out.println(babyListString(topNames));
            ArrayList<Baby> allNameStartWith = commonLetterRange(startYear,endYear,gender,fname);
        System.out.println(babyListString(allNameStartWith));








    }




}
