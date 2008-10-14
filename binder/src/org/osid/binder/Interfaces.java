//
// Interfaces.java
//
//     Captures all interfaces for reference during interface expansion.
//  
//
// Tom Coppeto
// OnTapSolutions
// 26 August 2006
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

package org.osid.binder;

import java.io.PrintStream;


/**
 *  Captures all interfaces for reference during interface expansion.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class Interfaces {

    private static java.util.HashMap<String, org.osid.binder.Interface> map = new java.util.HashMap<String, org.osid.binder.Interface>();


    public synchronized static void add(org.osid.binder.Interface iface) {
	map.put(iface.getName(), iface);
	return;
    }

    
    public static org.osid.binder.Interface get(String name) {
	return (map.get(name));
    }
}
	
