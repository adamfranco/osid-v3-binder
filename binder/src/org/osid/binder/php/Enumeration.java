//
// Enumeration.java
//
//     Produces the PHP Binding for an Enumeration.
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

import java.io.PrintStream;


/**
 *  Produces the PHP binding for an enumeration.
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


    void print(PrintStream out) {
	out.println("/**");
	out.println(" * " + getClassName(getName()));
	out.println(" * ");
	out.println(" *     Specifies the OSID definition for " + getClassName(getName()) + ".");
	out.println(" * ");
	getCopyright().printPlain(out, " * ");
	out.println();
	out.println(" * ");
	getLicense().printPlain(out, " *     ");
	out.println();
	out.println(" * ");
	out.println(" * @package " + getPackageName(getName()));
	out.println(" */");
	out.println();

// 	out.println("package " + getPackageName(getName()) + ";");
// 	out.println();
// 	out.println();

	out.println("/**");
	if (getDescription() != null) {
            out.print(" *  ");
            getDescription().printHtml(out, " *  ", 4);
            out.println();
        }
	out.println(" */");
        out.println();

// 	out.println("trigger_error('Binding of enums not complete. " + getClassName(getName()) + " not bound');");
	
	out.println("class " + getClassName(getName()) + " {");
	out.println();

// 	boolean first = true;
	for (org.osid.binder.EnumerationItem item: getItems()) {
// 	    if (!first) {
// 		out.println(",");
// 		out.println();
// 	    } else {
// 		first = false;
// 	    }

	    out.print("    /** ");
	    item.getDescription().printHtml(out, "        ", 10);
	    out.println("*/");
	    out.println("    public static function " + item.getBinderName() + "() {");
	     if (getName().equals("osid.OSID")) {
			out.print("        return new osid_OSID ");
			out.println("("
				  + "\"" + getServiceName(item.getBinderName()) + "\", " 
				  + "null, " 
				  + "\"" + getManagerName(item.getBinderName()) + "\", " 
				  + "\"" + getProxyManagerName(item.getBinderName()) + "\", "
				  + "\"" + item.getDescription().getText() + "\"" 
				  + ");");
	     } else {
	     	 out.println("        return \"" + getServiceName(item.getBinderName()) + "\";");
	     }
	    out.println("    }");
   		out.println();
	}

// 	out.println(");");

	out.println();
	
	out.println("    public static function values() {");
	out.println("        $ret = array();");
	out.println("        $ref = new ReflectionClass(__CLASS__);");
	out.println("        $properties = $ref->getProperties();");
	out.println("        foreach ($properties as $property)");
	out.println("            $ret[$property->getName()] = $property->getValue();");
	out.println("        return $ret;");
	out.println("    }");
	
	
	if (getName().equals("osid.OSID")) {
		out.println();
		out.println();
		out.println("    private $service;");
		out.println("    private $osid;");
		out.println("    private $manager;");
		out.println("    private $proxyManager;");
		out.println("    private $description;");
		out.println();
		out.println("    public function __construct($service, $osid, $manager, $proxyManager, $description) {");
		out.println("        $this->service = $service;");
		out.println("        $this->osid = $osid;");
		out.println("        $this->manager = $manager;");
		out.println("        $this->proxyManager = $proxyManager;");
		out.println("        $this->description = $description;");
		out.println("    }");
		out.println();
		out.println("    public function getOSIDServiceName() {");
		out.println("        return $this->service;");
		out.println("    }");
		out.println();
		out.println("    public function getOSIDPackageName() {");
		out.println("        return $this->osid;");
		out.println("    }");
		out.println();
		out.println("    public function getManager() {");
		out.println("        return $this->manager;");
		out.println("    }");
		out.println();
		out.println("    public function getProxyManager() {");
		out.println("        return $this->proxyManager;");
		out.println("    }");
		out.println();
		out.println("    public function getDescription() {");
		out.println("        return $this->description;");
		out.println("    }");
	}
	
	out.println("}");
	
	out.println();
// 	out.println();
// 	for (org.osid.binder.EnumerationItem item: getItems()) {
// 		if (getName().equals("osid.OSID")) {
// 			out.println();
// 			out.print("/** ");
// 			item.getDescription().printHtml(out, "        ", 10);
// 			out.println("*/");
// 			out.print(getClassName(getName()) + "::$" + item.getBinderName() + " = ");
// 	
// 		   
// 			out.print("new osid_OSID ");
// 			out.println("("
// 				  + "\"" + getServiceName(item.getBinderName()) + "\", " 
// 				  + "null, " 
// 				  + "\"" + getManagerName(item.getBinderName()) + "\", " 
// 				  + "\"" + getProxyManagerName(item.getBinderName()) + "\", "
// 				  + "\"" + item.getDescription().getText() + "\"" 
// 				  + ");");
// 	    }
// 	}
	return;
    }


    private String getClassName(final String path) {
	return (org.osid.binder.php.Interface.getClassName(path));
// 	int pos = path.lastIndexOf(".");
// 	return (path.substring(pos + 1));
    }


    private String getPackageName(final String path) {
	return (org.osid.binder.php.Interface.getPackageName(path));
// 	int pos = path.lastIndexOf(".");
// 	return ("org." + path.substring(0, pos));
    }


    private String getServiceName(final String osid) {
	return (osid.toLowerCase());
    }


    private String getManagerName(final String osid) {
    return ("osid_" + osid.toLowerCase() + "_" +
		capitalize(osid.toLowerCase()) + "Manager");
// 	return ("org.osid." + osid.toLowerCase() + "." +
// 		capitalize(osid.toLowerCase()) + "Manager");
    }


    private String getProxyManagerName(final String osid) {
    return ("osid_" + osid.toLowerCase() + "_" +
		capitalize(osid.toLowerCase()) + "ProxyManager");

// 	return ("org.osid." + osid.toLowerCase() + "." + 
// 		capitalize(osid.toLowerCase()) + "ProxyManager");
    }

    
    private String capitalize(final String s) {
	char chars[] = s.toCharArray();
	chars[0] = Character.toUpperCase(chars[0]);
	return (new String(chars));
    }
}
	
