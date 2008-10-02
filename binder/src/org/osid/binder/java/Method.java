//
// Method.java
//
//     Produces the Java binding for a method.
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
 *  Thrown to indicate a problem in translating the passwd xosid..
 *  </p>
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class Method
    extends org.osid.binder.Method {
    
    private OsidBinderFactory factory = new OsidBinderFactory();
    private String interfaceName;


    Method() {
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
	boolean close = false;

	/* 
	 * kind of kludgy.. even though runtimes don't need to be declared,
	 * the illegal states for methods invoked following a close() are
	 * declared for completeness. 
	 */

	if ((this.interfaceName != null) && (this.interfaceName.endsWith("Manager") ||
					     this.interfaceName.endsWith("Session") ||
					     this.interfaceName.endsWith("List") ||
					     this.interfaceName.endsWith("Profile"))) {
	    if (!getName().startsWith("supports") && 
		!getName().startsWith("use")) {
		addClose();
	    }
	}

	out.println("    /**");
	out.print("     *  ");
	getDescription().printHtml(out, "     *  ", 8);
	out.println();
	out.println("     *");

	for (org.osid.binder.Parameter parameter: getParameters()) {
	    ((Parameter) parameter).printJDoc(out, "     *  ");
	}
	
	if (getReturn() != null) {
	    ((Return) getReturn()).printJDoc(out, "     *  ");
	}

	for (org.osid.binder.Error error: getErrors()) {
	    ((Error) error).printJDoc(out, "     *  ");
	}

	if (getCompliance() != null) {
	    ((Compliance) getCompliance()).printJDoc(out, "     *  ");
	}

	if (getNotes() != null) {
	    out.print("     *  @notes  ");
	    getNotes().printHtml(out, "     *          ", 16);
	    out.println();
	}

	out.println("     */");
	out.println();
	
	String intLine = "    public ";
	if (getReturn() == null) {
	    intLine = intLine + "void ";
	} else {
	    intLine = intLine + getReturn().getBinderType() + " ";
	}
      
	intLine = intLine + getName() + "(";
	int indent = intLine.length();
	
	boolean first = true;
	for(org.osid.binder.Parameter parameter: getParameters()) {
	    if (!first) {
		intLine = intLine + ", ";
	    }

	    String pname = parameter.getBinderType() + " " + parameter.getName();
	    if (!first && ((pname.length() + intLine.length()) > 78)) {
		out.println(intLine);
		intLine = "";
		for (int i = 0; i < indent; i++) {
		    intLine = intLine + " ";
		}
	    }

	    intLine = intLine + pname;
	    first   = false;
	}

	out.print(intLine);
	out.print(")");

	first = true;
	for (org.osid.binder.Error error: getErrors()) {
	    if (isCheckedError(error) == false) {
		continue;
	    }
	    if (first) {
		out.println();
		out.print("        throws " + error.getBinderType());
		first = false;
	    } else {
		out.println(",");
		out.print("               " + error.getBinderType());
	    }
	} 
	
	out.println(";");
	return;
    }


    void setInterfaceName(String name) {
	this.interfaceName = name;
	return;
    }


    private void addClose() {
	org.osid.binder.Error ise = null;

	String type;
	String action;

	if (this.interfaceName.endsWith("Manager") || 
	    this.interfaceName.endsWith("Profile")) {
	    type = "manager";
	    action = "shut down";
	} else if (this.interfaceName.endsWith("Session")) {
	    type = "session";
	    action = "closed";
	} else if (this.interfaceName.endsWith("List")) {
	    type = "list";
	    action = "closed";
	} else {
	    type = "thing";
	    action = "";
	}
	
	addError("ILLEGAL_STATE", "Programming", "this " + type + " has been " + action);

	return;
    }


    private boolean isCheckedError(org.osid.binder.Error error) {
	
	String category = error.getCategory();

	if (category.equals("Programming") || category.equals("Integration")) {
	    return (false);
	}

	return (true);
    }
}
	
