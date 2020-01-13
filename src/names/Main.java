package names;


/**
 * Feel free to completely change this code or delete it entirely. 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This is the Baby class which, for the Basics portion, will merely store the name, Gender, and frequency of a given baby name
 * There will also be a toString method here of course
 */
class Baby{
    int frequency;
    String name;
    String gender;
    public Baby(int freq, String nam, String gen){
        this.frequency = freq;
        this.name = nam;
        this.gender = gen;

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

class FreqSort implements  Comparator<Baby>{
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
     *This method returns the given
     * @param name
     * @param gender
     * @param temp
     * @return
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
    private static int findName(String name, String gender, ArrayList<Baby> temp){
        for(Baby babe:temp){
            if(babe.name.equals(name) && babe.gender.equals(gender)){
                return temp.indexOf(babe);
            }
        }
        return 0;
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
            ArrayList<Integer> ourName = new ArrayList<Integer>();
            File nameDir = new File("D:/ActualDesktop/cs307/data_mw376/data/ssa_complete");
            File[] years = nameDir.listFiles();
            System.out.println(Arrays.toString(years));
            System.out.println(nameDir.isDirectory());
            for(File year: years){
                ArrayList<Baby> tempList = new ArrayList<>();

                if(year.getName().contains("yob")) {

                    Scanner s = new Scanner(year);
                    while(s.hasNextLine()){
                        String line = s.nextLine();

                        String[] babyParts = line.split(",");
                        System.out.println(Arrays.toString(babyParts));
                        int freq = Integer.parseInt(babyParts[2]);
                        Baby daBaby = new Baby(freq,babyParts[0],babyParts[1]);
                        tempList.add(daBaby);

                    }

                    s.close();
                }
                tempList.sort(new FreqSort());
                Collections.reverse(tempList);
                System.out.println(tempList.toString());
                int ourRank = rank(name,gender,tempList);
                ourName.add(ourRank);

            }
                int startYear = 1880;
            for(int i = 0; i < ourName.size();i++){
                System.out.printf("For year %d ", startYear);
                System.out.printf("The rank was %d %n", ourName.get(i));
            }

        }

        else if(choice == 2){
            Scanner option2 = new Scanner(System.in);
            System.out.println("Thank you for choosing option 1, please enter your desired name:");
            String name = option2.nextLine();
            System.out.println("Now enter the gender/sex of your baby:");
            String gender = option2.nextLine();
            System.out.println("Now enter the year of this name:");
            String year = option2.nextLine();
            option.close();
            option2.close();
            File targetYear = new File("D:/ActualDesktop/cs307/data_mw376/data/ssa_complete");
            File [] allYears = targetYear.listFiles();
            ArrayList<Baby> tempList = new ArrayList<>();
            for(File file :allYears ){
                if(file.getName().contains(year)){
                    Scanner s = new Scanner(file);
                    while(s.hasNextLine()){
                        String line = s.nextLine();

                        String[] babyParts = line.split(",");
                        System.out.println(Arrays.toString(babyParts));
                        int freq = Integer.parseInt(babyParts[2]);
                        Baby daBaby = new Baby(freq,babyParts[0],babyParts[1]);
                        tempList.add(daBaby);
                    }
                    break;
                }


            }
            tempList.sort(new FreqSort());
            Collections.reverse(tempList);
            System.out.println(tempList.toString());
            int ourRank = rank(name,gender,tempList);
            ArrayList<Baby> recentList = new ArrayList<>();
            for(File file:allYears){
                if(file.getName().contains(endYear)){
                    Scanner s = new Scanner(file);
                    while(s.hasNextLine()){
                        String line = s.nextLine();

                        String[] babyParts = line.split(",");
                        System.out.println(Arrays.toString(babyParts));
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
            System.out.println(matchBaby.toString());

        }

    }
}
