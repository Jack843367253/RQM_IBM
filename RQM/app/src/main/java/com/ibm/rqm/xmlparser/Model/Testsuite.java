package com.ibm.rqm.xmlparser.Model;

/**
 * Created by Administrator on 2015/4/6 0006.
 */
public class Testsuite {
    private String projectAreaAlias;
    private String projectAreaHref;
    private String identifier;
    private String stylesheet;
    private String webId;
    private String title;
    private String description;
    private String creationDate;
    private String updated;
    private String state;
    private String stateResource;
    private String sequentialExecution;
    private String passVariables;
    private String haltOnFailure;
    private String creator;
    private String creatorResource;
    private String owner;
    private String ownerResource;
    private String locked;
    private String authorid;
    private String authoridResouce;
    private String ownerid;
    private String owneridResource;
    private String variables;
    private String weight;
    private String priority;
    private String priorityResource;
    //TODO:suiteelements 考虑用列表！。
    ///???????列表？？？？？？
    /*
    <ns2:suiteelements>
<ns2:suiteelement>
<ns2:testcase href="http://jts.sfa.se/qm/service/com.ibm.rqm.integration.service.IIntegrationService/resources/Lekstuga+%28Quality+Management%29/testcase/urn:com.ibm.rqm:testcase:3041"/>
<ns2:testscript href="http://jts.sfa.se/qm/service/com.ibm.rqm.integration.service.IIntegrationService/resources/Lekstuga+%28Quality+Management%29/testscript/urn:com.ibm.rqm:testscript:2900"/>
</ns2:suiteelement>
<ns2:suiteelement>
<ns2:testcase href="http://jts.sfa.se/qm/service/com.ibm.rqm.integration.service.IIntegrationService/resources/Lekstuga+%28Quality+Management%29/testcase/urn:com.ibm.rqm:testcase:3040"/>
<ns2:testscript href="http://jts.sfa.se/qm/service/com.ibm.rqm.integration.service.IIntegrationService/resources/Lekstuga+%28Quality+Management%29/testscript/urn:com.ibm.rqm:testscript:2899"/>
</ns2:suiteelement>
<ns2:suiteelement>
<ns2:testcase href="http://jts.sfa.se/qm/service/com.ibm.rqm.integration.service.IIntegrationService/resources/Lekstuga+%28Quality+Management%29/testcase/urn:com.ibm.rqm:testcase:3338"/>
</ns2:suiteelement>
</ns2:suiteelements>
    * */
    private String suiteelements;
    private String suiteelement;
    private String testcaseHref;
    private String testscriptHref;


    //TODO：category is a list!
    /*
    * <ns2:category value="SAF" term="Kravunderlag"/>
<ns2:category value="S1" term="Testfas"/>
<ns2:category value="FOPE" term="IT-Produkt"/>
<ns2:category value="F?r?ldraf?rs?kring" term="LO"/>
<ns2:category value="001" term="Kravunderlag ID"/>
<ns2:category value="Testexekvering" term="Identifikation"/>
    * */
    private String categoryValue;
    private String categoryTerm;

    private String templateHref;

    //TODO custom list
    /*
    * <ns2:customAttributes>
<ns2:customAttribute required="false" type="SMALL_STRING">
<ns2:identifier>Testmilj?</ns2:identifier>
<ns2:name>Testmilj?</ns2:name>
<ns2:value>TM925, TM927</ns2:value>
<ns2:description/>
</ns2:customAttribute>
<ns2:customAttribute required="false" type="SMALL_STRING">
<ns2:identifier>Testplan</ns2:identifier>
<ns2:name>Testplan</ns2:name>
<ns2:value>Auto FP</ns2:value>
<ns2:description/>
</ns2:customAttribute>
</ns2:customAttributes>
    * */
    private String customAttributes;
    private String customAttribute;
    private String customAttributeRequired;
    private String customAttributeType;
    private String customAttributeIdentifier;
    private String customAttributeName;
    private String customAttributeValue;
    private String customAttributeDescription;

    public String getProjectAreaAlias() {
        return projectAreaAlias;
    }

