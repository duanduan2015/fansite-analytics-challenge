package test;
import org.junit.*;
import java.util.*;
import tools.*;
import logprocessing.*;

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

    @Test
    public void testLogEntry() {
        Assert.assertEquals("log_entry(210.238.40.43, time(Fri Jun 30 21:00:09 PDT 1995), http_req(method:GET, path:/), http_resp(code:200, length:7074))",
                LogParser.parseLogEntry("210.238.40.43 - - [01/Jul/1995:00:00:09 -0400] \"GET / HTTP/1.0\" 200 7074").toString());
        Assert.assertEquals("log_entry(204.120.229.63, time(Sat Jul 01 01:29:05 PDT 1995), http_req(method:GET, path:/history/history.html), http_resp(code:200, length:1502))",
                LogParser.parseLogEntry("204.120.229.63 - - [01/Jul/1995:04:29:05 -0400] \"GET /history/history.html        hqpao/hqpao_home.html HTTP/1.0\" 200 1502").toString());
        Assert.assertEquals("log_entry(nccse.gsfc.nasa.gov, time(Sat Jul 01 04:36:13 PDT 1995), http_req(method:GET, path:/shuttle/missions/missions.html), http_resp(code:200, length:8677))",
                LogParser.parseLogEntry("nccse.gsfc.nasa.gov - - [01/Jul/1995:07:36:13 -0400] \"GET /shuttle/missions/missions.html Shuttle Launches from Kennedy Space Center HTTP/1.0\" 200 8677").toString());
        Assert.assertEquals("log_entry(ix-nbw-nj1-22.ix.netcom.com, time(Sat Jul 01 07:42:09 PDT 1995), http_req(method:GET, path:/finger), http_resp(code:404, length:0))",
                LogParser.parseLogEntry("ix-nbw-nj1-22.ix.netcom.com - - [01/Jul/1995:10:42:09 -0400] \"GET /finger @net.com HTTP/1.0\" 404 -").toString());
        Assert.assertEquals("log_entry(gpotterpc.llnl.gov, time(Sat Jul 01 19:26:51 PDT 1995), http_req(method:GET, path:/htbin/wais.pl), http_resp(code:200, length:317))",
                LogParser.parseLogEntry("gpotterpc.llnl.gov - - [01/Jul/1995:22:26:51 -0400] \"GET /htbin/wais.pl?orbit sts71 HTTP/1.0\" 200 317").toString());
        Assert.assertEquals("log_entry(wxs6-7.worldaccess.nl, time(Sun Jul 02 05:09:27 PDT 1995), http_req(method:GET, path:/), http_resp(code:200, length:7074))",
                LogParser.parseLogEntry("wxs6-7.worldaccess.nl - - [02/Jul/1995:08:09:27 -0400] \"GET / /   HTTP/1.0\" 200 7074").toString());
        Assert.assertEquals("log_entry(wxs6-7.worldaccess.nl, time(Sun Jul 02 05:11:20 PDT 1995), http_req(method:GET, path:/), http_resp(code:200, length:7074))",
                LogParser.parseLogEntry("wxs6-7.worldaccess.nl - - [02/Jul/1995:08:11:20 -0400] \"GET / /facts/facts.html HTTP/1.0\" 200 7074").toString());
        Assert.assertEquals("log_entry(pipe3.nyc.pipeline.com, time(Sun Jul 02 19:24:41 PDT 1995), http_req(method:GET, path:/shuttle/countdown/dy), http_resp(code:404, length:0))",
                LogParser.parseLogEntry("pipe3.nyc.pipeline.com - - [02/Jul/1995:22:24:41 -0400] \"GET /shuttle/countdown/dy �?shuttle%20tracking HTTP/1.0\" 404 -").toString());
        Assert.assertEquals("log_entry(atl4-m52.ed.ac.uk, time(Mon Jul 03 11:01:19 PDT 1995), http_req(method:GET, path:/images/NASA-logosmall.gif), http_resp(code:200, length:786))",
                LogParser.parseLogEntry("atl4-m52.ed.ac.uk - - [03/Jul/1995:14:01:19 -0400] \"GET /images/NASA-logosmall.gif align=left HTTP/1.0\" 200 786").toString());
        Assert.assertEquals("log_entry(ad06-022.compuserve.com, time(Mon Jul 03 21:49:49 PDT 1995), http_req(method:GET, path:/elv/uplink.htm>This), http_resp(code:404, length:0))",
                LogParser.parseLogEntry("ad06-022.compuserve.com - - [04/Jul/1995:00:49:49 -0400] \"GET /elv/uplink.htm>This Atlas/Centaur launch will be covered LIVE via satellite.<a/>\" 404 -").toString());
        Assert.assertEquals("log_entry(ppp-236-100.neta.com, time(Tue Jul 04 08:35:40 PDT 1995), http_req(method:GET, path:/shuttle/technology/sts-newsref/sts-lcc.html), http_resp(code:200, length:32252))",
                LogParser.parseLogEntry("ppp-236-100.neta.com - - [04/Jul/1995:11:35:40 -0400] \"GET /shuttle/technology/sts-newsref/sts-lcc.html (31K) HTTP/1.0\" 200 32252").toString());
        Assert.assertEquals("log_entry(150.216.65.157, time(Fri Jul 07 05:57:53 PDT 1995), http_req(method:GET, path:/history/apollo), http_resp(code:302, length:0))",
                LogParser.parseLogEntry("150.216.65.157 - - [07/Jul/1995:08:57:53 -0400] \"GET /history//apollo 13.html HTTP/1.0\" 302 -").toString());
        Assert.assertEquals("log_entry(198.3.128.46, time(Fri Jul 07 07:16:10 PDT 1995), http_req(method:GET, path:/shuttle/missions/sts-71/images/KSC-95EC-0), http_resp(code:404, length:0))",
                LogParser.parseLogEntry("198.3.128.46 - - [07/Jul/1995:10:16:10 -0400] \"GET /shuttle/missions/sts-71/images/KSC-95EC-0 913.jpg HTTP/1.0\" 404 -").toString());
        Assert.assertEquals("log_entry(asgard.cs.colorado.edu, time(Fri Jul 07 11:17:47 PDT 1995), http_req(method:GET, path:/images/shuttle-patch.jpg), http_resp(code:200, length:162913))",
                LogParser.parseLogEntry("asgard.cs.colorado.edu - - [07/Jul/1995:14:17:47 -0400] \"GET /images/shuttle-patch.jpg \" 200 162913").toString());
        Assert.assertEquals("log_entry(asgard.cs.colorado.edu, time(Fri Jul 07 11:18:12 PDT 1995), http_req(method:GET, path:/images/), http_resp(code:200, length:17688))",
                LogParser.parseLogEntry("asgard.cs.colorado.edu - - [07/Jul/1995:14:18:12 -0400] \"GET /images/ \" 200 17688").toString());
        Assert.assertEquals("log_entry(ix-sj19-25.ix.netcom.com, time(Sat Jul 08 20:46:35 PDT 1995), http_req(method:GET, path:/HTTP/1.0), http_resp(code:404, length:0))",
                LogParser.parseLogEntry("ix-sj19-25.ix.netcom.com - - [08/Jul/1995:23:46:35 -0400] \"GET /HTTP/1.0 200 OKDate: Sunday, 09-Jul-95 03:42:07 GMTServer: NCSA/1.3MIME-version: 1.0Content-type: text/htmlMIME-Version: 1.0Server: NCSA-Lycos<html HTTP/1.0\" 404 -").toString());
        Assert.assertEquals("log_entry(192.107.38.192, time(Mon Jul 10 20:24:29 PDT 1995), http_req(method:GET, path:/shuttle/missions/sts-64/mission-sts-64.html), http_resp(code:200, length:58311))",
                LogParser.parseLogEntry("192.107.38.192 - - [10/Jul/1995:23:24:29 -0400] \"GET /shuttle/missions/sts-64/mission-sts-64.html (56K) HTTP/1.0\" 200 58311").toString());
        Assert.assertEquals("log_entry(blazemonger.pc.cc.cmu.edu, time(Wed Jul 12 21:18:43 PDT 1995), http_req(method:GET, path:/htbin/cdt_clock.pl), http_resp(code:200, length:752))",
                LogParser.parseLogEntry("blazemonger.pc.cc.cmu.edu - - [13/Jul/1995:00:18:43 -0400] \"GET /htbin/cdt_clock.pl HTTP/1.0From:  <berend@blazemonger.pc.cc.cmu.edu>\" 200 752").toString());
        Assert.assertEquals("log_entry(titan02f, time(Tue Jul 18 13:13:14 PDT 1995), http_req(method:GET, path:/htbin/wais.pl), http_resp(code:200, length:317))",
                LogParser.parseLogEntry("titan02f - - [18/Jul/1995:16:13:14 -0400] \"GET /htbin/wais.pl?Space Acceleration Measurement System HTTP/1.0\" 200 317").toString());
        Assert.assertEquals("log_entry(163.205.180.17, time(Fri Jul 21 09:13:56 PDT 1995), http_req(method:GET, path:/htbin/wais.pl), http_resp(code:200, length:317))",
                LogParser.parseLogEntry("163.205.180.17 - - [21/Jul/1995:12:13:56 -0400] \"GET /htbin/wais.pl?Space Acceleration Measurement System HTTP/1.0\" 200 317").toString());
        Assert.assertEquals("log_entry(oceana.sdsc.edu, time(Tue Jul 25 14:15:06 PDT 1995), http_req(method:GET, path:/welcome.html), http_resp(code:200, length:790))",
                LogParser.parseLogEntry("oceana.sdsc.edu - - [25/Jul/1995:17:15:06 -0400] \"GET /welcome.html \" 200 790").toString());
        Assert.assertEquals("log_entry(drjo014a102.embratel.net.br, time(Thu Jul 27 18:59:14 PDT 1995), http_req(method:GET, path:/www.umcc.umich.edu), http_resp(code:302, length:0))",
                LogParser.parseLogEntry("drjo014a102.embratel.net.br - - [27/Jul/1995:21:59:14 -0400] \"GET  //www.umcc.umich.edu HTTP/1.0\" 302 -").toString());
    }

    @Test
    public void testClientAddressMap() {

        ClientAddressMap cAddrMap = new ClientAddressMap();

        ClientAddress cAddr1 = new ClientDomainNameAddress("www.omg.com");
        ClientAddress cAddr2 = new ClientDomainNameAddress("www.hello.com");
        ClientAddress cAddr3 = new ClientIPv4Address("1.2.3.4");
        ClientAddress cAddr4 = new ClientIPv4Address("192.168.100.200");

        cAddrMap.put(cAddr1, 123);
        cAddrMap.put(cAddr2, 0);
        cAddrMap.put(cAddr3, 987);
        cAddrMap.put(cAddr4, -123);

        Assert.assertEquals(123, cAddrMap.get(cAddr1).intValue());
        Assert.assertEquals(0, cAddrMap.get(cAddr2).intValue());
        Assert.assertEquals(987, cAddrMap.get(cAddr3).intValue());
        Assert.assertEquals(-123, cAddrMap.get(cAddr4).intValue());

        Assert.assertTrue(cAddrMap.contains(cAddr1));
        Assert.assertTrue(cAddrMap.contains(cAddr2));
        Assert.assertTrue(cAddrMap.contains(cAddr3));
        Assert.assertTrue(cAddrMap.contains(cAddr4));

        Assert.assertFalse(cAddrMap.contains(new ClientDomainNameAddress("www.aloha.com")));
        Assert.assertFalse(cAddrMap.contains(new ClientIPv4Address("0.0.0.1")));
    }

}
