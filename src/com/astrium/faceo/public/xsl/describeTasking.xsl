<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
   xmlns="http://www.w3.org/TR/REC-html40" xmlns:eoli="http://earth.esa.int/XML/eoli">

<xsl:output method="html" indent="no" />
<xsl:strip-space elements="*" />

<xsl:template match="/">
<html>
	<head>
		<title>Test avec XSL</title>
		
   	<!-- CSS -->
    	<style type="text/css">
			.title {
				text-align: center;
				color: #00008B;
			}

			.firstLevel {
				position:relative; 
				left:10px; 
				font-weight:bolder;
				color: #9acd32;
			}
			
			.secondLevel {
				position:relative; 
				left:50px; 
				color: #8B008B;
			}
			
			.thirdLevel {
				position:relative; 
				left:100px; 
				color: #00008B;
			}

			.fourthLevel {
				position:relative; 
				left:150px; 
				color: #D2691E;
			}

			.spanKompsat2 {
				font-weight:normal;
				color: #000000;
			}
    	</style>
	</head>

	<body>
		<h3 class="title">Product MetaData</h3>
		<xsl:for-each select="eoli:response/eoli:retrievedData/eoli:Metadata">
			<xsl:apply-templates select="eoli:mdContact"/>
			<xsl:apply-templates select="eoli:mdDateSt"/>  
			<xsl:apply-templates select="eoli:dataIdInfo"/>  
			<xsl:apply-templates select="eoli:contInfo"/> 
			<xsl:apply-templates select="eoli:dqInfo"/> 
			<xsl:apply-templates select="eoli:addInfo"/> 
		</xsl:for-each>
	</body>
</html>
</xsl:template>

<xsl:template match="eoli:mdContact">  
	<div class="firstLevel">Contact :&#160;</div>
 	<div class="secondLevel">responsible organisation name :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:rpOrgName"/></span></div>
 	<div class="secondLevel">role :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:role"/></span></div>
</xsl:template>

<xsl:template match="eoli:mdDateSt">
 	<div class="firstLevel">Metadata date :&#160;<span class="spanKompsat2"><xsl:value-of select="."/></span></div>
</xsl:template>

<xsl:template match="eoli:dataIdInfo">
 	<div class="firstLevel">Identification informations :&#160;</div>
	<xsl:apply-templates select="eoli:prcTypeCode"/>  
	<xsl:apply-templates select="eoli:plaInsId"/>  
	<xsl:apply-templates select="eoli:satDom"/>  
	<xsl:apply-templates select="eoli:idCitation"/>  
  	<div class="firstLevel">Abstract element :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:idAbs"/></span></div>
	<div class="firstLevel">Product status element :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:idStatus"/></span></div>
	<xsl:apply-templates select="eoli:dataExt"/>  
</xsl:template>

<xsl:template match="eoli:prcTypeCode">
 	<div class="secondLevel">product type identifier :&#160;</div>
	<xsl:apply-templates select="eoli:identCode"/>  
</xsl:template>

<xsl:template match="eoli:identCode">
 	<div class="thirdLevel">code identifier :&#160;<span class="spanKompsat2"><xsl:value-of select="."/></span></div>
</xsl:template>

<xsl:template match="eoli:plaInsId">
 	<div class="secondLevel">platform instrument identifier :&#160;</div>
 	<div class="thirdLevel">platform name :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:platfSNm"/></span></div>
 	<div class="thirdLevel">platform identifier :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:platfSer"/></span></div>
 	<div class="thirdLevel">sensor :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:instShNm"/></span></div>
 	<div class="thirdLevel">sensor mode :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:instMode"/></span></div>
</xsl:template>

<xsl:template match="eoli:satDom">
 	<div class="secondLevel">satellite domain element :&#160;</div>
 	<div class="thirdLevel">orbit number :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:orbit"/></span></div>
 	<div class="thirdLevel">last orbit :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:lastOrbit"/></span></div>
 	<div class="thirdLevel">orbit direction element :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:orbitDir"/></span></div>
	<xsl:apply-templates select="eoli:wwRefSys"/>  
 	<div class="thirdLevel">swath Id :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:swathId"/></span></div>
