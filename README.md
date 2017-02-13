# oXygen-XML-editor-xspec-support

An oxygen XML Editor plugin intended to help those that create XSpec scenarios.

Installation
------------

The fastest way to install it is through Oxygen's add-on system:

1. inside Oxygen go to Help->Install new add-ons... and paste: https://raw.githubusercontent.com/AlexJitianu/oXygen-XML-editor-xspec-support/master/build/update_site.xml
2. follow the installation procedure

An alterantive method is to download the [ZIP package](https://github.com/AlexJitianu/oXygen-XML-editor-xspec-support/blob/master/build/xspec.support-1.0-SNAPSHOT-plugin.zip?raw=true) and unzip it inside {OxygenInstallDir}/plugins


Known issues
----
The plugin relies on a special framework. This might pose a problem is you are using project 
files and the preferences page "Locations" is set to "Project". In this cases please load a 
new project or switch the previously mentioned page to "Global". I'll think of a better 
solution for the future.

How to use it
-----------

1. Inside Oxygen open an XSpec file.
1. On the toolbar click on the action "XSpec Run"
1. A view named "XSpec Test Results" will be opened. 


What you can do inside the "XSpec Test Results" view:
1. For each test threre is a "Show" action that will select in the editor the coresponding test
1. For each scenario there is a "Run" action that will run just that scenario
1. For a failed test you can click on it and the diff between the Expected/Actual results will be opened
 

How to customize it
-------------------
On the XML XSpec report, an XSLT is applied that generates HTML. This HTML is opened inside the view. The XSLT in question 
is: {pluginDirectory}/frameworks/xspec/src/reporter/unit-report-oxygen.xsl