//
// Interface.java
//
//     Defines an OSID interface.
//  
//
// Tom Coppeto
// OnTapSolutions
// 20 May 2006
//
//
// Copyright (c) 2006-2008 Massachusetts Institute of Technology
//
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
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *  Produces the Java binding for an OSID interface.
 *
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class Interface
    extends org.osid.binder.Interface {
    
    private OsidBinderFactory factory = new OsidBinderFactory();


    /**
     *  Constructs a new <code>Interface</code>
     */

    Interface() {
	super();
	return;
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

	out.println("package " + getPackageName() + ";");
	out.println();
	out.println();

	out.println("/**");
	if (getDescription() != null) {
            getDescription().printHtmlParagraph(out, " *  ");
            out.println();
        }

	out.println(" */");
        out.println();

	out.print("public interface " + getClassName());
	boolean first = true;
	for (String s: getInheritedInterfaces()) {
	    if (!first) {
		out.println(",");
		out.print("            org." + s);
	    } else {
		out.println();
		out.print("    extends org." + s);
		first = false;
	    } 
	}

	out.println(" {");

	/*	includeInheritedMethods(out);*/
	
	for (org.osid.binder.Method method: getMethods()) {
	    out.println();
	    out.println();
	    ((Method) method).setInterfaceName(getName());
	    ((Method) method).print(out); 
	}

	/* we can't rely on the GC to free up resources like spawned threads */
	printClose(out, getName());

	out.println("}");
	return;
    }


    void printAssembly(PrintStream out) {

	out.println("//");
	out.println("// " + getName());
	out.println("//");
	out.println("//     Defines an assembly for " + getName() + ".");
	out.println("//");
	getCopyright().printPlain(out, "// ");
	out.println();
	out.println("//");
	getLicense().printPlain(out, "//     ");
	out.println();
	out.println("//");
	out.println();

	out.println("package " + getAssemblyName() + ";");
	out.println();
	out.println();

	out.println("/**");
	out.println(" *  An abstract class to use as a version management assembly for");
	out.println(" *  {@link " + getPackageName() + "." + getClassName() + "}");
	out.println(" */");
        out.println();

	out.println("public abstract class " + getClassName() + " {");
	out.println();
	out.println("    /*");
	out.println("     * Space reserved for future use.");
	out.println("     */");
	out.println();
	out.println("}");

	return;
    }

    
    /**
     *   Includes inherited methods. 
     */

    void includeInheritedMethods(PrintStream out) {

	for (String s: getInheritedInterfaces()) {
	    org.osid.binder.Interface iface = org.osid.binder.Interfaces.get(s);
	    if (iface == null) {
		System.err.println("unknown interface " + s);
		continue;
	    }
	    
	    for (org.osid.binder.Method method: iface.getMethods()) {
		out.println();
		out.println();
		((Method) method).print(out);
	    }
	}

	return;
    }



    /**
     *  Prints close() method for managers, sessions and lists. 
     */

    void printClose(PrintStream out, String interfaceName) {

	if (interfaceName.equals("osid.OsidManager")) {
	    out.println();
	    out.println();
	    out.println("    /**");
	    out.println("     *  Shuts down this <code>" + interfaceName + "</code>");
	    out.println("     */");
	    out.println();
	    out.println("    public void shutdown();");
	}

	if (interfaceName.equals("osid.OsidSession")) {
	    out.println();
	    out.println();
	    out.println("    /**");
	    out.println("     *  Closes this <code>" + interfaceName + "</code>");
	    out.println("     */");
	    out.println();
	    out.println("    public void close();");
	}

	if (interfaceName.equals("osid.OsidList")) {
	    out.println();
	    out.println();
	    out.println("    /**");
	    out.println("     *  Closes down this <code>" + interfaceName + "</code>");
	    out.println("     */");
	    out.println();
	    out.println("    public void done();");
	}

	return;
    }


    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected OsidBinderFactory getOsidBinderFactory() {
	return (this.factory);
    }


    protected String getClassName() {
	int pos = getName().lastIndexOf(".");
	return (getName().substring(pos + 1));
    }


    protected String getPackageName() {
	int pos = getName().lastIndexOf(".");
	return ("org." + getName().substring(0, pos));
    }


    protected String getAssemblyName() {
	String[] parts = getName().split("\\.");
	return ("org." + parts[0] + ".assembly." + parts[1]);
    }
}
	
