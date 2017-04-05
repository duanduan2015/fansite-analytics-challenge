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

    @Test
    public void testPrefixTree() {

        PrefixTree<ResourcePath, Integer> tmap = new PrefixTree<ResourcePath, Integer>();

        ResourcePath rp1 = new ResourcePath("/a/b/c");
        ResourcePath rp2 = new ResourcePath("/a/b/d");
        ResourcePath rp3 = new ResourcePath("/a/b");
        ResourcePath rp4 = new ResourcePath("/");

        tmap.put(rp1, 1);
        tmap.put(rp2, 2);
        tmap.put(rp3, 3);
        tmap.put(rp4, 4);

        Assert.assertEquals(1, tmap.get(rp1).intValue());
        Assert.assertEquals(2, tmap.get(rp2).intValue());
        Assert.assertEquals(3, tmap.get(rp3).intValue());
        Assert.assertEquals(4, tmap.get(rp4).intValue());

        tmap.put(rp4, 9);
        Assert.assertEquals(9, tmap.get(rp4).intValue());

        Assert.assertTrue(tmap.contains(new ResourcePath("a/b/c")));
        Assert.assertTrue(tmap.contains(new ResourcePath("")));

        Assert.assertFalse(tmap.contains(new ResourcePath("a")));
        Assert.assertFalse(tmap.contains(new ResourcePath("/a")));
        Assert.assertFalse(tmap.contains(new ResourcePath("/a/b/c/d")));

        tmap.put(rp1, 900);
        Assert.assertTrue(tmap.contains(new ResourcePath("a/b/c")));
        Assert.assertTrue(tmap.contains(new ResourcePath("a/b/d")));
        Assert.assertTrue(tmap.contains(new ResourcePath("a/b")));
    }

}
