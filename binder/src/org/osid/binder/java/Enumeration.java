//
// Enumeration.java
//
//     Produces the Java Binding for an Enumeration.
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

import java.io.PrintStream;


/**
 *  <p>
 *  Produces the Java binding for an enumeration.
 *  </p>
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */


public class Enumeration
    extends org.osid.binder.Enumeration {
    
    private OsidBinderFactory factory = new OsidBinderFactory();


    /**
     *  Constructs a new <code>Enumeration</code> object. 
     */
    
    protected Enumeration() {

	super();
    }


    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected OsidBinderFactory getOsidBinderFactory() {

	return (this.factory);
    }


    void print(PrintStream out) {

	out.println("//");
	out.println("// " + getName());
	out.println("//");
	out.println("//     Specifies the OSID definition for " + getName() + ".");
	out.println("//");
	getCopyright().printPlain(out, "// ");
	out.println();
	out.println("//");
	getLicense().printPlain(out, "//     ");
	out.println();
	out.println("//");
	out.println();

	out.println("package " + getPackageName(getName()) + ";");
	out.println();
	out.println();

	out.println("/**");
	if (getDescription() != null) {
            out.print(" *  ");
            getDescription().printHtml(out, " *  ", 4);
            out.println();
        }
	out.println(" */");
        out.println();

	out.println("public enum " + getClassName(getName()) + " {");
	out.println();

	boolean first = true;
	for (org.osid.binder.EnumerationItem item: getItems()) {
	    if (!first) {
		out.println(",");
		out.println();
	    } else {
		first = false;
	    }

	    out.print("    /** ");
	    item.getDescription().printHtml(out, "        ", 10);
	    out.println("*/");
	    out.print("    " + item.getBinderName());

	    if (getName().equals("osid.OSID")) {
		out.print("(\"" + getServiceName(item.getBinderName()) + 
			  "\", \"org.osid." +
			  getServiceName(item.getBinderName()) + 
			  "\", \"" +
			  getManagerName(item.getBinderName()) + 
			  "\", \"" +
			  getProxyManagerName(item.getBinderName()) +
			  "\", \"" + 
			  item.getDescription().getText() +
			  "\")");
	    } else {
		out.print("(\"" + item.getBinderName().toLowerCase() + 
			  "\", \"" +
			  item.getDescription().getText() + 
			  "\")");
	    }
	}

	out.println(";");

	if (getName().equals("osid.OSID")) {
	    out.println();
	    out.println();
	    out.println("    private final String service;");
	    out.println("    private final String package;");
	    out.println("    private final String manager;");
	    out.println("    private final String proxyManager;");
	    out.println("    private final String description;");
	    out.println();
	    out.println("    OSID(String service, String package, String manager, String proxyManager, String description) {");
	    out.println("        this.service      = service;");
	    out.println("        this.package      = package;");
	    out.println("        this.manager      = manager;");
	    out.println("        this.proxyManager = proxyManager;");
	    out.println("        this.description  = description;");
	    out.println("    }");
	    out.println();
	    out.println("    public String service() {");
	    out.println("        return (this.service);");
	    out.println("    }");
	    out.println();
	    out.println("    public String package() {");
	    out.println("        return (this.package);");
	    out.println("    }");
	    out.println();
	    out.println("    public String manager() {");
	    out.println("        return (this.manager);");
	    out.println("    }");
	    out.println();
	    out.println("    public String proxyManager() {");
	    out.println("        return (this.proxyManager);");
	    out.println("    }");
	    out.println();
	    out.println("    public String description() {");
	    out.println("        return (this.description);");
	    out.println("    }");
	} else {
	    out.println();
	    out.println();
	    out.println("    private final String name;");
	    out.println("    private final String description;");
	    out.println();
	    out.println("    OSID(String name, String description) {");
	    out.println("        this.name        = name;");
	    out.println("        this.description = description;");
	    out.println("    }");
	    out.println();
	    out.println("    public String name() {");
	    out.println("        return (this.name);");
	    out.println("    }");
	    out.println();
	    out.println("    public String description() {");
	    out.println("        return (this.description);");
	    out.println("    }");
	}

	out.println("}");
	return;
    }


    private String getClassName(final String path) {
	
	int pos = path.lastIndexOf(".");
	return (path.substring(pos + 1));
    }


    private String getPackageName(final String path) {
	int pos = path.lastIndexOf(".");
	return ("org." + path.substring(0, pos));
    }


    private String getServiceName(final String osid) {
	return (osid.toLowerCase());
    }


    private String getManagerName(final String osid) {

	return ("org.osid." + osid.toLowerCase() + "." +
		capitalize(osid.toLowerCase()) + "Manager");
    }


    private String getProxyManagerName(final String osid) {

	return ("org.osid." + osid.toLowerCase() + "," + 
		capitalize(osid.toLowerCase()) + "ProxyManager");
    }

    
    private String capitalize(final String s) {

	char chars[] = s.toCharArray();
	chars[0] = Character.toUpperCase(chars[0]);
	return (new String(chars));
    }
}
	
