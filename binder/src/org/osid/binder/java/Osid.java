//
// Osid.java
//
//     Outputs the Java binding for an OSID.
//  
//
// Tom Coppeto
// OnTapSolutions
// 20 May 2006
//
//
// Copyright (c) 2006-2008 Massachusetts Institute of Technology
//      Permission is hereby granted, free of charge, to any person
//      obtaining a copy of this software and associated documentation
//      files (the "Software"), to deal in the Software without
//      restriction, including without limitation the rights to use,
//      copy, modify, merge, publish, distribute, sublicesne, and/or
//      sell copies of the Software, and to permit the persons to whom the
//      Software is furnished to do so, subject the following conditions:
//
//      The above copyright notice and this permission notice shall be
//      included in all copies or substantial portions of the Software.
//
//      The Software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND,
//      EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
//      OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
//      NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
//      HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
//      WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
//      DEALINGS IN THE SOFTWARE.
//

package org.osid.binder.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *  <p>
 *  Outputs the Java binding for an OSID.
 *  </p>
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class Osid
    extends org.osid.binder.Osid {
    
    private OsidBinderFactory factory = new OsidBinderFactory();


    Osid() {

	super();
    }

    
    protected void print(String directory) {

	File dir = new File(directory);
	if (dir.exists() == false) {
	    System.err.println("unable to find " + directory);
	    return;
	}

	PrintStream out;

	try {
	    out = new PrintStream(new FileOutputStream(new File(dir, "package-summary.html")));
	} catch (FileNotFoundException fnfe) {
	    System.err.println("cannot open " + dir.getPath() + "/" + "package-summary.html");
	    return;
	}

	printPackageInfo(out);
	out.close();

	for (org.osid.binder.Interface intraface: getInterfaces()) {

/*
	    // Let's not confuse macros with class hierarchies. Ppunt on 
	    // the pseudo-interfaces. Their methods will be rolled in later.
	    String name = intraface.getName();
	    if (name.equals("osid.OsidList") 
		|| name.equals("osid.OsidSession")
		|| name.equals("osid.OsidManager")
		|| name.equals("osid.OsidProxyManager")) {
		continue;
	    }
*/

	    try {
		out = new PrintStream(new FileOutputStream(new File(dir, getClassName(intraface.getName()) + ".java")));
	    } catch (FileNotFoundException fnfe) {
		System.err.println("cannot open " + dir.getPath() + "/" + getClassName(intraface.getName()));
		return;
	    }

	    ((Interface) intraface).print(out);
	    out.close();
	}

	for (org.osid.binder.Enumeration enumeration: getEnumerations()) {
	    try {
		out = new PrintStream(new FileOutputStream(new File(dir, getClassName(enumeration.getName()) + ".java")));
	    } catch (FileNotFoundException fnfe) {
		System.err.println("cannot open " + dir.getPath() + "/" + getClassName(enumeration.getName()));
		return;
	    }

	    ((Enumeration) enumeration).print(out);
	    out.close();
	}

	return;
    }


    protected String qualifyOsidName(String name) {
	
	return ("org." + name);
    }

	
    private String getClassName(String path) {
	
	int pos = path.lastIndexOf(".");
	return (path.substring(pos + 1));
    }


    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected OsidBinderFactory getOsidBinderFactory() {
	return (this.factory);
    }


    private void printPackageInfo(PrintStream out) {

	out.println("/**");
	out.println(" * <center><strong>" + getName() + " " + getVersion() + "</strong></center>");
	out.println(" * ");
	out.println(" * <p>Defines the interfaces necessary to describe the " + getName());
	out.println(" * service.</p>");
	out.println(" * ");
	getDescription().printHtmlParagraph(out, " * ");
	out.println();
	out.println(" * ");
	getCopyright().printHtmlParagraph(out, " * ");
	out.println();
	out.println(" * ");
	getLicense().printHtmlParagraph(out, " * ");
	out.println();
	out.println(" * ");
	if (getName().equals("org.osid") == false) {
	    out.println(" * @see org.osid");
	}
	out.println(" */");
	out.println();
	out.println("package org." + getName() + ";");
	
	return;
    }
}