    public void setProjectAreaAlias(String projectAreaAlias) {
        this.projectAreaAlias = projectAreaAlias;
    }

    public String getProjectAreaHref() {
        return projectAreaHref;
    }

    public void setProjectAreaHref(String projectAreaHref) {
        this.projectAreaHref = projectAreaHref;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getStateResource() {
        return stateResource;
    }

    public void setStateResource(String stateResource) {
        this.stateResource = stateResource;
    }

    public String getSequentialExecution() {
        return sequentialExecution;
    }

    public void setSequentialExecution(String sequentialExecution) {
        this.sequentialExecution = sequentialExecution;
    }

    public String getPassVariables() {
        return passVariables;
    }

    public void setPassVariables(String passVariables) {
        this.passVariables = passVariables;
    }

    public String getHaltOnFailure() {
        return haltOnFailure;
    }

    public void setHaltOnFailure(String haltOnFailure) {
        this.haltOnFailure = haltOnFailure;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getAuthoridResouce() {
        return authoridResouce;
    }

    public void setAuthoridResouce(String authoridResouce) {
        this.authoridResouce = authoridResouce;
    }

    public String getOwneridResource() {
        return owneridResource;
    }

    public void setOwneridResource(String owneridResource) {
        this.owneridResource = owneridResource;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPriorityResource() {
        return priorityResource;
    }

    public void setPriorityResource(String priorityResource) {
        this.priorityResource = priorityResource;
    }

    public String getSuiteelements() {
        return suiteelements;
    }

    public void setSuiteelements(String suiteelements) {
        this.suiteelements = suiteelements;
    }

    public String getSuiteelement() {
        return suiteelement;
    }

    public void setSuiteelement(String suiteelement) {
        this.suiteelement = suiteelement;
    }

    public String getTestcaseHref() {
        return testcaseHref;
    }

    public void setTestcaseHref(String testcaseHref) {
        this.testcaseHref = testcaseHref;
    }

    public String getTestscriptHref() {
        return testscriptHref;
    }

    public void setTestscriptHref(String testscriptHref) {
        this.testscriptHref = testscriptHref;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getCategoryTerm() {
        return categoryTerm;
    }

    public void setCategoryTerm(String categoryTerm) {
        this.categoryTerm = categoryTerm;
    }

    public String getTemplateHref() {
        return templateHref;
    }

    public void setTemplateHref(String templateHref) {
        this.templateHref = templateHref;
    }

    public String getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(String customAttributes) {
        this.customAttributes = customAttributes;
    }

    public String getCustomAttribute() {
        return customAttribute;
    }

    public void setCustomAttribute(String customAttribute) {
        this.customAttribute = customAttribute;
    }

    public String getCustomAttributeRequired() {
        return customAttributeRequired;
    }

    public void setCustomAttributeRequired(String customAttributeRequired) {
        this.customAttributeRequired = customAttributeRequired;
    }

    public String getCustomAttributeType() {
        return customAttributeType;
    }

    public void setCustomAttributeType(String customAttributeType) {
        this.customAttributeType = customAttributeType;
    }

    public String getCustomAttributeIdentifier() {
        return customAttributeIdentifier;
    }

    public void setCustomAttributeIdentifier(String customAttributeIdentifier) {
        this.customAttributeIdentifier = customAttributeIdentifier;
    }

    public String getCustomAttributeName() {
        return customAttributeName;
    }

    public void setCustomAttributeName(String customAttributeName) {
        this.customAttributeName = customAttributeName;
    }

    public String getCustomAttributeValue() {
        return customAttributeValue;
    }

    public void setCustomAttributeValue(String customAttributeValue) {
        this.customAttributeValue = customAttributeValue;
    }

    public String getCustomAttributeDescription() {
        return customAttributeDescription;
    }

    public void setCustomAttributeDescription(String customAttributeDescription) {
        this.customAttributeDescription = customAttributeDescription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatorResource() {
        return creatorResource;
    }

    public void setCreatorResource(String creatorResource) {
        this.creatorResource = creatorResource;
    }

    public String getOwnerResource() {
        return ownerResource;
    }

    public void setOwnerResource(String ownerResource) {
        this.ownerResource = ownerResource;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
