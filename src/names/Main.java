package names;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
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
    final static String END_YEAR = "2017";
    final static String START = "1880";
    final static String male = "M";
    final static String female = "F";
    final static  String siteURL = "https://www2.cs.duke.edu/courses/spring20/compsci307d/assign/01_data/data/ssa_complete/";
    final static String siteMeaningURL = "https://www2.cs.duke.edu/courses/compsci307d/spring20/assign/01_data/data/baby_name_meanings.txt";
    public static boolean yearCheck(String year){
        int lowest = convertYear(START);
        int highest = convertYear(END_YEAR);
        return convertYear(year) >= lowest && convertYear(year) <= highest;
    }
    public static boolean fileCheck(File file){
        if(!file.isFile()){
            return false;
        }
        return file.length() != 0;//means file has no characters so we can't do anything with it anyway
    }
    public static ArrayList<Baby> babyGenderRange(String gender, String startYear, int rank, String endYear, String fname){
        if(!gender.equals(male) && !gender.equals(female)){//if gender is not one of these then its a bust
            return new ArrayList<>();
        }
        if(!yearCheck(startYear)){//check bounds
            return new ArrayList<>();
        }
        if(!yearCheck(endYear)){
            return new ArrayList<>();
        }
        ArrayList<Baby> sameBaby = new ArrayList<>();
        int [] babyRanks;
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());//get the directory contents
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);//convert our ears to ints
        assert years != null;
        for(File year: years) {
            if(!fileCheck(year)){//invalid file so skip
                continue;
            }
            int thisYear = convertYear(year.getName());
            if (lowerYear <= thisYear && thisYear <= upperyear) { //inclusive range check
                ArrayList<Baby> tempList = new ArrayList<>();
                updateBabyList(year, tempList);
                tempList.sort(new FreqSort());
                Collections.reverse(tempList);// get descending order
                babyRanks = rankArray(tempList);
                for(int i = 0; i < babyRanks.length;i++){
                    if(babyRanks[i] == rank){
                        if(tempList.get(i).getGender().equals(gender))//check gender
                        sameBaby.add(tempList.get(i));
                    }
                }
            }
        }
        return sameBaby;
    }
    public static ArrayList<String>  meaningList() throws IOException {//this method just gets the tneire text file essentially
        URL nameSite = new URL(siteMeaningURL);
        ArrayList<String> siteLines = new ArrayList<>();
        BufferedReader siteReader = new BufferedReader(new InputStreamReader(nameSite.openConnection().getInputStream()));
        String siteLine = "";
        while((siteLine = siteReader.readLine()) != null){
            siteLines.add(siteLine);
        }
        siteReader.close();
        return siteLines;
    }
    public static ArrayList<String> mostCommonMeaning(String startYear, String endYear, String fname){
        if(!yearCheck(startYear)){
            return new ArrayList<>();
        }
        if(!yearCheck(endYear)){
            return new ArrayList<String>();
        }
        File  targetYear = new File(fname);
        File [] allYears = targetYear.listFiles(new YearFileFilter());
        ArrayList<Baby> topName = new ArrayList<>();
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        assert allYears != null;
        for(File year: allYears){
            if(!fileCheck(year)){
                continue;
            }
            int thisYear = convertYear(year.getName());
            ArrayList<Baby> tempList = new ArrayList<>();
            if( lowerYear<= thisYear && thisYear <= upperyear){
                updateBabyList(year, tempList);
                tempList.sort(new FreqSort());
                Collections.reverse(tempList);
            }
                 if(tempList.size() != 0)
                topName.add(tempList.get(0));
        }

        HashMap<Baby,Integer> babyFreq = new HashMap<>();
        for(Baby babe:topName){
            babyFreq.putIfAbsent(babe,Collections.frequency(topName,babe));
        }
        Integer maxName = Collections.max(babyFreq.values());
        ArrayList<Baby> topNames   = new ArrayList<>();
        for(Baby name: babyFreq.keySet()){
            if(babyFreq.get(name).equals(maxName)){
                topNames.add(name);
            }
        }
        ArrayList<String> nameMeanings = null;

        try {
            nameMeanings = meaningList();
        } catch (IOException e) {
            return new ArrayList<>();
        }

        ArrayList<String> retList = new ArrayList<>();
        for(Baby babe:topNames){
            for(String meaning:nameMeanings){
                String namebound = babe.getName().toLowerCase() + " ";
                if(meaning.toLowerCase().startsWith(namebound)){
                    retList.add(meaning);
                }
            }
        }
        return retList;
    }
    public static HashMap<String,Integer> sameRankInRange(String startYear, String endYear, int rank, String fname){
        if(!yearCheck(startYear)){
            return new HashMap<>();
        }
        if(!yearCheck(endYear)){
            return new HashMap<>();
        }

        HashMap<Baby,Integer> sameName = new HashMap<>();
        ArrayList<ArrayList<Baby>> sameBaby = new ArrayList<>();
        HashMap<String,Integer> sameRankBaby = new HashMap<>();
        int [] babyRanks;
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        assert years != null;
        for(File year: years) {
            if(!fileCheck(year)){
                continue;
            }
            int thisYear = convertYear(year.getName());
            if (lowerYear <= thisYear && thisYear <= upperyear) { //inclusive range check
                ArrayList<Baby> tempList = new ArrayList<>();
                ArrayList<Baby> ourBabies = new ArrayList<>();
                updateBabyList(year, tempList);
                tempList.sort(new FreqSort());
                Collections.reverse(tempList);// get descending order
                babyRanks = rankArray(tempList);
                for(int i = 0; i < babyRanks.length;i++){
                    if(babyRanks[i] == rank){
                    ourBabies.add(tempList.get(i));
                    }
                }
                sameBaby.add(ourBabies);
            }
        }
        for(ArrayList<Baby> year: sameBaby){
            for(Baby babe:year){
                sameName.putIfAbsent(babe,0);
                sameName.put(babe,sameName.get(babe) + 1);
            }
        }
        Integer maxBaby = Collections.max(sameName.values());
        for(Baby babe:sameName.keySet()){
            if(sameName.get(babe).equals(maxBaby)){
                sameRankBaby.putIfAbsent(babe.strinConv(),0);
                sameRankBaby.put(babe.strinConv(),sameName.get(babe) + 1);
            }
        }
        return sameRankBaby;

    }
    public static int urlReader(String url) throws IOException {
        URL nameSite = new URL(url);
        ArrayList<String> siteLines = new ArrayList<>();
        BufferedReader siteReader = new BufferedReader(new InputStreamReader(nameSite.openConnection().getInputStream()));
        String siteLine = "";
        while((siteLine = siteReader.readLine()) != null){
            if(siteLine.contains("href=\"yob")){
                siteLines.add(siteLine);
            }
        }
        siteReader.close();
        System.out.println(siteLines);
        return 1;
    }
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
            if(babe.getGender().equals(gender) && babe.getName().toLowerCase().equals(name.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public static String mostVolatile(String startYear, String endYear, String fname){
        if(!yearCheck(startYear)){
            return "";
        }
        if(!yearCheck(endYear)){
            return "";
        }
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        ArrayList<Baby> startList = new ArrayList<>();
        ArrayList<Integer> rankChangeList = new ArrayList<>();
        assert years != null;
        for(File year: years) {
            if(!fileCheck(year)){
                continue;
            }
            int thisYear = convertYear(year.getName());
            if (thisYear == lowerYear) { //inclusive range check
                updateBabyList(year, startList);
                startList.sort(new FreqSort());
                Collections.reverse(startList);// get descending order
                break;
            }
        }
        for(Baby babe: startList){
            int thisRankChange = firstLastDiff(babe.getName(),babe.getGender(),startYear,endYear,fname);
            rankChangeList.add(Math.abs(thisRankChange));
        }
        return startList.get(rankChangeList.indexOf(Collections.max(rankChangeList))).getName();
    }
    public static int firstLastDiff(String name, String gender, String startYear, String endYear,String fname) {
        if(!yearCheck(startYear)){
            return -1;
        }
        if(!yearCheck(endYear)){
            return -1;
        }
        if(!gender.equals(male) && !gender.equals(female)){
            return -1;
        }
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        int startRank = 0;
        int endRank = 0;
        assert years != null;
        for(File year: years) {
            if(!fileCheck(year)){
                continue;
            }
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
    public static ArrayList<Integer> recentRanks(String name, int numYears, String fname){
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
            if(!fileCheck(year)){
                continue;
            }
            ArrayList<Baby> tempList = new ArrayList<>();
            updateBabyList(year, tempList);
            tempList.sort(new FreqSort());
            Collections.reverse(tempList);// get descending order
            int ourRank = babyNameRank(name, male, tempList);// get the rank fro this year
            int ourSecondRank = babyNameRank(name,female,tempList);
            if(ourRank != 0)
            ourName.add(ourRank);
            if(ourSecondRank != 0)
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

     * @param temp list of baby objects
     * @return returns the actual array of ranks
     */
    private static int[] rankArray(ArrayList<Baby> temp){
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
            if(babe.getName().toLowerCase().equals(name.toLowerCase()) && babe.getGender().equals(gender)){
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

     */
    public static void updateBabyList(File year, ArrayList<Baby> babyList) {
        Scanner yearFile = null;
        try {
            yearFile = new Scanner(year);
        } catch (FileNotFoundException e) {
            return;
        }
        while (yearFile.hasNextLine()) {
            String nameLine = yearFile.nextLine();
            String[] babyParts = nameLine.split(",");
            int freq = Integer.parseInt(babyParts[2]);
            Baby daBaby = new Baby(freq, babyParts[0], babyParts[1]);
            babyList.add(daBaby);

        }

        yearFile.close();
    }
    public static ArrayList<Integer> ranksRangeNoGender(String name, String startYear, String endYear, String fname) {
        if(!yearCheck(startYear)){
            return new ArrayList<>();
        }
        if(!yearCheck(endYear)){
            return new ArrayList<>();
        }
        ArrayList<Integer> ourName = new ArrayList<>();
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        assert years != null;
        for(File year: years) {
            if(!fileCheck(year)){
                continue;
            }
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
    public static ArrayList<Integer> ranksRange(String name, String gender, String startYear, String endYear, String fname){
        if(!gender.equals(male) && !gender.equals(female)){
            return new ArrayList<Integer>();
        }
        if(!yearCheck(startYear)){
            return new ArrayList<>();
        }
        if(!yearCheck(endYear)){
            return new ArrayList<>();
        }
        ArrayList<Integer> ourName = new ArrayList<>();
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        assert years != null;
        for(File year: years) {
            if(!fileCheck(year)){
                continue;
            }
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

    public static int averageRank(ArrayList<Integer> ranks)  {
        int sum = 0;
        for(int rank:ranks){
            sum += rank;
        }
        if(ranks.size() == 0){
            return 0;
        }
        return sum / ranks.size();
    }

    /**
     *
     * @param name name of the baby
     * @param gender gender of the baby
     * @param fname name of our file
     * @return returns a list that contains the ranks of this name through the years

     */
    public static ArrayList<Integer> allRanks(String name, String gender, String fname)  {
        if(!gender.equals(male) && !gender.equals(female)){
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> ourName = new ArrayList<>();
        File nameDir = new File(fname);
        File[] years = nameDir.listFiles( new YearFileFilter());

        assert years != null;
        for(File year: years) {
            if(!fileCheck(year)){
                continue;
            }
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

     */
    public static Baby sameRank(String name, String gender, String year, String fname ) {
        if(!yearCheck(year)){
            return new Baby(0,"","") ;
        }
        if(!gender.equals(male) && !gender.equals(female)){
            return new Baby(0,"","");
        }
        File targetYear = new File(fname);
        File [] allYears = targetYear.listFiles( new YearFileFilter());
        ArrayList<Baby> tempList = new ArrayList<>();
        assert allYears != null;
        for(File file :allYears ){
            if(!fileCheck(file)){
                continue;
            }
            if(file.getName().contains(year)){
                updateBabyList(file,tempList);
                break;
            }

        }

        tempList.sort(new FreqSort());
        Collections.reverse(tempList);
        if(tempList.size() == 0){
            return new Baby(0,"","");
        }
        int ourRank = babyNameRank(name,gender,tempList);
        ArrayList<Baby> recentList = new ArrayList<>();
        for(File file:allYears){
            if(file.getName().contains(END_YEAR)){
                updateBabyList(file,recentList);
                break;
            }
        }
        recentList.sort(new FreqSort());
        Collections.reverse(recentList);
        int [] ranks = rankArray(recentList);
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
    public static ArrayList<Baby> commonNameRange(String startYear, String endYear, String gender ,String fname)  {

        if(!gender.equals(male) && !gender.equals(female)){
            return new ArrayList<Baby>();
        }
        if(!yearCheck(startYear)){
            return new ArrayList<>();
        }
        if(!yearCheck(endYear)){
            return new ArrayList<>();
        }
        File targetYear = new File(fname);
        File [] allYears = targetYear.listFiles(new YearFileFilter());
        ArrayList<Baby> topName = new ArrayList<>();
        int lowerYear = convertYear(startYear);
        int upperyear = convertYear(endYear);
        assert allYears != null;
        for(File year: allYears){
            if(!fileCheck(year)){//if File is invalid, just skip it
                continue;
            }
            int thisYear = convertYear(year.getName());
            ArrayList<Baby> tempList = new ArrayList<>();
            if( lowerYear<= thisYear && thisYear <= upperyear){//within this range
                updateBabyList(year, tempList);
                tempList.sort(new FreqSort());
                Collections.reverse(tempList);
            }
            for(Baby name:tempList){
                if(name.getGender().equals(gender)){
                    topName.add(name);//ladies and gentleman we got him
                    break;
                }
            }
        }

        return topName;
    }

    /**
     *
     * @param startYear The beginning and end of the r
     * @param endYear end of the range
     * @param gender Gender of interest, lets you switch between displaying most popular  names for not just girls, but boys as well
     * @param fname path to the directory that holds all of these diles
     * @return returns an arraylist of all the baby names that start with the most common letter

     */
    public static ArrayList<Baby> commonLetterRange(String startYear, String endYear, String gender,String fname) {
        if(!yearCheck(startYear)){
            return new ArrayList<>();
        }
        if(!yearCheck(endYear)){
            return new ArrayList<>();
        }
        if(!gender.equals(male) && !gender.equals(female)){
            return new ArrayList<Baby>();
        }
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
            if(!fileCheck(file)){
                continue;
            }
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
            babyFreq.putIfAbsent(babe,Collections.frequency(babyNames,babe));//to prevent multiple of the same baby
        }
        for(String name: babyFreq.keySet()){
            System.out.printf("For name %s ", name);//this portion just print out the counts for a name
            System.out.printf(" The count was %d %n", babyFreq.get(name));
        }
    }

    /**
     * Start of the program.
     */
    public static void main (String[] args)  {
            String name = "Helen";//these values are just for me to test main on the real dataset
            String gender = female;//I could have usrd a constant here
            String startYear = "1990";
            String endYear = "2001";
            String fname = "data/ssa_complete";
            int rank = 7;

            int numYears = 5;
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
        ArrayList<Integer> nameRanksRange = ranksRange(name,gender,startYear,endYear,fname);
        ArrayList<Integer> nameRanksNoGender = ranksRangeNoGender(name,startYear,endYear,fname);
        ArrayList<Integer> recentNameRanks =   recentRanks(name,numYears,fname);
        start = convertYear(startYear);
        for(Integer rankRange:nameRanksRange){//all of this prints for question 1
            System.out.printf("For year %d ", start);
            System.out.printf("The rank was %d", rankRange);
            System.out.printf(" for name %s%n", name);
            start++;
        }
        System.out.printf("The difference between year %s ", startYear );
        System.out.printf("and %s ", endYear);
        System.out.printf("for name %s ", name);
        System.out.printf("was %d %n", firstLastDiff(name,gender,startYear,endYear,fname));//question 2
        System.out.printf("Most volatile name was %s %n", mostVolatile(startYear,endYear,fname));//question 3
        System.out.printf("The average for this name %s  was ",  name);//question 4
        System.out.printf("%d %n", averageRank(nameRanksRange));
        System.out.printf("The average for this name regardless of gender name %s  was ",  name);//question 5
        System.out.printf("%d %n", averageRank(nameRanksNoGender));
        System.out.printf("The average for for most recent years name %s  was ",  name);//question 6
        System.out.printf("%d %n", averageRank(recentNameRanks));

        ArrayList<Baby> genderRange = babyGenderRange(gender,startYear, rank, endYear,fname);
        start = convertYear(startYear);
        for(String genderRank:babyListToString(genderRange)){//all of this prints for question 7
            System.out.printf("For year %d ", start);
            System.out.printf("The name  %s", genderRank);
            System.out.printf(" had rank %d %n", rank);
            start++;
        }
        HashMap<String,Integer> sameRankRange = sameRankInRange(startYear,endYear,rank,fname);//This is for Question 8
        System.out.println(sameRankRange);
        ArrayList<String> meanings = mostCommonMeaning(startYear,endYear,fname);//this is for queston 9
        for(String meaning:meanings){
            System.out.println(meaning);

        }
    }
}
