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

    @Test
    public void testAllRank() throws FileNotFoundException {

        ArrayList<Integer> tests = new ArrayList<>();
        tests.add(1);//for all 3 files in our test, Helen is the #1 baby name, so we need to have something of size 3 here
        tests.add(1);
        tests.add(1);

        Assert.assertEquals(tests.size(), allRanks(nameHelen,"F",fname).size() );// tests to see if allRanks produces an array of the proper size
        Assert.assertEquals(tests, allRanks(nameHelen,"F",fname) );//see if the contents of the
    }
    @Test
    public void testSameRank() throws FileNotFoundException{
    Baby tester = new Baby(100000,nameHelen,"F");
    Baby testee = sameRank(nameHelen,"F",start,fname);
    Assert.assertEquals(tester.getFrequency(), testee.getFrequency() );// These 3 tests determine if the baby object produced by the sameRank method is was it should be
    Assert.assertEquals(tester.getGender(),testee.getGender());
    Assert.assertEquals(tester.getName(),testee.getName());
    }
    @Test
    public void testCommonNameRange() throws FileNotFoundException{
    ArrayList<Baby> test = new ArrayList<>();
    Baby helen = new Baby(100000,nameHelen,"F");
    test.add(helen);
    test.add(helen);
    ArrayList<Baby>testee = commonNameRange(start,end, "F", fname);
    Assert.assertEquals(test.size(),testee.size());// preliminary test to see if this method returns an arraylist of the correct size
    Assert.assertEquals(babyListString(test),babyListString(testee));//prelimary test to check the contents of both of these arraylists, make sure to convert this to a an ArrayList of strings, baby comparisons do not work

    }
    @Test
    public void testCommonLetterRange() throws FileNotFoundException{
        ArrayList<Baby> test = new ArrayList<>();
        Baby helen = new Baby(100000, nameHelen,"F");
        Baby harriet = new Baby(28,"Harriet","F");
        test.add(helen);
        test.add(harriet);
        test.add(helen);
        test.add(harriet);
        ArrayList<Baby> testee = commonLetterRange(start,end, "F", fname);


        Assert.assertEquals(test.size(),testee.size());
        Assert.assertEquals(babyListString(test),babyListString(testee));//compare the contents of both lists
    }


}