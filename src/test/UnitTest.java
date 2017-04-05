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
}
