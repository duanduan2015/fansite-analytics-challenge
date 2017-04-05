package test;
import org.junit.*;
import java.util.*;
import tools.*;

public class UnitTest {
    @Test
    public void testResourcePath() {
        Assert.assertEquals("/a/b", (new ResourcePath("/a/b")).toString());
        Assert.assertEquals("/a/b/", (new ResourcePath("/a/b/")).toString());
        Assert.assertEquals("/a/b/", (new ResourcePath("/a/////b/")).toString());
        Assert.assertEquals("/", (new ResourcePath("/")).toString());
        Assert.assertEquals("/", (new ResourcePath("////////////")).toString());
        Assert.assertEquals("/a/bcd/asdfasdf/g", (new ResourcePath("/a/////bcd/asdfasdf/g")).toString());
        Assert.assertEquals("/a/bcd/asdfasdf/g/", (new ResourcePath("/a/////bcd/asdfasdf/g/")).toString());
        Assert.assertEquals("/", (new ResourcePath(" ")).toString());
        Assert.assertEquals("/", (new ResourcePath("    ")).toString());
        Assert.assertEquals("/aa", (new ResourcePath("  aa")).toString());
        Assert.assertEquals("/aa/bb/c", (new ResourcePath("  aa/bb/c")).toString());

        Assert.assertEquals(Arrays.asList("a", "b"), (new ResourcePath("/a/b")).getSegments());
        Assert.assertEquals(Arrays.asList("a", "b"), (new ResourcePath("a/b")).getSegments());
        Assert.assertEquals(Arrays.asList(), (new ResourcePath("/")).getSegments());
        Assert.assertEquals(Arrays.asList("asdf"), (new ResourcePath("/asdf")).getSegments());
        Assert.assertEquals(Arrays.asList("asdf", ""), (new ResourcePath("/asdf/")).getSegments());
        Assert.assertEquals(Arrays.asList("  a", "b  c", ""), (new ResourcePath("/  a/b  c/")).getSegments());
    }

    @Test
    public void testIPv4Address() {
        Assert.assertEquals("0.0.0.0", (new IPv4Address("0.0.0.0")).toString());
        Assert.assertEquals("100.100.100.100", (new IPv4Address("100.100.100.100")).toString());

        for (String s: Arrays.asList("256.0.0.0", "0", "", "0.0.0.-1", "hello.world.1.0")) {
            try {
                IPv4Address dummy = new IPv4Address(s);
                Assert.fail("IPv4Address('" + s + "') should throw " + IllegalArgumentException.class.getCanonicalName());
            } catch (IllegalArgumentException e) {}
        }
    }

}
