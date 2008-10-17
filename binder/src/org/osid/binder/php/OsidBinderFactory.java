//
// OsidBinderFactory.java
//
//     The factory for the Java binding.
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


/**
 *  The factory for the Java binding.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class OsidBinderFactory
    implements org.osid.binder.OsidBinderFactory {


    /**
     *  Creates a new <code>Osid</code>.
     */

    public org.osid.binder.Osid createOsid() {
	return (new Osid());
    }


    /**
     *  Creates a new <code>Enumeration</code>.
     */

    public org.osid.binder.Enumeration createEnumeration() {

	return (new Enumeration());
    }


    /**
     *  Creates a new <code>EnumerationItem</code>.
     */

    public org.osid.binder.EnumerationItem createEnumerationItem() {

	return (new EnumerationItem());
    }


    /**
     *  Creates a new <code>Interface</code>.
     */

    public org.osid.binder.Interface createInterface() {

	return (new Interface());
    }


    /**
     *  Creates a new <code>Method</code>.
     */

    public org.osid.binder.Method createMethod() {

	return (new Method());
    }


    /**
     *  Creates a new <code>Parameter</code>.
     */

    public org.osid.binder.Parameter createParameter() {
	
	return (new Parameter());
    }


    /**
     *  Creates a new <code>Return</code>.
     */

    public org.osid.binder.Return createReturn() {

	return (new Return());
    }


    /**
     *  Creates a new <code>Error</code>.
     */

    public org.osid.binder.Error createError() {

	return (new Error());
    }


    /**
     *  Creates a new <code>Compliance</code>.
     */

    public org.osid.binder.Compliance createCompliance() {

	return (new Compliance());
    }


    /**
     *  Creates a new <code>Text</code>.
     */

    public org.osid.binder.Text createText() {

	return (new org.osid.binder.Text());
    }
}
