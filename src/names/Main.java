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

    }
}
public class Main {
    final static String endYear = "2017";

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
     * @param temp the list of possible baby names
     * @return an int that is the rank of our baby name
     */

    private static int rank(String name, String gender, ArrayList<Baby> temp){
        int ret = 0;
        int nameIndex = findName(name,gender,temp);
        int [] ranks = new int[temp.size()];

        ranks[0] = 1;
        for(int i = 1; i < ranks.length; i++){
            Baby first = temp.get(i);
            Baby secon = temp.get(i-1);

            if(first.frequency != secon.frequency){
                ranks[i] = i + 1;
            }
            else {
                ranks[i] = ranks[i - 1];
            }
        }
        ret = ranks[nameIndex];
        return ret;
    }

    /**
     * This method creates a  rank array that provides a ranking at an index that corresponds to a name in the babyList
     * @param name name of interest
     * @param gender desired gender
     * @param temp list of baby objects
     * @return
     */
    private static int[] rankArray(String name, String gender,ArrayList<Baby> temp){
        int nameIndex = findName(name,gender,temp);
        int [] ranks = new int[temp.size()];

        ranks[0] = 1;
        for(int i = 1; i < ranks.length; i++){
            Baby first = temp.get(i);
            Baby secon = temp.get(i-1);

            if(first.frequency != secon.frequency){
                ranks[i] = i + 1;
            }
            else {
                ranks[i] = ranks[i - 1];
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
                return temp.indexOf(babe);
            }
        }
        return 0;
    }

