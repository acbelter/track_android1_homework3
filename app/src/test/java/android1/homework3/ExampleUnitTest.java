package android1.homework3;

import com.android1.homework3.net.DataProcessor;
import com.android1.homework3.net.JSONDataProcessor;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void nestedJsonReaderTest() throws Exception {
        DataProcessor reader = new JSONDataProcessor();

        String data = "{\"id\":{\"id\":\"2\"}}";
        List<String> parts = reader.process(data);
        assertEquals(parts.size(), 1);

//        for (String part : parts) {
//            System.out.println(part);
//        }
//        System.out.println();
    }

    @Test
    public void twoJsonReaderTest() throws Exception {
        DataProcessor reader = new JSONDataProcessor();

        String data = "{\"id\":\"1\"}{\"id\":\"2\"}";
        List<String> parts = reader.process(data);
        assertEquals(parts.size(), 2);

//        for (String part : parts) {
//            System.out.println(part);
//        }
//        System.out.println();
    }

    @Test
    public void incompleteJsonReaderTest() throws Exception {
        DataProcessor reader = new JSONDataProcessor();

        String data = "{\"id\":\"1\"";
        List<String> parts = reader.process(data);
        assertNull(parts);

//        for (String part : parts) {
//            System.out.println(part);
//        }
//        System.out.println();
    }

    @Test
    public void generalJsonReaderTest() throws Exception {
        DataProcessor reader = new JSONDataProcessor();
        String data = "{\"id\":\"1\"}";
        List<String> parts = reader.process(data);
        assertEquals(parts.size(), 1);

//        for (String part : parts) {
//            System.out.println(part);
//        }
//        System.out.println();
    }
}