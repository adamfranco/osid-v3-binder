<?xml version="1.0" encoding="iso-8859-1" standalone="no"?>

<!--
    XSL for Java
    
    OSID Version 3.0.0
    Open Knowledge Initiative
    
    Copyright (C) 2005-2006 Massachusetts Institute of Technology. All Rights
    Reserved.

    This Work is being provided by the copyright holder(s) subject to the
    following license. By obtaining, using and/or copying this Work, you agree
    that you have read, understand, and will comply with the following terms
    and conditions.

    This Work and the information contained herein is provided on an "AS IS"
    basis. The Massachusetts Institute of Technology, the Open Knowledge
    Initiative, and THE AUTHORS DISCLAIM ALL WARRANTIES, EXPRESS OR IMPLIED,
    INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
    PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
    COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF
    OR IN CONNECTION WITH THE WORK OR THE USE OR OTHER DEALINGS IN THE WORK.

    Permission to use, copy and distribute this Work, for any purpose, 
    without fee or royalty is hereby granted, provided that you include 
    the above copyright notice and the terms of this license on ALL
    copies of the Work or portions thereof. 

    You may nodify or create Derivatives of this Work only for your internal
    purposes. You shall not distribute or transfer any such Derivative or this
    Work to any location or to any third party. For the purposes of this
    license, Derivative shall mean any derivative of the Work as defined in the
    United States Copyright Act of 1976, such as a translation or modification.

    The export of software employing encryption technology may require a
    specific license from the United States Government. It is the
    responsibility of any person or organization comtemplating export to obtain
    such a license before exporting this Work.

    Tom Coppeto
    OnTapSolutions
    14 May 2006

    Based on XOSID 2.1 by Charles Shubert
-->

<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xslt="http://www.apache.org.xslt"
		xmlns:xosid="http://osid.org/3.0.0/xosid"
                xmlns:redirect="org.apache.xalan.xslt.extensions.Redirect"
                extension-element-prefixes="redirect"
		xmlns:fo="http://www.w3.org/1999/XSL/Format"
