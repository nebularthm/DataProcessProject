package names;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static names.Main.allRanks;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    public void testAllRank() throws FileNotFoundException {
        String fname = "D:/ActualDesktop/cs307/data_mw376/data/tests";
        ArrayList<Integer> tests = new ArrayList<>();
        tests.add(1);
        tests.add(1);
        Assert.assertEquals(tests, allRanks("Helen","F",fname) );
    }


}