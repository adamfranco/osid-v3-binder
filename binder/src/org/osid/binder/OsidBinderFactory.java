//
// OsidBinderFactory.java
//
//     Factory for creating components of the OSID interface.
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

package org.osid.binder;


/**
 *  <p>
 *  Factory for creating interface components.
 *  </p>
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public interface OsidBinderFactory {

    /**
     *  Creates a new subclass of <code>Osid</code>.
     */

    public Osid createOsid();


    /**
     *  Creates a new subclass of <code>Enumeration</code>.
     */

    public Enumeration createEnumeration();


    /**
     *  Creates a new subclass of <code>EnumerationItem</code>.
     */

    public EnumerationItem createEnumerationItem();


    /**
     *  Creates a new subclass of <code>Interface</code>.
     */

    public Interface createInterface();


    /**
     *  Creates a new subclass of <code>Method</code>.
     */

    public Method createMethod();


    /**
     *  Creates a new subclass of <code>Parameter</code>.
     */

    public Parameter createParameter();


    /**
     *  Creates a new subclass of <code>Return</code>.
     */

    public Return createReturn();


    /**
     *  Creates a new subclass of <code>Error</code>.
     */

    public Error createError();


    /**
     *  Creates a new subclass of <code>Compliance</code>.
     */

    public Compliance createCompliance();


    /**
     *  Creates a new subclass of <code>Text</code>.
     */

    public Text createText();
}