>

    <xsl:output method="text" indent="no"/>

    <xsl:template match="xosid:osid">
        <xsl:call-template name="xosid:interface">
            <xsl:with-param name="package" select="@xosid:name"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="xosid:interface">
	<xsl:param name="package"/>        

        <xsl:for-each select="xosid:interface">	
            <xsl:call-template name="xosid:openFile">
                <xsl:with-param name="package" select="$package"/>
            </xsl:call-template>
            <xsl:text>package org.</xsl:text>
            <xsl:value-of select="$package"/>
            <xsl:text>;&#10;&#10;</xsl:text>

            <xsl:text>public interface </xsl:text>
            <xsl:call-template name="xosid:interfaceName">
                <xsl:with-param name="name" select="@xosid:name"/>
            </xsl:call-template>
            <xsl:for-each select="xosid:implements/@xosid:interface">
                <xsl:text>&#10;    extends org.</xsl:text>
                <xsl:value-of select="."/>
            </xsl:for-each>
            <xsl:text> {&#10;&#10;</xsl:text>
            <xsl:call-template name="xosid:method"/>
            <xsl:text>}&#10;&#10;</xsl:text>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="xosid:method">
	<xsl:for-each select="xosid:method">
            <xsl:call-template name="xosid:methodDoc"/>
            <xsl:text>    public </xsl:text>
	    <xsl:call-template name="xosid:return"/>
            <xsl:text> </xsl:text>
	    <xsl:value-of select="@xosid:name"/>
            <xsl:text>(</xsl:text>
            <xsl:call-template name="xosid:parameters"/>
            <xsl:text>)</xsl:text>
            <xsl:if test="xosid:error">
                    <xsl:text>&#10;        throws org.osid.OsidException</xsl:text>
            </xsl:if>
            <xsl:text>;&#10;</xsl:text>
            <xsl:if test="position() != last()">
                <xsl:text>&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="xosid:return">
	<xsl:choose>
	    <xsl:when test="xosid:return">
                <xsl:for-each select="xosid:return">
                    <xsl:choose>
	                <xsl:when test="xosid:primitiveType">
                            <xsl:call-template name="xosid:primitiveType"/>
                        </xsl:when>
                        <xsl:when test="xosid:interfaceType">
                            <xsl:call-template name="xosid:interfaceType"/>
                        </xsl:when>
                    </xsl:choose>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>void</xsl:text>
            </xsl:otherwise>
       </xsl:choose>
    </xsl:template>

    <xsl:template name="xosid:parameters">
	<xsl:for-each select="xosid:parameter">
            <xsl:if test="xosid:primitiveType">
                <xsl:call-template name="xosid:primitiveType"/>
            </xsl:if>
            <xsl:if test="xosid:interfaceType">
                <xsl:call-template name="xosid:interfaceType"/>
            </xsl:if>
            <xsl:text> </xsl:text>
            <xsl:value-of select="@xosid:name"/>
            <xsl:if test="position() != last()">
                <xsl:text>, </xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="xosid:primitiveType">
	<xsl:for-each select="xosid:primitiveType">
            <xsl:call-template name="xosid:primitiveTypeConvert">
	        <xsl:with-param name="primitive" select="@xosid:type"/>
            </xsl:call-template>
            <xsl:if test="@xosid:array='true'">
                <xsl:text>[]</xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="xosid:interfaceType">
        <xsl:for-each select="xosid:interfaceType">
	    <xsl:text>org.</xsl:text>
            <xsl:value-of select="@xosid:type"/>
            <xsl:if test="@xosid:array='true'">
                <xsl:text>[]</xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="xosid:primitiveTypeConvert">
        <xsl:param name="primitive"/>
        <xsl:choose>
            <xsl:when test="contains($primitive, 'boolean')">
                <xsl:text>boolean</xsl:text>
            </xsl:when>
            <xsl:when test="contains($primitive, 'byte')">
                <xsl:text>byte</xsl:text>
            </xsl:when>
            <xsl:when test="contains($primitive, 'cardinal')">
                <xsl:text>int</xsl:text>
            </xsl:when>
            <xsl:when test="contains($primitive, 'float')">
                <xsl:text>double</xsl:text>
            </xsl:when>
            <xsl:when test="contains($primitive, 'integer')">
                <xsl:text>int</xsl:text>
            </xsl:when>
            <xsl:when test="contains($primitive, 'proc')">
                <xsl:text>Object</xsl:text>
            </xsl:when>
            <xsl:when test="contains($primitive, 'string')">
                <xsl:text>String</xsl:text>
            </xsl:when>
            <xsl:when test="contains($primitive, 'timestamp')">
                <xsl:text>java.util.Date</xsl:text>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="xosid:interfaceName">
        <xsl:param name="name"/>

        <xsl:variable name="next" select="substring-after($name, '.')"/> 
        <xsl:if test="string-length($next) &gt; 0">
            <xsl:call-template name="xosid:interfaceName"> 
                <xsl:with-param name="name" select="$next"/> 
            </xsl:call-template> 
        </xsl:if> 
        <xsl:if test="string-length($next)=0">
            <xsl:value-of select="$name"/>
        </xsl:if>
    </xsl:template> 

    <xsl:template name="xosid:methodDoc">
        <xsl:text>/**&#10; *</xsl:text>
	<xsl:apply-templates select="xosid:description"/>
        <xsl:text>&#10; */&#10;</xsl:text>
    </xsl:template>

    <xsl:template name="xosid:openFile">
        <xsl:param name="package"/>

    </xsl:template>

    <xslt:component prefix="formatText" elements="col" functions="format">
        <xslt:script lang="javascript">
            var col = 0;
            
            function format(xslProcessorContext, elem) {
                
    <xsl:template match="xosid:copyrightSymbol">
        <xsl:text>(C) </xsl:text>
    </xsl:template>

    <xsl:template match="xosid:pbreak">
        <xsl:text>&#10;</xsl:text>
    </xsl:template>

    <xsl:template match="xosid:outline">
        <xsl:apply-templates select="xosid:element"/>
    </xsl:template>

    <xsl:template match="xosid:element">
        <xsl:text>* </xsl:text>
        <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="xosid:token">
        <xsl:text>&#60;code&#62;</xsl:text>
        <xsl:value-of select="."/>
        <xsl:text>&#60;/code&#62;</xsl:text>
    </xsl:template>

    <xsl:template match="xosid:code">
        <xsl:text>&#60;pre&#62;</xsl:text>
        <xsl:value-of select="."/>
        <xsl:text>&#60;/pre&#62;</xsl:text>
    </xsl:template>

</xsl:stylesheet>




