package names;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static names.Main.*;

public class MainTest {
    final static String fname = "data/tests";//this leads to the directory that contains all of our test files, where we know the answers
    final static String start = "1990";
    final static String end = "1991";
    final static String nameHelen = "Helen";
    final static String nameHenry = "Henry";
    final static String male = "M";
    final static String female = "F";
    final static String tooLow = "1492";//this represents a yar that is outside of the range of years we have access to
    final static String tooHigh = "2150";//this represents a year that is much farther than any year in the data set
    final static int numYears = 3;
    @Test
    public void testAllRank(){

        ArrayList<Integer> allRankTester = new ArrayList<>();
        allRankTester.add(1);//for all 3 files in our test, Helen is the #1 baby name, so we need to have something of size 3 here
        allRankTester.add(1);
        allRankTester.add(1);
        Assert.assertEquals(allRankTester.size(), allRanks(nameHelen,female,fname).size() );// tests to see if allRanks produces an array of the proper size
        Assert.assertEquals(allRankTester, allRanks(nameHelen,female,fname) );//see if the contents of the
        allRankTester.clear();
        allRankTester.add(2);
        allRankTester.add(2);
        allRankTester.add(2);

        Assert.assertEquals(allRankTester.size(), allRanks(nameHenry,male,fname).size() );// tests to see if allRanks produces an array of the proper size
        Assert.assertEquals(allRankTester, allRanks(nameHenry,male,fname) );//see if the contents of the

    }
    @Test
    public void testSameRank(){
    Baby sameRankTester = new Baby(100000,nameHelen,female);
    Baby sameRankMethod = sameRank(nameHelen,female,start,fname);
    Assert.assertEquals(sameRankTester.getFrequency(), sameRankMethod.getFrequency() );// These 3 tests determine if the baby object produced by the sameRank method is was it should be
    Assert.assertEquals(sameRankTester.getGender(),sameRankMethod.getGender());
    Assert.assertEquals(sameRankTester.getName(),sameRankMethod.getName());
    sameRankTester = new Baby(10000, nameHenry, male);
    sameRankMethod = sameRank(nameHenry,male,start,fname);
        Assert.assertEquals(sameRankTester.getFrequency(), sameRankMethod.getFrequency() );// These 3 tests determine if the baby object produced by the sameRank method is was it should be
        Assert.assertEquals(sameRankTester.getGender(),sameRankMethod.getGender());
    }
    @Test
    public void testCommonNameRange(){
    ArrayList<Baby> commonNameRangeTester = new ArrayList<>();
    Baby helen = new Baby(100000,nameHelen,female);
    commonNameRangeTester.add(helen);// Helen is the number 1 ranked female in all the test files , so the most popular name should always be Helen
    commonNameRangeTester.add(helen);
    ArrayList<Baby>commonNameRangeMethod = commonNameRange(start,end, female, fname);
    Assert.assertEquals(commonNameRangeTester.size(),commonNameRangeMethod.size());// preliminary test to see if this method returns an arraylist of the correct size
    Assert.assertEquals(babyListToString(commonNameRangeTester), babyListToString(commonNameRangeMethod));//prelimary test to check the contents of both of these arraylists, make sure to convert this to a an ArrayList of strings, baby comparisons do not work
        commonNameRangeTester.clear();
        commonNameRangeMethod.clear();
        Baby henry = new Baby(10000, nameHenry, male);
        commonNameRangeTester.add(henry);//Henry is the number 1 ranked male in all the test files, so this method should always have Henry at the top
        commonNameRangeTester.add(henry);
        commonNameRangeMethod = commonNameRange(start,end,male,fname);
        Assert.assertEquals(commonNameRangeTester.size(),commonNameRangeMethod.size());// preliminary test to see if this method returns an arraylist of the correct size
        Assert.assertEquals(babyListToString(commonNameRangeTester), babyListToString(commonNameRangeMethod));//prelimary test to check the contents of both of these arraylists, make sure to convert this to a an ArrayList of strin

    }
    @Test
    public void testCommonLetterRange(){
        ArrayList<Baby> commonLetterTester = new ArrayList<>();
        Baby helen = new Baby(100000, nameHelen,female);
        Baby harriet = new Baby(28,"Harriet",female);
        commonLetterTester.add(helen);// for the testing files, the order for femalses is always Helen then HArriet, so over all files in range this has to be done twice
        commonLetterTester.add(harriet);
        commonLetterTester.add(helen);
        commonLetterTester.add(harriet);
        ArrayList<Baby> commonLetterMethod = commonLetterRange(start,end, female, fname);


        Assert.assertEquals(commonLetterTester.size(),commonLetterMethod.size());
        Assert.assertEquals(babyListToString(commonLetterTester), babyListToString(commonLetterMethod));//compare the contents of both lists
        commonLetterTester.clear();
        commonLetterMethod.clear();
        Baby henry = new Baby(10000, nameHenry, male);
        Baby hunter = new Baby(20,"Hunter",male);
        commonLetterTester.add(henry);//for the testing files, the order for males is always Henry then Hunter, so over all the files in range this has to be done twice
        commonLetterTester.add(hunter);
        commonLetterTester.add(henry);
        commonLetterTester.add(hunter);
        commonLetterMethod = commonLetterRange(start,end,male,fname);
        Assert.assertEquals(commonLetterTester.size(),commonLetterMethod.size());
        Assert.assertEquals(babyListToString(commonLetterTester), babyListToString(commonLetterMethod));//compare the contents of both lists


    }
    @Test
    public void testURL() throws IOException{
        Assert.assertEquals(1,urlReader("https://www2.cs.duke.edu/courses/spring20/compsci307d/assign/01_data/data/ssa_complete/"));//this test is not complete, it essentially just lets me see what URLReader is printing out to the console relatively easily
    }
    @Test
    public void testRanksRange(){
        ArrayList<Integer> rankRangeTester = new ArrayList<>();
        rankRangeTester.add(1);//for all 3 files in our test, Helen is the #1 baby name, so we need to have something of size 2 here fpr 1990 and 1991
        rankRangeTester.add(1);
        ArrayList<Integer>  rankRangeTestee = ranksRange(nameHelen,female,start,end,fname);
        Assert.assertEquals(rankRangeTester.size(),rankRangeTestee.size());// for now just compare the sizes of the returned array
        Assert.assertEquals(rankRangeTestee,rankRangeTester);//compare the contents of both arraylists
        ArrayList<Integer> badYears = ranksRange(nameHelen,female,tooLow,tooHigh,fname);
        Assert.assertEquals(new ArrayList<>(), badYears);//this test  checks to see if in the case of an improper year, this method returns an empty object
        Assert.assertEquals(new ArrayList<>(), ranksRange(nameHelen,"Q",start,end,fname));//this test check to see that with invalid gender, this method returns an empty list
        Assert.assertEquals(rankRangeTester,ranksRange("helen",female,start,end,fname));//this method checks that even though the provided name does not exactly match the case pf a name in the dataset, that name stil gets processed
        Assert.assertEquals(rankRangeTester,ranksRange(nameHelen,female,start,"1992",fname));//this method checks that empty files, such as yest1992.txt, are not processed if they are within the range of years

    }
    @Test
    public void testFirstLastDiff(){
        int firstLastTestee = firstLastDiff(nameHelen,female,start,end,fname);
        Assert.assertEquals(0,firstLastTestee);// for now just compare the retrurned value
        Assert.assertEquals(-1,firstLastDiff(nameHelen,female,tooLow,tooHigh,fname));//this test  checks to see if in the case of an improper year, this method returns an empty object
        Assert.assertEquals(-1, firstLastDiff(nameHelen,"Q",start,end,fname));//this test check to see that with invalid gender, this method returns an empty list
        Assert.assertEquals(0,firstLastDiff("helen",female,start,end,fname));//this method checks that even though the provided name does not exactly match the case pf a name in the dataset, that name stil gets processed
        Assert.assertEquals(1,firstLastDiff(nameHelen,female,start,"1992",fname));//this method checks that empty files, such as yest1992.txt, are not processed if they are within the range of years, this value is 1 because the end of the range has a value of 0

    }
    @Test
    public void testMostVolatile(){
    String mostVolatileTestee = mostVolatile(start,end,fname);
    Assert.assertEquals(nameHelen,mostVolatileTestee);// for now just compare the retrurned value
        Assert.assertEquals("",mostVolatile(tooLow,tooHigh,fname));//this test  checks to see if in the case of an improper year, this method returns an empty object
         Assert.assertEquals("Hunter",mostVolatile(start,"1992",fname));//this method checks that empty files, such as yest1992.txt, are not processed if they are within the range of years, this value is 1 because the end of the range has a value of 0


    }
    @Test
    public void testAverageOverRangeWithGender(){
        ArrayList<Integer> averageRangeTester = ranksRange(nameHelen,female,start,end,fname);
        int averageRangeTestee = averageRank(averageRangeTester);
        Assert.assertEquals(1,averageRangeTestee);// for now just compare the retrurned value
        Assert.assertEquals(0,averageRank(ranksRange(nameHelen,female,tooLow,tooHigh,fname)));//this test  checks to see if in the case of an improper year, this method returns an empty object
        Assert.assertEquals(0, averageRank(ranksRange(nameHelen,"Q",start,end,fname)));//this test check to see that with invalid gender, this method returns an empty list
        Assert.assertEquals(1,averageRank(ranksRange("helen",female,start,end,fname)));//this method checks that even though the provided name does not exactly match the case pf a name in the dataset, that name stil gets processed
        Assert.assertEquals(1,averageRank(ranksRange(nameHelen,female,start,"1992",fname)));//this method checks that empty files, such as yest1992.txt, are not processed if they are within the range of years, this value is 1 because the end of the range has a value of 0

    }
    @Test
    public void testAverageOverRangeNoGender(){
        ArrayList<Integer> averageRangeTester = ranksRangeNoGender(nameHelen,start,end,fname);
        int averageRangeTestee = averageRank(averageRangeTester);
        Assert.assertEquals(1,averageRangeTestee);// for now just compare the retrurned value
        Assert.assertEquals(0,averageRank(ranksRangeNoGender(nameHelen,tooLow,tooHigh,fname)));//this test  checks to see if in the case of an improper year, this method returns an empty object
        Assert.assertEquals(1,averageRank(ranksRangeNoGender("helen",start,end,fname)));//this method checks that even though the provided name does not exactly match the case pf a name in the dataset, that name stil gets processed
        Assert.assertEquals(1,averageRank(ranksRangeNoGender(nameHelen,start,"1992",fname)));//this method checks that empty files, such as yest1992.txt, are not processed if they are within the range of years, this value is 1 because the end of the range has a value of 0

    }
    @Test
    public void testRecentRanks(){
        ArrayList<Integer> averageRangeTester = recentRanks(nameHelen,numYears,fname);
        int averageRangeTestee = averageRank(averageRangeTester);
        Assert.assertEquals(1,averageRangeTestee);// for now just compare the retrurned value
        Assert.assertEquals(1,averageRank(recentRanks("helen",numYears,fname)));//this method checks that even though the provided name does not exactly match the case pf a name in the dataset, that name stil gets processed

    }
    @Test
    public void testbabyGenderRange(){
        ArrayList<Baby>  genderRangeTester = new ArrayList<>();
        Baby helen = new Baby(100000,nameHelen,female);
        genderRangeTester.add(helen);// Helen is the number 1 ranked female in all the test files , so the most popular name should always be Helen
        genderRangeTester.add(helen);
        ArrayList<Baby>  genderRangeTestee = babyGenderRange(female,start,1,end,fname);
        Assert.assertEquals(genderRangeTester.size(),genderRangeTestee.size());// for now just compare the sizes of the returned array
        Assert.assertEquals(babyListToString(genderRangeTestee), babyListToString(genderRangeTester));//compare the contents of both arraylists
        Assert.assertEquals(new ArrayList<>(), babyGenderRange(female,tooLow,1,tooHigh,fname));//this test  checks to see if in the case of an improper year, this method returns an empty object
        Assert.assertEquals(new ArrayList<>(), babyGenderRange("Q",start,1,end,fname));//this test check to see that with invalid gender, this method returns an empty list
        Assert.assertEquals(babyListToString(genderRangeTester),babyListToString(babyGenderRange(female, start, 1, "1992",fname)));//this method checks that empty files, such as yest1992.txt, are not processed if they are within the range of years

    }
    @Test
    public void testSameRankInRange(){
        HashMap<String,Integer> sameRankTester = new HashMap<>();
        sameRankTester.put("Helen, F",2);//this is the name at the top, it holds that position twice
        Assert.assertEquals(sameRankTester.size(),sameRankInRange(start,end,1,fname).size());//first see if both maps are of the same size
        Assert.assertEquals(sameRankTester,sameRankInRange(start,end,1,fname));//then check if both maps are of the same types
        Assert.assertEquals(new HashMap<>(), sameRankInRange(tooLow,tooHigh,1,fname));//check if this method works wehn year is out of range
        
    }


}