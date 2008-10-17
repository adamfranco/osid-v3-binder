//
// OsidBinder.java
//
//     Produces the Java binding for the OSIDs.
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

package org.osid.binder.php;

import java.io.File;
import java.io.PrintStream;


/**
 *  Procuces the Java binding for the OSIDs.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class OsidBinder
    extends org.osid.binder.OsidBinder {

    private OsidBinderFactory factory = new OsidBinderFactory();

    
    /**
     *  Constructs a new <code>OsidBinder</code>.
     */

    OsidBinder() {
	super();
	return;
    }


    /**
     *  Parses XOSIDs in the given directory.
     *
     *  @param directory path to xosids
     *  @throws org.osid.binder.OsidBinderException
     */

    public void parse(String directory)
	throws org.osid.binder.OsidBinderException {

	super.parse(directory);

	for (org.osid.binder.Osid osid: getOsids()) {
	    for (org.osid.binder.Interface intraface: osid.getInterfaces()) {
		for (org.osid.binder.Method method: intraface.getMethods()) {
		    boolean add_inv = true;

		    for (org.osid.binder.Parameter parameter: method.getParameters()) {
			if (parameter.getType() != "cardinal") {
			    continue;
			}

			for (org.osid.binder.Error error: method.getErrors()) {
			    if (error.getType() == "INVALID_ARGUMENT") {
				add_inv = false;
			    }
			}
			if (add_inv) {
			    method.addError("INVALID_ARGUMENT", "Programming",
					    "cardinal value is negative");
			}
		    }
		}
	    }
	}

	for (org.osid.binder.Osid osid: getOsids()) {
	    for (org.osid.binder.Interface intraface: osid.getInterfaces()) {
		for (org.osid.binder.Method method: intraface.getMethods()) {
		    if (method.getParameters().length == 0) {
			continue;
		    }
		    boolean add_null = true;
		    for (org.osid.binder.Error error: method.getErrors()) {
			if (error.getType().equals("NULL_ARGUMENT")) {
			    add_null = false;
			}
		    }
		    if (add_null) {
			method.addError("NULL_ARGUMENT", "Programming",
					"null argument provided");
		    }
		}
	    }
	}
    }


    public void outputInterfaces(String directory) 
	throws org.osid.binder.OsidBinderException {

	directory = directory + "/org/osid";

	File dir = new File(directory);
	if (!dir.exists()) {
	    dir.mkdirs();
	} else if (!dir.isDirectory()) {
	    throw new org.osid.binder.OsidBinderException(directory + ": not a typewriter");
	}

	for (org.osid.binder.Osid osid: getOsids()) {

	    String sub = osid.getName();
	    if (sub.equals("osid")) {
		sub = "";
	    }

	    File osidDir = new File(dir, sub);
	    if (!osidDir.exists()) {
		osidDir.mkdirs();
	    } else if (!osidDir.isDirectory()) {
		throw new org.osid.binder.OsidBinderException(sub + ": not a directory");
	    }

	    ((Osid) osid).print(osidDir.getPath());
	}

	outputErrors(directory);
	return;
    }


    public void outputAssemblies(String directory) 
	throws org.osid.binder.OsidBinderException {

	directory = directory + "/org/osid/assemblies";

	File dir = new File(directory);
	if (!dir.exists()) {
	    dir.mkdirs();
	} else if (!dir.isDirectory()) {
	    throw new org.osid.binder.OsidBinderException(directory + ": not a typewriter");
	}

	for (org.osid.binder.Osid osid: getOsids()) {
	    String sub = osid.getName();
	    if (sub.equals("osid")) {
		sub = "";
	    }

	    File osidDir = new File(dir, sub);
	    if (!osidDir.exists()) {
		osidDir.mkdirs();
	    } else if (!osidDir.isDirectory()) {
		throw new org.osid.binder.OsidBinderException(sub + ": not a directory");
	    }

	    ((Osid) osid).printAssembly(osidDir.getPath());
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


    private void outputErrors(String directory)
	throws org.osid.binder.OsidBinderException {

	File dir = new File(directory);
	if (!dir.exists()) {
	    dir.mkdirs();
	} else if (!dir.isDirectory()) {
	    throw new org.osid.binder.OsidBinderException(directory + ": not a typewriter");
	}

	org.osid.binder.Osid[] osids = getOsids();
	if (osids.length > 0) {
	    printOsidError((org.osid.binder.Error) new OsidError(), 
			   directory, (org.osid.binder.Osid) osids[0]);
	    printOsidError((org.osid.binder.Error) new OsidRuntimeError(), 
			   directory, (org.osid.binder.Osid) osids[0]);
	}

	for (org.osid.binder.Osid osid: getOsids()) {
	    for (org.osid.binder.Interface intraface: osid.getInterfaces()) {
		for (org.osid.binder.Method method: intraface.getMethods()) {
		    for (org.osid.binder.Error error: method.getErrors()) {
			String type = error.getBinderType();
			printOsidError(error, directory, osid);
		    }
		}
	    }
	}
    }

    
    private void printOsidError(org.osid.binder.Error error, String directory,
				org.osid.binder.Osid osid)
	throws org.osid.binder.OsidBinderException {

	String name = error.getBinderType();
	int pos = name.lastIndexOf('.');
	if (pos > 0) {
	    name = name.substring(pos + 1);
	}

	File efile = new File(directory, name + ".php");
	if (efile.exists()) {
	    return;
	}
	
	PrintStream out;
	try {
	    out = new PrintStream(efile);
	} catch (Exception e) {
	    throw new org.osid.binder.OsidBinderException("cannot open file " + efile.getPath(), e);
	}

	out.println("//");
	out.println("// " + error.getBinderType());
	out.println("//");
	out.println("//     Specifies the OSID exception " + name + ".");
	out.println("//");
	osid.getCopyright().printPlain(out, "// ");
	out.println();
	out.println("//");
	osid.getLicense().printPlain(out, "//     ");
	out.println();
	out.println("//");
	out.println();
	
	out.println("package org.osid;");
	out.println();
	out.println();
	
	out.print("public class " + name);
	if (error.getCategory().equals("")) {
	    out.println();
	    if (name.equals("OsidRuntimeException")) {
		out.println("    extends RuntimeException {");
	    } else {
		out.println("    extends Exception {");
	    }
	} else if (error.getCategory().equalsIgnoreCase("integration") || error.getCategory().equalsIgnoreCase("programming")) {
	    out.println();	    
	    out.println("    extends OsidRuntimeException {");
	} else {
	    out.println();	    
	    out.println("    extends OsidException {");
	}

	out.println();
	out.println();

	out.println("    /**");
	out.println("     *  Constructs a <code>" + name + "</code> with the specified");
	out.println("     *  detail message. The error message string <code>msg</code> can");
	out.println("     *  later be retrieved by the getMessage() method of ");
	out.println("     *  {@link java.lang.Throwable}.");
	out.println("     *  ");
	out.println("     *  @param msg the error message");
	out.println("     */");
	out.println();
	out.println("    public " + name + "(String msg) {");
	out.println("        super(msg);");
	out.println("        return;");
	out.println("    }");

	out.println();
	out.println();

	out.println("    /**");
	out.println("     *  Constructs a <code>" + name + "</code> with the specified");
	out.println("     *  detail message and cause. The error message string <code>msg</code> can");
	out.println("     *  later be retrieved by the getMessage() method of ");
	out.println("     *  {@link java.lang.Throwable}.");
	out.println("     *");
	out.println("     *  @param msg the error message");
	out.println("     *  @param t the cause of the exception");
	out.println("     */");
	out.println();
	out.println("    public " + name + "(String msg, Throwable t) {");
	out.println("        super(msg, t);");
	out.println("        return;");
	out.println("    }");

	out.println();
	out.println();

	out.println("    /**");
	out.println("     *  Constructs a <code>" + name + "</code> with the specified");
	out.println("     *  cause and a detail message of <code>");
	out.println("     *  cause.toString()</code> which typically contains the class and");
	out.println("     *  detail message of <code>cause</code>.");
	out.println("     *");
	out.println("     *  @param t the cause of the exception");
	out.println("     */");
	out.println();
	out.println("    public " + name + "(Throwable t) {");
	out.println("        super(t);");
	out.println("        return;");
	out.println("    }");	

	out.println("}");
	out.close();
    }


    public static void main(String[] args) {
	OsidBinder binder = new OsidBinder();

	if (args.length != 2) {
	    System.err.println("usage: " + binder.getClass().getName() + " <src directory> <dst directory>");
	    return;
	}

	try {
	    binder.parse(args[0]);
	    binder.outputInterfaces(args[1]);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return;
    }
}	