</xsl:template>

<xsl:template match="eoli:wwRefSys">
 	<div class="thirdLevel">world reference system element :&#160;</div>
 	<div class="fourthLevel">frame number :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:frame"/></span></div>
 	<div class="fourthLevel">track number :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:track"/></span></div>
</xsl:template>

<xsl:template match="eoli:idCitation">
 	<div class="secondLevel">identification :&#160;</div>
 	<div class="thirdLevel">product identifier :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:resTitle"/></span></div>
</xsl:template>

<xsl:template match="eoli:dataExt">
 	<div class="secondLevel">temporal and geographical data :&#160;</div>
	<xsl:apply-templates select="eoli:tempEle"/>
	<xsl:apply-templates select="eoli:geoEle"/>
</xsl:template>

<xsl:template match="eoli:tempEle/eoli:exTemp/eoli:beginEnd">
 	<div class="thirdLevel">begin-end :&#160;</div>
 	<div class="fourthLevel">start date :&#160;<span class="spanKompsat2"><xsl:value-of  select="eoli:begin"/></span></div>
 	<div class="fourthLevel">end date :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:end"/></span></div>
</xsl:template>

<xsl:template match="eoli:geoEle">
 	<div class="thirdLevel">geographic element :&#160;</div>
	<xsl:apply-templates select="eoli:polygon"/>  
	<xsl:apply-templates select="eoli:scCenter"/>  
</xsl:template>

<xsl:template match="eoli:polygon">
 	<div class="fourthLevel">polygon element :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:coordinates"/></span></div>
</xsl:template>

<xsl:template match="eoli:scCenter">
 	<div class="fourthLevel">scene center element :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:coordinates"/></span></div>
</xsl:template>

<xsl:template match="eoli:contInfo">
 	<div class="firstLevel">Content informations :&#160;</div>
	<xsl:apply-templates select="eoli:attDesc"/>  
 	<div class="secondLevel">cont Type :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:contType"/></span></div>
 	<div class="secondLevel">illumination elevation angle :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:illElevAng"/></span></div>
 	<div class="secondLevel">illumination azimuth angle :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:illAziAng"/></span></div>
 	<div class="secondLevel">cloud cover percentage :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:cloudCovePerc"/></span></div>
</xsl:template>

<xsl:template match="eoli:attDesc">
 	<div class="secondLevel">attribute description :&#160;</div>
 	<div class="thirdLevel">type name :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:typeName"/></span></div>
	<xsl:apply-templates select="eoli:attTypes"/>  
</xsl:template>

<xsl:template match="eoli:attTypes">
 	<div class="thirdLevel">attribute types :&#160;</div>
 	<div class="fourthLevel">attribute name :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:attName"/></span></div>
 	<div class="fourthLevel">type name :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:typeName"/></span></div>
</xsl:template>

<xsl:template match="eoli:dqInfo">
 	<div class="firstLevel">Scope level :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:dqScope/eoli:scpLvl"/></span></div>
	<xsl:apply-templates select="eoli:graphOver"/>  
</xsl:template>

<xsl:template match="eoli:graphOver">
 	<div class="firstLevel">Graphic overview element :&#160;</div>
 	<div class="secondLevel">HTTP URL for graphic element :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:bgFileName"/></span></div>
 	<div class="secondLevel">start date :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:brwExt/eoli:tempEle/eoli:exTemp/eoli:beginEnd/eoli:begin"/></span></div>
 	<div class="secondLevel">end date :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:brwExt/eoli:tempEle/eoli:exTemp/eoli:beginEnd/eoli:end"/></span></div>
 	<div class="secondLevel">browser type element :&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:brwType"/></span></div>
</xsl:template>

<xsl:template match="eoli:addInfo">
  	<div class="firstLevel">Additional informations :&#160;</div>
	<xsl:for-each select="eoli:locAtt">
		<div class="secondLevel"><xsl:value-of select="eoli:locName"/>&#160;:&#160;<span class="spanKompsat2"><xsl:value-of select="eoli:locValue"/></span></div>
	</xsl:for-each> 
</xsl:template>

</xsl:stylesheet>