    /**
     * This method returns the proper string representation of a list of baby names
     * @param temp
     * @return returns a string arraylist that holds a certain thot
     */
    private static ArrayList<String> babyListString(ArrayList<Baby> temp){
        ArrayList<String> ret = new ArrayList<>();
        for(Baby baby: temp){
            String literal = baby.strinConv();
            ret.add(literal);
        }
        return ret;
    }
    /**
     * Start of the program.
     */
    public static void main (String[] args) throws FileNotFoundException {
        Scanner option = new Scanner(System.in);
        System.out.println("Hello world");
        System.out.println("Hello, welcome to the Baby Stats Program, please enter a number from 1-4 for a specific service");
        System.out.println("1 - Give me the all time rankings for this name and gender, no year or range of years specified ");
        System.out.println("2- Give me a name with a gender  that is just as common as this name and gender from a certain year ");
        System.out.println("3- Given a gender and range of years , what names were the most popular?");
        System.out.println("4- Given a range of years, what was the most popular letter for girls names? Boys? ");
        int choice = option.nextInt();
        if(choice == 1){
            Scanner option1 = new Scanner(System.in);
            System.out.println("Thank you for choosing option 1, please enter your desired name:");
            String name = option1.nextLine();
            System.out.println("Now enter the gender/sex of your baby:");
            String gender = option1.nextLine();
            option.close();
            option1.close();
            ArrayList<ArrayList<Baby>> nameList = new ArrayList<ArrayList<Baby>>();
            ArrayList<Integer> ourName = new ArrayList<>();
            File nameDir = new File("D:/ActualDesktop/cs307/data_mw376/data/ssa_complete");
            File[] years = nameDir.listFiles( new YearFileFilter());

            for(File year: years) {
                ArrayList<Baby> tempList = new ArrayList<>();

                    Scanner s = new Scanner(year);
                    while (s.hasNextLine()) {
                        String line = s.nextLine();

                        String[] babyParts = line.split(",");

                        int freq = Integer.parseInt(babyParts[2]);
                        Baby daBaby = new Baby(freq, babyParts[0], babyParts[1]);

                        tempList.add(daBaby);

                    }

                    s.close();
//
                    tempList.sort(new FreqSort());
                    Collections.reverse(tempList);

//
                    int ourRank = rank(name, gender, tempList);
//
                    ourName.add(ourRank);



            }



                int startYear = 1880;
                for (int i = 0; i < ourName.size(); i++) {
                    System.out.printf("For year %d ", startYear);
                    System.out.printf("The rank was %d %n", ourName.get(i));
                    startYear++;
                }


        }

        else if(choice == 2){
            Scanner option2 = new Scanner(System.in);
            System.out.println("Thank you for choosing option 2, please enter your desired name:");
            String name = option2.nextLine();
            System.out.println("Now enter the gender/sex of your baby:");
            String gender = option2.nextLine();
            System.out.println("Now enter the year of this name:");
            String year = option2.nextLine();
            option.close();
            option2.close();
            File targetYear = new File("D:/ActualDesktop/cs307/data_mw376/data/ssa_complete");
            File [] allYears = targetYear.listFiles( new YearFileFilter());
            ArrayList<Baby> tempList = new ArrayList<>();
            for(File file :allYears ){
                if(file.getName().contains(year)){
                    Scanner s = new Scanner(file);
                    while(s.hasNextLine()){
                        String line = s.nextLine();

                        String[] babyParts = line.split(",");

                        int freq = Integer.parseInt(babyParts[2]);
                        Baby daBaby = new Baby(freq,babyParts[0],babyParts[1]);
                        tempList.add(daBaby);
                    }
                    break;
                }


            }
            tempList.sort(new FreqSort());
            Collections.reverse(tempList);
            System.out.println(babyListString(tempList));
            int ourRank = rank(name,gender,tempList);
            ArrayList<Baby> recentList = new ArrayList<>();
            for(File file:allYears){
                if(file.getName().contains(endYear)){
                    Scanner s = new Scanner(file);
                    while(s.hasNextLine()){
                        String line = s.nextLine();

                        String[] babyParts = line.split(",");

                        int freq = Integer.parseInt(babyParts[2]);
                        Baby daBaby = new Baby(freq,babyParts[0],babyParts[1]);
                        recentList.add(daBaby);
                    }
                    break;

                }
            }
        recentList.sort(new FreqSort());
            Collections.reverse(tempList);
            int [] ranks = rankArray(name,gender,recentList);
            int matchRank = ranks[ourRank];
            Baby matchBaby = recentList.get(matchRank);
            System.out.println(matchBaby.strinConv());

        }


        else if(choice == 3){
            Scanner option3 = new Scanner(System.in);
            System.out.println("Thank you for choosing option 3, please enter desired start year:");
            String startYear = option3.nextLine();
            System.out.println("Now enter the end year:");
            String endYear = option3.nextLine();
            System.out.println("Now enter the gender of interest:");
            String gender = option3.nextLine();
            option.close();
            option3.close();
            File targetYear = new File("D:/ActualDesktop/cs307/data_mw376/data/ssa_complete");
            File [] allYears = targetYear.listFiles(new YearFileFilter());
            ArrayList<Baby> topName = new ArrayList<>();
            int lowerYear = convertYear(startYear);
            int upperyear = convertYear(endYear);
            for(File year: allYears){
                int thisYear = convertYear(year.getName());
                ArrayList<Baby> tempList = new ArrayList<>();
                if( lowerYear<= thisYear && thisYear <= upperyear){




                    Scanner s = new Scanner(year);
                    while (s.hasNextLine()) {
                        String line = s.nextLine();

                        String[] babyParts = line.split(",");

                        int freq = Integer.parseInt(babyParts[2]);
                        Baby daBaby = new Baby(freq, babyParts[0], babyParts[1]);

                        tempList.add(daBaby);

                    }

                    s.close();

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
            System.out.println(babyListString(topName));

        }
    else if(choice == 4){
            Scanner option3 = new Scanner(System.in);
            System.out.println("Thank you for choosing option 3, please enter desired start year:");
            String startYear = option3.nextLine();
            System.out.println("Now enter the end year:");
            String endYear = option3.nextLine();
            System.out.println("Now enter the gender of interest:");
            String gender = option3.nextLine();
            option.close();
            option3.close();
            File targetYear = new File("D:/ActualDesktop/cs307/data_mw376/data/ssa_complete");
            File [] allYears = targetYear.listFiles(new YearFileFilter());
            ArrayList<Baby> topName = new ArrayList<>();
            int lowerYear = convertYear(startYear);
            int upperyear = convertYear(endYear);
        }
    }
}
