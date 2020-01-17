package names;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static names.Main.*;

public class MainTest {
    final static String fname = "data/tests";//this leads to the directory that contains all of our test files, where we know the answers
    final static String start = "1990";
    final static String end = "1991";
    final static String nameHelen = "Helen";
    final static String nameHenry = "Henry";
    final static String male = "M";
    final static String female = "F";

    @Test
    public void testAllRank() throws FileNotFoundException {

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
    public void testSameRank() throws FileNotFoundException{
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
    public void testCommonNameRange() throws FileNotFoundException{
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
    public void testCommonLetterRange() throws FileNotFoundException{
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


}