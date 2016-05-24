package android1.homework3;

import com.android1.homework3.net.JSONStreamProcessor;
import com.android1.homework3.net.StreamProcessor;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void nestedJsonReaderTest() throws Exception {
        final byte[] buf = new byte[1024 * 64];
        String data = "{\"id\":{\"id\":\"2\"}}";
        StreamProcessor reader = new JSONStreamProcessor();
        InputStream in = new ByteArrayInputStream(data.getBytes("UTF-8"));
        List<String> parts = reader.read(in, buf);
        assertEquals(parts.size(), 1);

//        for (String part : parts) {
//            System.out.println(part);
//        }
//        System.out.println();
    }

    @Test
    public void twoJsonReaderTest() throws Exception {
        final byte[] buf = new byte[1024 * 64];
        String data = "{\"id\":\"1\"}{\"id\":\"2\"}";
        StreamProcessor reader = new JSONStreamProcessor();
        InputStream in = new ByteArrayInputStream(data.getBytes("UTF-8"));
        List<String> parts = reader.read(in, buf);
        assertEquals(parts.size(), 2);

//        for (String part : parts) {
//            System.out.println(part);
//        }
//        System.out.println();
    }

    @Test
    public void incompleteJsonReaderTest() throws Exception {
        final byte[] buf = new byte[1024 * 64];
        String data = "{\"id\":\"1\"";
        StreamProcessor reader = new JSONStreamProcessor();
        InputStream in = new ByteArrayInputStream(data.getBytes("UTF-8"));
        List<String> parts = reader.read(in, buf);
        assertEquals(parts.size(), 0);

//        for (String part : parts) {
//            System.out.println(part);
//        }
//        System.out.println();
    }

    @Test
    public void generalJsonReaderTest() throws Exception {
        final byte[] buf = new byte[1024 * 64];
        String data = "{\"id\":\"1\"}";
        StreamProcessor reader = new JSONStreamProcessor();
        InputStream in = new ByteArrayInputStream(data.getBytes("UTF-8"));
        List<String> parts = reader.read(in, buf);
        assertEquals(parts.size(), 1);

//        for (String part : parts) {
//            System.out.println(part);
//        }
//        System.out.println();
    }
}