//
// OsidBinderException.java
//
//     Thrown to indicate an error in the OsidBinder.
//  
//
// Tom Coppeto
// OnTapSolutions
// 5 March 2005
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
 *  Thrown to indicate a problem in translating the passwd xosid.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class OsidBinderException
    extends Exception {


    /**
     *  Constructs a <code>OsidBinderException</code> with <code>null</code>
     *  as its error message.
     */

    public OsidBinderException() {
	super();
	return;
    }


    /**
     *  Constructs a <code>OsidBinderException</code> with the specified
     *  detail message. The error message string <code>msg</code> can
     *  later be retrieved by the getMessage() method of 
     *  {@link java.lang.Throwable}.
     *  
     *  @param msg the error message
     */

    public OsidBinderException(String msg) {
	super(msg);
	return;
    }


    /**
     *  Constructs a <code>OsidBinderException</code> with the specified
     *  detail message and cause. The error message string <code>msg</code> can
     *  later be retrieved by the getMessage() method of 
     *  {@link java.lang.Throwable}.
     *
     *  @param msg the error message
     *  @param t the cause of the exception
     */

    public OsidBinderException(String msg, Throwable t) {
	super(msg, t);
	return;
    }


    /**
     *  Constructs a <code>OsidBinderException</code> with the specified
     *  cause and a detail message of <code>
     *  cause.toString()</code> which typically contains the class and
     *  detail message of <code>cause</code>.
     *
     *  @param t the cause of the exception
     */

    public OsidBinderException(Throwable t) {
	super(t);
	return;
    }
}

