package names;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static names.Main.*;


import static org.junit.jupiter.api.Assertions.*;

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

        ArrayList<Integer> tests = new ArrayList<>();
        tests.add(1);//for all 3 files in our test, Helen is the #1 baby name, so we need to have something of size 3 here
        tests.add(1);
        tests.add(1);

        Assert.assertEquals(tests.size(), allRanks(nameHelen,female,fname).size() );// tests to see if allRanks produces an array of the proper size
        Assert.assertEquals(tests, allRanks(nameHelen,female,fname) );//see if the contents of the
        tests.clear();
        tests.add(2);
        tests.add(2);
        tests.add(2);

        Assert.assertEquals(tests.size(), allRanks(nameHenry,male,fname).size() );// tests to see if allRanks produces an array of the proper size
        Assert.assertEquals(tests, allRanks(nameHenry,male,fname) );//see if the contents of the

    }
    @Test
    public void testSameRank() throws FileNotFoundException{
    Baby tester = new Baby(100000,nameHelen,female);
    Baby testee = sameRank(nameHelen,female,start,fname);
    Assert.assertEquals(tester.getFrequency(), testee.getFrequency() );// These 3 tests determine if the baby object produced by the sameRank method is was it should be
    Assert.assertEquals(tester.getGender(),testee.getGender());
    Assert.assertEquals(tester.getName(),testee.getName());
    tester = new Baby(10000, nameHenry, male);
    testee = sameRank(nameHenry,male,start,fname);
        Assert.assertEquals(tester.getFrequency(), testee.getFrequency() );// These 3 tests determine if the baby object produced by the sameRank method is was it should be
        Assert.assertEquals(tester.getGender(),testee.getGender());
    }
    @Test
    public void testCommonNameRange() throws FileNotFoundException{
    ArrayList<Baby> test = new ArrayList<>();
    Baby helen = new Baby(100000,nameHelen,female);
    test.add(helen);
    test.add(helen);
    ArrayList<Baby>testee = commonNameRange(start,end, female, fname);
    Assert.assertEquals(test.size(),testee.size());// preliminary test to see if this method returns an arraylist of the correct size
    Assert.assertEquals(babyListString(test),babyListString(testee));//prelimary test to check the contents of both of these arraylists, make sure to convert this to a an ArrayList of strings, baby comparisons do not work
        test.clear();
        testee.clear();
        Baby henry = new Baby(10000, nameHenry, male);
        test.add(henry);
        test.add(henry);
        testee = commonNameRange(start,end,male,fname);
        Assert.assertEquals(test.size(),testee.size());// preliminary test to see if this method returns an arraylist of the correct size
        Assert.assertEquals(babyListString(test),babyListString(testee));//prelimary test to check the contents of both of these arraylists, make sure to convert this to a an ArrayList of strin

    }
    @Test
    public void testCommonLetterRange() throws FileNotFoundException{
        ArrayList<Baby> test = new ArrayList<>();
        Baby helen = new Baby(100000, nameHelen,female);
        Baby harriet = new Baby(28,"Harriet",female);
        test.add(helen);
        test.add(harriet);
        test.add(helen);
        test.add(harriet);
        ArrayList<Baby> testee = commonLetterRange(start,end, female, fname);


        Assert.assertEquals(test.size(),testee.size());
        Assert.assertEquals(babyListString(test),babyListString(testee));//compare the contents of both lists
        test.clear();
        testee.clear();
        Baby henry = new Baby(10000, nameHenry, male);
        Baby hunter = new Baby(20,"Hunter",male);
        test.add(henry);
        test.add(hunter);
        test.add(henry);
        test.add(hunter);
        testee = commonLetterRange(start,end,male,fname);
        Assert.assertEquals(test.size(),testee.size());
        Assert.assertEquals(babyListString(test),babyListString(testee));//compare the contents of both lists


    }


}