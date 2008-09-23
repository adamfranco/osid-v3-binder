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


    Interface() {

	super();
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
            getDescription().printHtmlParagraph(out, " *  ");
            out.println();
        }

	out.println(" */");
        out.println();

	out.print("public interface " + getClassName(getName()));
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

	if (!interfaceName.endsWith("Manager") && !interfaceName.endsWith("Session") 
	    && !interfaceName.endsWith("List")) {
	    return;
	}

	out.println();
	out.println();
	out.println("    /**");
	out.println("     *  Closes this <code>" + interfaceName + "</code>");
	out.println("     */");
	out.println();
	out.println("    public void close();");
	
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


    private String getClassName(String path) {
	int pos = path.lastIndexOf(".");
	return (path.substring(pos + 1));
    }


    private String getPackageName(String path) {
	int pos = path.lastIndexOf(".");
	return ("org." + path.substring(0, pos));
    }
}
	
