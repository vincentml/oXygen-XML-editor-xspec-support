package com.oxygenxml.xspec.jfx;

import java.io.File;
import java.net.URL;

import com.oxygenxml.xspec.XSpecUtil;

import ro.sync.util.URLUtil;

/**
 * Asserts the XSpec transformation meta data and the special report.
 *  
 * @author alex_jitianu
 */
public class XSpecMetaTest extends XSpecViewTestBase {

  /**
   * Run an XSpec file that has no failed asserts.
   * 
   * 1. To ensure back mapping we need to remember in which file a scenario was defined.
   * 
   * @throws Exception
   */
  public void testRunScenario_Passed() throws Exception {
    URL xspecURL = getClass().getClassLoader().getResource("meta/escape-for-regex.xspec");
    URL xslURL = getClass().getClassLoader().getResource("meta/escape-for-regex.xsl");
    File xspecFile = URLUtil.getCanonicalFileFromFileUrl(xspecURL);
    File outputFile = new File(xspecFile.getParentFile(), "escape-for-regex-report.html");
    
    executeANT(xspecFile, outputFile);
    
    File xmlFormatOutput = new File(xspecFile.getParentFile(), "xspec/escape-for-regex-result.xml");
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" + 
        "<x:report xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" + 
        "          xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" + 
        "          xmlns:test=\"http://www.jenitennison.com/xslt/unit-test\"\n" + 
        "          xmlns:x=\"http://www.jenitennison.com/xslt/xspec\"\n" + 
        "          xmlns:functx=\"http://www.functx.com\"\n" + 
        "          stylesheet=\"" + xslURL.toString() + "\"\n" + 
        "          date=\"XXX\">\n" + 
        // The source attribute is present.
        // To ensure back mapping we need to remember in which file a scenario was defined.
        "   <x:scenario source=\"" + xspecURL.toString() + "\">\n" + 
        "      <x:label>No escaping</x:label>\n" + 
        "      <x:call function=\"functx:escape-for-regex\">\n" + 
        "         <x:param select=\"'Hello'\"/>\n" + 
        "      </x:call>\n" + 
        "      <x:result select=\"'Hello'\"/>\n" + 
        "      <x:test successful=\"true\">\n" + 
        "         <x:label>Must not be escaped at all</x:label>\n" + 
        "         <x:expect select=\"'Hello'\"/>\n" + 
        "      </x:test>\n" + 
        "   </x:scenario>\n" + 
        "</x:report>", 
        read(xmlFormatOutput.toURI().toURL()).toString().replaceAll("date=\".*\"", "date=\"XXX\"").replaceAll("<\\?xml-stylesheet.*\\?>", ""));
    
    
    assertTrue(outputFile.exists());
    
    
    File css = new File("frameworks/xspec/oxygen-results-view/test-report.css");
    File js = new File("frameworks/xspec/oxygen-results-view/test-report.js");
    
    
    assertEquals(
        "<html xmlns:test=\"http://www.jenitennison.com/xslt/unit-test\">\n" + 
        "   <head>\n" + 
        "      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" + 
        "      <link rel=\"stylesheet\" type=\"text/css\" href=\"" + css.toURI().toURL().toString()
        + "\"><script type=\"text/javascript\" src=\""
        + js.toURI().toURL().toString() + "\"></script></head>\n" + 
        "   <body>\n" + 
        "      <div class=\"testsuite\" data-name=\"No escaping\" "
        
        + "data-source=\"" + xspecURL.toString() + "\" "
        + "data-tests=\"1\" "
        + "data-failures=\"0\">\n" + 
        "         <p style=\"margin:0px;\"><span>No escaping</span><span>&nbsp;</span><a class=\"button\" onclick=\"runScenario(this)\">Run</a></p>\n" + 
        "         <div class=\"testcase\" data-name=\"Must not be escaped at all\">\n" + 
        "            <p class=\"passed\"><span class=\"test-passed\" onclick=\"toggleResult(this)\">Must not be escaped at all</span><span>&nbsp;</span><a class=\"button\" onclick=\"showTest(this)\">Show</a></p>\n" + 
        "         </div>\n" + 
        "      </div>\n" + 
        "   </body>\n" + 
        "</html>", read(outputFile.toURI().toURL()).toString());
  }

  /**
   * Run an XSpec file that has failed asserts.
   * 
   * 1. To ensure back mapping we need to remember in which file a scenario was defined.
   * 
   * @throws Exception
   */
  public void testRunScenario_Failed() throws Exception {
    URL xspecURL = getClass().getClassLoader().getResource("meta/test.xspec");
    URL xslURL = getClass().getClassLoader().getResource("meta/escape-for-regex.xsl");
    File xspecFile = URLUtil.getCanonicalFileFromFileUrl(xspecURL);
    File outputFile = new File(xspecFile.getParentFile(), "test.html");
    File xmlFormatOutput = new File(xspecFile.getParentFile(), "xspec/test-result.xml");
    
    URL importedXSpecURL = getClass().getClassLoader().getResource("meta/escape-for-regex.xspec");
    
    executeANT(xspecFile, outputFile);
    
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" + 
        "<x:report xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" + 
        "          xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" + 
        "          xmlns:test=\"http://www.jenitennison.com/xslt/unit-test\"\n" + 
        "          xmlns:x=\"http://www.jenitennison.com/xslt/xspec\"\n" + 
        "          xmlns:functx=\"http://www.functx.com\"\n" + 
        "          stylesheet=\"" + xslURL.toString() + "\"\n" + 
        "          date=\"XXX\">\n" + 
        "   <x:scenario source=\"" + xspecFile.toURI().toURL().toString() + "\">\n" + 
        "      <x:label>When processing a list of phrases</x:label>\n" + 
        "      <x:context>\n" + 
        "         <phrases>\n" + 
        "            <phrase>Hello!</phrase>\n" + 
        "            <phrase>Goodbye!</phrase>\n" + 
        "            <phrase>(So long!)</phrase>\n" + 
        "         </phrases>\n" + 
        "      </x:context>\n" + 
        "      <x:result>\n" + 
        "         <phrases>\n" + 
        "            <phrase status=\"changed\">Hello!</phrase>\n" + 
        "            <phrase status=\"changed\">Goodbye!</phrase>\n" + 
        "            <phrase status=\"same\">\\(So long!\\)</phrase>\n" + 
        "         </phrases>\n" + 
        "      </x:result>\n" + 
        "      <x:test successful=\"true\">\n" + 
        "         <x:label>All phrase elements should remain</x:label>\n" + 
        "         <x:expect select=\"()\"/>\n" + 
        "      </x:test>\n" + 
        "      <x:test successful=\"false\">\n" + 
        "         <x:label>Strings should be escaped and status attributes should be added. The 'status' attribute are not as expected, indicating a problem in the tested template.</x:label>\n" + 
        "         <x:expect>\n" + 
        "            <phrases>\n" + 
        "               <phrase status=\"same\">Hello!</phrase>\n" + 
        "               <phrase status=\"same\">Goodbye!</phrase>\n" + 
        "               <phrase status=\"changed\">\\(So long!\\)</phrase>\n" + 
        "            </phrases>\n" + 
        "         </x:expect>\n" + 
        "      </x:test>\n" + 
        "   </x:scenario>\n" + 
        // The source attribute is present.
        // To ensure back mapping we need to remember in which file a scenario was defined.
        "   <x:scenario source=\"" + importedXSpecURL.toString() + "\">\n" + 
        "      <x:label>No escaping</x:label>\n" + 
        "      <x:call function=\"functx:escape-for-regex\">\n" + 
        "         <x:param select=\"'Hello'\"/>\n" + 
        "      </x:call>\n" + 
        "      <x:result select=\"'Hello'\"/>\n" + 
        "      <x:test successful=\"true\">\n" + 
        "         <x:label>Must not be escaped at all</x:label>\n" + 
        "         <x:expect select=\"'Hello'\"/>\n" + 
        "      </x:test>\n" + 
        "   </x:scenario>\n" + 
        "</x:report>", 
        read(xmlFormatOutput.toURI().toURL()).toString().replaceAll("date=\".*\"", "date=\"XXX\"").replaceAll("<\\?xml-stylesheet.*\\?>", ""));
    
    
    assertTrue(outputFile.exists());
    
    
    File css = new File("frameworks/xspec/oxygen-results-view/test-report.css");
    File js = new File("frameworks/xspec/oxygen-results-view/test-report.js");
    
    
    assertEquals(
        "<html xmlns:test=\"http://www.jenitennison.com/xslt/unit-test\">\n" + 
        "   <head>\n" + 
        "      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" + 
        "      <link rel=\"stylesheet\" type=\"text/css\" href=\"" + css.toURI().toURL().toString() + "\">"
            + "<script type=\"text/javascript\" src=\"" + js.toURI().toURL().toString() + "\"></script></head>\n" + 
        "   <body>\n" + 
        "      <div class=\"testsuite\" "
        // Scenario name
        + "data-name=\"When processing a list of phrases\" "
        // Scenario source
        + "data-source=\"" + xspecFile.toURI().toURL().toString() + "\" data-tests=\"2\" data-failures=\"1\">\n" + 
        "         <p style=\"margin:0px;\"><span>When processing a list of phrases</span><span>&nbsp;</span><a class=\"button\" onclick=\"runScenario(this)\">Run</a></p>\n" +
        // x:expect
        "         <div class=\"testcase\" data-name=\"All phrase elements should remain\">\n" + 
        "            <p class=\"passed\"><span class=\"test-passed\" onclick=\"toggleResult(this)\">All phrase elements should remain</span><span>&nbsp;</span><a class=\"button\" onclick=\"showTest(this)\">Show</a></p>\n" + 
        "         </div>\n" + 
        // x:expect
        "         <div class=\"testcase\" data-name=\"Strings should be escaped and status attributes should be added. The 'status' attribute are not as expected, indicating a problem in the tested template.\">\n" + 
        "            <p class=\"failed\">"
        // Toggle result on click
        + "<span class=\"test-failed\" onclick=\"toggleResult(this)\">Strings should be escaped and status attributes should be added. The 'status' attribute\n" + 
        "                  are not as expected, indicating a problem in the tested template.</span><span>&nbsp;</span>"
        // A button to locate the scenario inside the XSPEC source file.
        + "<a class=\"button\" onclick=\"showTest(this)\">Show</a><span>&nbsp;</span>"
        // A button to show the Q-DIFF.
        + "<a class=\"button\" onclick=\"toggleResult(this)\">Q-Diff</a><span>&nbsp;</span>"
        // A button to show the DIFF inside Oxygen's DIFF.
        + "<a class=\"button\" onclick=\"showDiff(this)\">Diff</a></p>\n" +
        // Q-DIFF data in HTML format.
        "            <div class=\"failure\" id=\"d3e47\" style=\"display:none;\">\n" + 
        "               <table class=\"xspecResult\">\n" + 
        "                  <thead>\n" + 
        "                     <tr>\n" + 
        "                        <th style=\"font-size:14px;\">Result</th>\n" + 
        "                        <th style=\"font-size:14px;\">Expected</th>\n" + 
        "                     </tr>\n" + 
        "                  </thead>\n" + 
        "                  <tbody>\n" + 
        "                     <tr>\n" + 
        "                        <td><pre>&lt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">phrases</span> xmlns:functx=\"http://www.functx.com\"&gt;\n" + 
        "   &lt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">phrase</span> <span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">status</span>=\"changed\"&gt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"same\">Hello!</span>&lt;/phrase&gt;\n" + 
        "   &lt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">phrase</span> <span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">status</span>=\"changed\"&gt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"same\">Goodbye!</span>&lt;/phrase&gt;\n" + 
        "   &lt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">phrase</span> <span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">status</span>=\"same\"&gt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"same\">\\(So long!\\)</span>&lt;/phrase&gt;\n" + 
        "&lt;/phrases&gt;</pre></td>\n" + 
        "                        <td><pre>&lt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">phrases</span> xmlns:functx=\"http://www.functx.com\"&gt;\n" + 
        "   &lt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">phrase</span> <span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">status</span>=\"same\"&gt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"same\">Hello!</span>&lt;/phrase&gt;\n" + 
        "   &lt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">phrase</span> <span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">status</span>=\"same\"&gt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"same\">Goodbye!</span>&lt;/phrase&gt;\n" + 
        "   &lt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">phrase</span> <span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"diff\">status</span>=\"changed\"&gt;<span xmlns:x=\"http://www.jenitennison.com/xslt/xspec\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:pkg=\"http://expath.org/ns/pkg\" class=\"same\">\\(So long!\\)</span>&lt;/phrase&gt;\n" + 
        "&lt;/phrases&gt;</pre></td>\n" + 
        "                     </tr>\n" + 
        "                  </tbody>\n" + 
        "               </table>\n" + 
        "            </div>"
        // DATA for the Oxygen DIFF.
        + "<pre class=\"embeded.diff.data\" style=\"display:none;\"><div class=\"embeded.diff.result\" style=\"white-space:pre;\">\n" + 
        "<phrases>\n" + 
        "   <phrase status=\"changed\">Hello!</phrase>\n" + 
        "   <phrase status=\"changed\">Goodbye!</phrase>\n" + 
        "   <phrase status=\"same\">\\(So long!\\)</phrase>\n" + 
        "</phrases>\n" + 
        "</div><div class=\"embeded.diff.expected\" style=\"white-space:pre;\">\n" + 
        "<phrases>\n" + 
        "   <phrase status=\"same\">Hello!</phrase>\n" + 
        "   <phrase status=\"same\">Goodbye!</phrase>\n" + 
        "   <phrase status=\"changed\">\\(So long!\\)</phrase>\n" + 
        "</phrases>\n" + 
        "</div></pre></div>\n" + 
        "      </div>\n" + 
        "      <div class=\"testsuite\" data-name=\"No escaping\" data-source=\"file:/C:/Users/alex_jitianu/Documents/Git-workspace/oXygen-XML-editor-xspec-plugin/target/test-classes/meta/escape-for-regex.xspec\" data-tests=\"1\" data-failures=\"0\">\n" + 
        "         <p style=\"margin:0px;\"><span>No escaping</span><span>&nbsp;</span><a class=\"button\" onclick=\"runScenario(this)\">Run</a></p>\n" + 
        "         <div class=\"testcase\" data-name=\"Must not be escaped at all\">\n" + 
        "            <p class=\"passed\"><span class=\"test-passed\" onclick=\"toggleResult(this)\">Must not be escaped at all</span><span>&nbsp;</span><a class=\"button\" onclick=\"showTest(this)\">Show</a></p>\n" + 
        "         </div>\n" + 
        "      </div>\n" + 
        "   </body>\n" + 
        "</html>", read(outputFile.toURI().toURL()).toString());
  }

  /**
   * We can specify which scenarios to be run.
   * 
   * @throws Exception
   */
  public void testRunSpecificScenarios_1() throws Exception {
    URL xspecURL = getClass().getClassLoader().getResource("meta/driverTest.xspec");
    URL xslURL = getClass().getClassLoader().getResource("meta/escape-for-regex.xsl");
    File xspecFile = URLUtil.getCanonicalFileFromFileUrl(xspecURL);
    File outputFile = new File(xspecFile.getParentFile(), "driverTest-report.html");
    
    executeANT(xspecFile, outputFile);
    
    File xmlFormatOutput = new File(xspecFile.getParentFile(), "xspec/driverTest-result.xml");
    
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "\n" + 
        "<x:report xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" + 
        "          xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" + 
        "          xmlns:test=\"http://www.jenitennison.com/xslt/unit-test\"\n" + 
        "          xmlns:x=\"http://www.jenitennison.com/xslt/xspec\"\n" + 
        "          xmlns:functx=\"http://www.functx.com\"\n" + 
        "          stylesheet=\"" + xslURL.toString() + "\"\n" + 
        "          date=\"XXX\">\n" + 
        "   <x:scenario source=\"" + xspecURL.toString() + "\">\n" + 
        "      <x:label>Test no.1</x:label>\n" + 
        "      <x:call function=\"functx:escape-for-regex\">\n" + 
        "         <x:param select=\"'Hello'\"/>\n" + 
        "      </x:call>\n" + 
        "      <x:result select=\"'Hello'\"/>\n" + 
        "      <x:test successful=\"true\">\n" + 
        "         <x:label>Must not be escaped at all</x:label>\n" + 
        "         <x:expect select=\"'Hello'\"/>\n" + 
        "      </x:test>\n" + 
        "   </x:scenario>\n" + 
        "   <x:scenario source=\"" + xspecURL.toString() + "\">\n" + 
        "      <x:label>Test no.2</x:label>\n" + 
        "      <x:call function=\"functx:escape-for-regex\">\n" + 
        "         <x:param select=\"'(Hello)'\"/>\n" + 
        "      </x:call>\n" + 
        "      <x:result select=\"'\\(Hello\\)'\"/>\n" + 
        "      <x:test successful=\"true\">\n" + 
        "         <x:label>Must not be escaped at all</x:label>\n" + 
        "         <x:expect select=\"'\\(Hello\\)'\"/>\n" + 
        "      </x:test>\n" + 
        "   </x:scenario>\n" + 
        "</x:report>", 
        read(xmlFormatOutput.toURI().toURL()).toString().replaceAll("date=\".*\"", "date=\"XXX\"").replaceAll("<\\?xml-stylesheet.*\\?>", ""));
    
    assertTrue(outputFile.exists());
    
    
    File css = new File("frameworks/xspec/oxygen-results-view/test-report.css");
    File js = new File("frameworks/xspec/oxygen-results-view/test-report.js");
    
    assertEquals(
        "<html xmlns:test=\"http://www.jenitennison.com/xslt/unit-test\">\n" + 
        "   <head>\n" + 
        "      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" + 
        "      <link rel=\"stylesheet\" type=\"text/css\" href=\"" + css.toURI().toURL().toString() + "\"><script type=\"text/javascript\" src=\"" + js.toURI().toURL().toString()
            + "\"></script></head>\n" + 
        "   <body>\n" + 
        "      <div class=\"testsuite\" data-name=\"Test no.1\" data-source=\"" + xspecURL.toString()
        + "\" data-tests=\"1\" data-failures=\"0\">\n" + 
        "         <p style=\"margin:0px;\"><span>Test no.1</span><span>&nbsp;</span><a class=\"button\" onclick=\"runScenario(this)\">Run</a></p>\n" + 
        "         <div class=\"testcase\" data-name=\"Must not be escaped at all\">\n" + 
        "            <p class=\"passed\"><span class=\"test-passed\" onclick=\"toggleResult(this)\">Must not be escaped at all</span><span>&nbsp;</span><a class=\"button\" onclick=\"showTest(this)\">Show</a></p>\n" + 
        "         </div>\n" + 
        "      </div>\n" + 
        "      <div class=\"testsuite\" data-name=\"Test no.2\" data-source=\"" + xspecURL.toString()
        + "\" data-tests=\"1\" data-failures=\"0\">\n" + 
        "         <p style=\"margin:0px;\"><span>Test no.2</span><span>&nbsp;</span><a class=\"button\" onclick=\"runScenario(this)\">Run</a></p>\n" + 
        "         <div class=\"testcase\" data-name=\"Must not be escaped at all\">\n" + 
        "            <p class=\"passed\"><span class=\"test-passed\" onclick=\"toggleResult(this)\">Must not be escaped at all</span><span>&nbsp;</span><a class=\"button\" onclick=\"showTest(this)\">Show</a></p>\n" + 
        "         </div>\n" + 
        "      </div>\n" + 
        "   </body>\n" + 
        "</html>", read(outputFile.toURI().toURL()).toString());
    
    // Assert the driver.
    File compiledXSL = new File(xspecFile.getParentFile(), "xspec/driverTest.xsl");
    File driverXSL = new File(xspecFile.getParentFile(), "xspec/driverTest-driver.xsl");
    
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" + 
        "                xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" + 
        "                xmlns:x=\"http://www.jenitennison.com/xslt/xspec\"\n" + 
        "                version=\"2.0\">\n" + 
        "   <xsl:import href=\"" + compiledXSL.toURI().toURL().toString() + "\"/>\n" + 
        "</xsl:stylesheet>", 
        read(driverXSL.toURI().toURL()).toString().replaceAll("\\\\", "/").replaceAll("<\\?xml-stylesheet.*\\?>", ""));
  }

  /**
   * We can specify which scenarios to be run.
   * 
   * @throws Exception
   */
  public void testRunSpecificScenarios_2() throws Exception {
    URL xspecURL = getClass().getClassLoader().getResource("meta/driverTest.xspec");
    File xspecFile = URLUtil.getCanonicalFileFromFileUrl(xspecURL);
    File outputFile = new File(xspecFile.getParentFile(), "driverTest-report.html");
    
    String entryPoints = XSpecUtil.generateId("Test no.1") + " " + XSpecUtil.generateId("Test no.2");
    executeANT(xspecFile, outputFile, entryPoints);
    
    File xmlFormatOutput = new File(xspecFile.getParentFile(), "xspec/driverTest-result.xml");
    
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<x:report xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" + 
        "          xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" + 
        "          xmlns:x=\"http://www.jenitennison.com/xslt/xspec\">\n" + 
        "   <x:scenario xmlns:test=\"http://www.jenitennison.com/xslt/unit-test\"\n" + 
        "               xmlns:functx=\"http://www.functx.com\"\n" + 
        "               source=\"" + xspecURL.toString() + "\">\n" + 
        "      <x:label>Test no.1</x:label>\n" + 
        "      <x:call function=\"functx:escape-for-regex\">\n" + 
        "         <x:param select=\"'Hello'\"/>\n" + 
        "      </x:call>\n" + 
        "      <x:result select=\"'Hello'\"/>\n" + 
        "      <x:test successful=\"true\">\n" + 
        "         <x:label>Must not be escaped at all</x:label>\n" + 
        "         <x:expect select=\"'Hello'\"/>\n" + 
        "      </x:test>\n" + 
        "   </x:scenario>\n" + 
        "   <x:scenario xmlns:test=\"http://www.jenitennison.com/xslt/unit-test\"\n" + 
        "               xmlns:functx=\"http://www.functx.com\"\n" + 
        "               source=\"" + xspecURL.toString() + "\">\n" + 
        "      <x:label>Test no.2</x:label>\n" + 
        "      <x:call function=\"functx:escape-for-regex\">\n" + 
        "         <x:param select=\"'(Hello)'\"/>\n" + 
        "      </x:call>\n" + 
        "      <x:result select=\"'\\(Hello\\)'\"/>\n" + 
        "      <x:test successful=\"true\">\n" + 
        "         <x:label>Must not be escaped at all</x:label>\n" + 
        "         <x:expect select=\"'\\(Hello\\)'\"/>\n" + 
        "      </x:test>\n" + 
        "   </x:scenario>\n" + 
        "</x:report>", 
        read(xmlFormatOutput.toURI().toURL()).toString().replaceAll("date=\".*\"", "date=\"XXX\"").replaceAll("<\\?xml-stylesheet.*\\?>", ""));
    
        
    // Assert the driver.
    File compiledXSL = new File(xspecFile.getParentFile(), "xspec/driverTest.xsl");
    File driverXSL = new File(xspecFile.getParentFile(), "xspec/driverTest-driver.xsl");
    
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" + 
        "                xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" + 
        "                xmlns:x=\"http://www.jenitennison.com/xslt/xspec\"\n" + 
        "                version=\"2.0\">\n" + 
        "   <xsl:import href=\"" + compiledXSL.toURI().toURL().toString() + "\"/>\n" + 
        "   <xsl:template name=\"x:main\">\n" + 
        "      <xsl:result-document format=\"x:report\">\n" + 
        "         <x:report>\n" + 
        // 
        "            <xsl:call-template name=\"x:x902ab200-b398-338b-90b5-a19708ce37d1\"/>\n" + 
        "            <xsl:call-template name=\"x:xa0a2d95c-2b63-3229-b5ad-8a166aabd06a\"/>\n" + 
        "         </x:report>\n" + 
        "      </xsl:result-document>\n" + 
        "   </xsl:template>\n" + 
        "</xsl:stylesheet>", 
        read(driverXSL.toURI().toURL()).toString().replaceAll("\\\\", "/").replaceAll("<\\?xml-stylesheet.*\\?>", ""));
  }
}
