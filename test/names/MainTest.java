package names;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static names.Main.*;
import names.Baby;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    final static String fname = "data/tests";//this leads to the directory that contains all of our test files, where we know the answers
    @Test
    public void testAllRank() throws FileNotFoundException {

        ArrayList<Integer> tests = new ArrayList<>();
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


}