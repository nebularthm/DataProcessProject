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
    @Test
    public void testAllRank() throws FileNotFoundException {

        ArrayList<Integer> tests = new ArrayList<>();
        tests.add(1);//for all 3 files in our test, Helen is the #1 baby name, so we need to have something of size 3 here
        tests.add(1);
        tests.add(1);

        Assert.assertEquals(tests.size(), allRanks("Helen","F",fname).size() );// tests to see if allRanks produces an array of the proper size
        Assert.assertEquals(tests, allRanks("Helen","F",fname) );//see if the contents of the
    }
    @Test
    public void testSameRank() throws FileNotFoundException{
    Baby tester = new Baby(100000,"Helen","F");
    Baby testee = sameRank("Helen","F","1990",fname);
    Assert.assertEquals(tester.getFrequency(), testee.getFrequency() );// These 3 tests determine if the baby object produced by the sameRank method is was it should be
    Assert.assertEquals(tester.getGender(),testee.getGender());
    Assert.assertEquals(tester.getName(),testee.getName());
    }
    @Test
    public void testCommonNameRange() throws FileNotFoundException{
    ArrayList<Baby> test = new ArrayList<>();
    Baby helen = new Baby(100000,"Helen","F");
    test.add(helen);
    test.add(helen);
    ArrayList<Baby>testee = commonNameRange("1990","1991", "F", fname);
        System.out.println(babyListString(testee));
        System.out.println(babyListString(test));
    Assert.assertEquals(test.size(),testee.size());// preliminary test to see if this method returns an arraylist of the correct size
    Assert.assertEquals(babyListString(test),babyListString(testee));//prelimary test to check the contents of both of these arraylists, make sure to convert this to a an ArrayList of strings, baby comparisons do not work

    }


}