package org.intermine.objectstore.intermine;

/*
 * Copyright (C) 2002-2005 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;

import org.intermine.model.testmodel.Company;
import org.intermine.model.testmodel.Department;
import org.intermine.model.testmodel.Employee;
import org.intermine.objectstore.ObjectStoreFactory;
import org.intermine.sql.DatabaseFactory;
import org.intermine.testing.OneTimeTestCase;
import org.intermine.util.TypeUtil;

public class FlatModeSqlGeneratorTest extends SqlGeneratorTest
{
    public FlatModeSqlGeneratorTest(String arg) {
        super(arg);
    }

    public static Test suite() {
        return OneTimeTestCase.buildSuite(FlatModeSqlGeneratorTest.class);
    }

    public static void oneTimeSetUp() throws Exception {
        SqlGeneratorTest.oneTimeSetUp();
        setUpResults();
        db = DatabaseFactory.getDatabase("db.flatmodeunittest");
    }

    public static void setUpResults() throws Exception {
        results.put("SelectSimpleObject", "SELECT intermine_Alias.CEOId AS \"intermine_Aliasceoid\", intermine_Alias.addressId AS \"intermine_Aliasaddressid\", intermine_Alias.id AS \"intermine_Aliasid\", intermine_Alias.name AS \"intermine_Aliasname\", intermine_Alias.vatNumber AS \"intermine_Aliasvatnumber\" FROM Company AS intermine_Alias ORDER BY intermine_Alias.id");
        results2.put("SelectSimpleObject", Collections.singleton("Company"));
        results.put("SubQuery", "SELECT DISTINCT intermine_All.intermine_Arrayname AS a1_, intermine_All.intermine_Alias AS \"intermine_Alias\" FROM (SELECT intermine_Array.CEOId AS intermine_ArrayCEOId, intermine_Array.addressId AS intermine_ArrayaddressId, intermine_Array.id AS intermine_Arrayid, intermine_Array.name AS intermine_Arrayname, intermine_Array.vatNumber AS intermine_ArrayvatNumber, 5 AS intermine_Alias FROM Company AS intermine_Array) AS intermine_All ORDER BY intermine_All.intermine_Arrayname, intermine_All.intermine_Alias");
        results2.put("SubQuery", Collections.singleton("Company"));
        results.put("WhereSimpleEquals", "SELECT DISTINCT a1_.name AS a2_ FROM Company AS a1_ WHERE a1_.vatNumber = 1234 ORDER BY a1_.name");
        results2.put("WhereSimpleEquals", Collections.singleton("Company"));
        results.put("WhereSimpleNotEquals", "SELECT DISTINCT a1_.name AS a2_ FROM Company AS a1_ WHERE a1_.vatNumber != 1234 ORDER BY a1_.name");
        results2.put("WhereSimpleNotEquals", Collections.singleton("Company"));
        results.put("WhereSimpleNegEquals", "SELECT DISTINCT a1_.name AS a2_ FROM Company AS a1_ WHERE a1_.vatNumber != 1234 ORDER BY a1_.name");
        results2.put("WhereSimpleNegEquals", Collections.singleton("Company"));
        results.put("WhereSimpleLike", "SELECT DISTINCT a1_.name AS a2_ FROM Company AS a1_ WHERE a1_.name LIKE 'Company%' ORDER BY a1_.name");
        results2.put("WhereSimpleLike", Collections.singleton("Company"));
        results.put("WhereEqualsString", "SELECT DISTINCT a1_.name AS a2_ FROM Company AS a1_ WHERE a1_.name = 'CompanyA' ORDER BY a1_.name");
        results2.put("WhereEqualsString", Collections.singleton("Company"));
        results.put("WhereAndSet", "SELECT DISTINCT a1_.name AS a2_ FROM Company AS a1_ WHERE (a1_.name LIKE 'Company%' AND a1_.vatNumber > 2000) ORDER BY a1_.name");
        results2.put("WhereAndSet", Collections.singleton("Company"));
        results.put("WhereOrSet", "SELECT DISTINCT a1_.name AS a2_ FROM Company AS a1_ WHERE (a1_.name LIKE 'CompanyA%' OR a1_.vatNumber > 2000) ORDER BY a1_.name");
        results2.put("WhereOrSet", Collections.singleton("Company"));
        results.put("WhereNotSet", "SELECT DISTINCT a1_.name AS a2_ FROM Company AS a1_ WHERE ( NOT (a1_.name LIKE 'Company%' AND a1_.vatNumber > 2000)) ORDER BY a1_.name");
        results2.put("WhereNotSet", Collections.singleton("Company"));
        results.put("WhereSubQueryField", "SELECT a1_.companyId AS a1_companyId, a1_.id AS a1_id, a1_.managerId AS a1_managerId, a1_.name AS a1_name FROM Department AS a1_ WHERE a1_.name IN (SELECT DISTINCT a1_.name FROM Department AS a1_) ORDER BY a1_.name, a1_.id");
        results2.put("WhereSubQueryField", Collections.singleton("Department"));
        results.put("WhereSubQueryClass", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_ WHERE a1_.id IN (SELECT a1_.id FROM Company AS a1_ WHERE a1_.name = 'CompanyA') ORDER BY a1_.id");
        results2.put("WhereSubQueryClass", Collections.singleton("Company"));
        results.put("WhereNotSubQueryClass", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_ WHERE a1_.id NOT IN (SELECT a1_.id FROM Company AS a1_ WHERE a1_.name = 'CompanyA') ORDER BY a1_.id");
        results2.put("WhereNotSubQueryClass", Collections.singleton("Company"));
        results.put("WhereNegSubQueryClass", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_ WHERE a1_.id NOT IN (SELECT a1_.id FROM Company AS a1_ WHERE a1_.name = 'CompanyA') ORDER BY a1_.id");
        results2.put("WhereNegSubQueryClass", Collections.singleton("Company"));
        results.put("WhereClassClass", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber, a2_.CEOId AS a2_CEOId, a2_.addressId AS a2_addressId, a2_.id AS a2_id, a2_.name AS a2_name, a2_.vatNumber AS a2_vatNumber FROM Company AS a1_, Company AS a2_ WHERE a1_.id = a2_.id ORDER BY a1_.id, a2_.id");
        results2.put("WhereClassClass", Collections.singleton("Company"));
        results.put("WhereNotClassClass", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber, a2_.CEOId AS a2_CEOId, a2_.addressId AS a2_addressId, a2_.id AS a2_id, a2_.name AS a2_name, a2_.vatNumber AS a2_vatNumber FROM Company AS a1_, Company AS a2_ WHERE a1_.id != a2_.id ORDER BY a1_.id, a2_.id");
        results2.put("WhereNotClassClass", Collections.singleton("Company"));
        results.put("WhereNegClassClass", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber, a2_.CEOId AS a2_CEOId, a2_.addressId AS a2_addressId, a2_.id AS a2_id, a2_.name AS a2_name, a2_.vatNumber AS a2_vatNumber FROM Company AS a1_, Company AS a2_ WHERE a1_.id != a2_.id ORDER BY a1_.id, a2_.id");
        results2.put("WhereNegClassClass", Collections.singleton("Company"));
        Integer id1 = (Integer) TypeUtil.getFieldValue(data.get("CompanyA"), "id");
        results.put("WhereClassObject", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_ WHERE a1_.id = " + id1 + " ORDER BY a1_.id");
        results2.put("WhereClassObject", Collections.singleton("Company"));
        results.put("Contains11", "SELECT a1_.companyId AS a1_companyId, a1_.id AS a1_id, a1_.managerId AS a1_managerId, a1_.name AS a1_name, a2_.addressId AS a2_addressId, a2_.age AS a2_age, a2_.companyId AS a2_companyId, a2_.departmentId AS a2_departmentId, a2_.departmentThatRejectedMeId AS a2_departmentThatRejectedMeId, a2_.fullTime AS a2_fullTime, a2_.id AS a2_id, a2_.intermine_end AS a2_intermine_end, a2_.name AS a2_name, a2_.salary AS a2_salary, a2_.seniority AS a2_seniority, a2_.title AS a2_title, a2_.objectclass AS a2_objectclass FROM Department AS a1_, Employee AS a2_ WHERE a2_.class = 'org.intermine.model.testmodel.Manager' AND (a1_.managerId = a2_.id AND a1_.name = 'DepartmentA1') ORDER BY a1_.id, a2_.id");
        results2.put("Contains11", new HashSet(Arrays.asList(new String[] {"Department", "Employee"})));
        results.put("ContainsNot11", "SELECT a1_.companyId AS a1_companyId, a1_.id AS a1_id, a1_.managerId AS a1_managerId, a1_.name AS a1_name, a2_.addressId AS a2_addressId, a2_.age AS a2_age, a2_.companyId AS a2_companyId, a2_.departmentId AS a2_departmentId, a2_.departmentThatRejectedMeId AS a2_departmentThatRejectedMeId, a2_.fullTime AS a2_fullTime, a2_.id AS a2_id, a2_.intermine_end AS a2_intermine_end, a2_.name AS a2_name, a2_.salary AS a2_salary, a2_.seniority AS a2_seniority, a2_.title AS a2_title, a2_.objectclass AS a2_objectclass FROM Department AS a1_, Employee AS a2_ WHERE a2_.class = 'org.intermine.model.testmodel.Manager' AND (a1_.managerId != a2_.id AND a1_.name = 'DepartmentA1') ORDER BY a1_.id, a2_.id");
        results2.put("ContainsNot11", new HashSet(Arrays.asList(new String[] {"Department", "Employee"})));
        results.put("ContainsNeg11", "SELECT a1_.companyId AS a1_companyId, a1_.id AS a1_id, a1_.managerId AS a1_managerId, a1_.name AS a1_name, a2_.addressId AS a2_addressId, a2_.age AS a2_age, a2_.companyId AS a2_companyId, a2_.departmentId AS a2_departmentId, a2_.departmentThatRejectedMeId AS a2_departmentThatRejectedMeId, a2_.fullTime AS a2_fullTime, a2_.id AS a2_id, a2_.intermine_end AS a2_intermine_end, a2_.name AS a2_name, a2_.salary AS a2_salary, a2_.seniority AS a2_seniority, a2_.title AS a2_title, a2_.objectclass AS a2_objectclass FROM Department AS a1_, Employee AS a2_ WHERE a2_.class = 'org.intermine.model.testmodel.Manager' AND (a1_.managerId != a2_.id AND a1_.name = 'DepartmentA1') ORDER BY a1_.id, a2_.id");
        results2.put("ContainsNeg11", new HashSet(Arrays.asList(new String[] {"Department", "Employee"})));
        results.put("Contains1N", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber, a2_.companyId AS a2_companyId, a2_.id AS a2_id, a2_.managerId AS a2_managerId, a2_.name AS a2_name FROM Company AS a1_, Department AS a2_ WHERE (a1_.id = a2_.companyId AND a1_.name = 'CompanyA') ORDER BY a1_.id, a2_.id");
        results2.put("Contains1N", new HashSet(Arrays.asList(new String[] {"Department", "Company"})));
        results.put("ContainsN1", "SELECT a1_.companyId AS a1_companyId, a1_.id AS a1_id, a1_.managerId AS a1_managerId, a1_.name AS a1_name, a2_.CEOId AS a2_CEOId, a2_.addressId AS a2_addressId, a2_.id AS a2_id, a2_.name AS a2_name, a2_.vatNumber AS a2_vatNumber FROM Department AS a1_, Company AS a2_ WHERE (a1_.companyId = a2_.id AND a2_.name = 'CompanyA') ORDER BY a1_.id, a2_.id");
        results2.put("ContainsN1", new HashSet(Arrays.asList(new String[] {"Department", "Company"})));
        results.put("ContainsMN", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.businessAddressId AS a1_businessAddressId, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.personalAddressId AS a1_personalAddressId, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass, a2_.CEOId AS a2_CEOId, a2_.addressId AS a2_addressId, a2_.id AS a2_id, a2_.name AS a2_name, a2_.vatNumber AS a2_vatNumber FROM ImportantPerson AS a1_, Company AS a2_, CompanysContractors AS indirect0 WHERE a1_.class = 'org.intermine.model.testmodel.Contractor' AND ((a1_.id = indirect0.Companys AND indirect0.Contractors = a2_.id) AND a1_.name = 'ContractorA') ORDER BY a1_.id, a2_.id");
        results2.put("ContainsMN", new HashSet(Arrays.asList(new String[] {"ImportantPerson", "Company", "CompanysContractors"})));
        results.put("ContainsDuplicatesMN", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.businessAddressId AS a1_businessAddressId, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.personalAddressId AS a1_personalAddressId, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass, a2_.CEOId AS a2_CEOId, a2_.addressId AS a2_addressId, a2_.id AS a2_id, a2_.name AS a2_name, a2_.vatNumber AS a2_vatNumber FROM ImportantPerson AS a1_, Company AS a2_, OldComsOldContracts AS indirect0 WHERE a1_.class = 'org.intermine.model.testmodel.Contractor' AND (a1_.id = indirect0.OldComs AND indirect0.OldContracts = a2_.id) ORDER BY a1_.id, a2_.id");
        results2.put("ContainsDuplicatesMN", new HashSet(Arrays.asList(new String[] {"ImportantPerson", "Company", "OldComsOldContracts"})));
        results.put("ContainsNotMN", NO_RESULT); //TODO: Fix this (ticket #445)
        id1 = (Integer) TypeUtil.getFieldValue(data.get("EmployeeA1"), "id");
        results.put("SimpleGroupBy", "SELECT DISTINCT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber, COUNT(*) AS a2_ FROM Company AS a1_, Department AS a3_ WHERE a1_.id = a3_.companyId GROUP BY a1_.CEOId, a1_.addressId, a1_.id, a1_.name, a1_.vatNumber ORDER BY a1_.id, COUNT(*)");
        results2.put("SimpleGroupBy", new HashSet(Arrays.asList(new String[] {"Department", "Company"})));
        results.put("MultiJoin", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber, a2_.companyId AS a2_companyId, a2_.id AS a2_id, a2_.managerId AS a2_managerId, a2_.name AS a2_name, a3_.addressId AS a3_addressId, a3_.age AS a3_age, a3_.companyId AS a3_companyId, a3_.departmentId AS a3_departmentId, a3_.departmentThatRejectedMeId AS a3_departmentThatRejectedMeId, a3_.fullTime AS a3_fullTime, a3_.id AS a3_id, a3_.intermine_end AS a3_intermine_end, a3_.name AS a3_name, a3_.salary AS a3_salary, a3_.seniority AS a3_seniority, a3_.title AS a3_title, a3_.objectclass AS a3_objectclass, a4_.address AS a4_address, a4_.id AS a4_id FROM Company AS a1_, Department AS a2_, Employee AS a3_, Address AS a4_ WHERE a3_.class = 'org.intermine.model.testmodel.Manager' AND (a1_.id = a2_.companyId AND a2_.managerId = a3_.id AND a3_.addressId = a4_.id AND a3_.name = 'EmployeeA1') ORDER BY a1_.id, a2_.id, a3_.id, a4_.id");
        results2.put("MultiJoin", new HashSet(Arrays.asList(new String[] {"Department", "Employee", "Company", "Address"})));
        results.put("SelectComplex", "SELECT DISTINCT (AVG(a1_.vatNumber) + 20) AS a3_, a2_.name AS a4_, a2_.companyId AS a2_companyId, a2_.id AS a2_id, a2_.managerId AS a2_managerId, a2_.name AS a2_name FROM Company AS a1_, Department AS a2_ GROUP BY a2_.companyId, a2_.id, a2_.managerId, a2_.name ORDER BY (AVG(a1_.vatNumber) + 20), a2_.name, a2_.id");
        results2.put("SelectComplex", new HashSet(Arrays.asList(new String[] {"Department", "Company"})));
        results.put("SelectClassAndSubClasses", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' ORDER BY a1_.name, a1_.id");
        results2.put("SelectClassAndSubClasses", Collections.singleton("Employee"));
        results.put("SelectInterfaceAndSubClasses", NO_RESULT);
        results2.put("SelectInterfaceAndSubClasses", NO_RESULT);
        results.put("SelectInterfaceAndSubClasses2", NO_RESULT);
        results2.put("SelectInterfaceAndSubClasses2", NO_RESULT);
        results.put("SelectInterfaceAndSubClasses3", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.businessAddressId AS a1_businessAddressId, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.personalAddressId AS a1_personalAddressId, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM ImportantPerson AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.ImportantPerson' ORDER BY a1_.id");
        results2.put("SelectInterfaceAndSubClasses3", Collections.singleton("ImportantPerson"));
        results.put("OrderByAnomaly", "SELECT DISTINCT 5 AS a2_, a1_.name AS a3_ FROM Company AS a1_ ORDER BY a1_.name");
        results2.put("OrderByAnomaly", Collections.singleton("Company"));
        Integer id2 = (Integer) TypeUtil.getFieldValue(data.get("CompanyA"), "id");
        Integer id3 = (Integer) TypeUtil.getFieldValue(data.get("DepartmentA1"), "id");
        results.put("SelectClassObjectSubquery", "SELECT DISTINCT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_, Department AS a2_ WHERE (a1_.id = " + id2 + " AND a1_.id = a2_.companyId AND a2_.id IN (SELECT a1_.id FROM Department AS a1_ WHERE a1_.id = " + id3 + ")) ORDER BY a1_.id");
        results2.put("SelectClassObjectSubquery", new HashSet(Arrays.asList(new String[] {"Department", "Company"})));
        results.put("SelectUnidirectionalCollection", "SELECT DISTINCT a2_.id AS a2_id, a2_.name AS a2_name FROM Company AS a1_, Secretary AS a2_, HasSecretarysSecretarys AS indirect0 WHERE (a1_.name = 'CompanyA' AND (a1_.id = indirect0.Secretarys AND indirect0.HasSecretarys = a2_.id)) ORDER BY a2_.id");
        results2.put("SelectUnidirectionalCollection", new HashSet(Arrays.asList(new String[] {"Company", "Secretary", "HasSecretarysSecretarys"})));
        results.put("EmptyAndConstraintSet", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_ WHERE true ORDER BY a1_.id");
        results2.put("EmptyAndConstraintSet", Collections.singleton("Company"));
        results.put("EmptyOrConstraintSet", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_ WHERE false ORDER BY a1_.id");
        results2.put("EmptyOrConstraintSet", Collections.singleton("Company"));
        results.put("EmptyNandConstraintSet", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_ WHERE false ORDER BY a1_.id");
        results2.put("EmptyNandConstraintSet", Collections.singleton("Company"));
        results.put("EmptyNorConstraintSet", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_ WHERE true ORDER BY a1_.id");
        results2.put("EmptyNorConstraintSet", Collections.singleton("Company"));
        results.put("BagConstraint", "SELECT Company.CEOId AS \"Companyceoid\", Company.addressId AS \"Companyaddressid\", Company.id AS \"Companyid\", Company.name AS \"Companyname\", Company.vatNumber AS \"Companyvatnumber\" FROM Company AS Company WHERE (Company.name IN ('CompanyA', 'goodbye', 'hello')) ORDER BY Company.id");
        results2.put("BagConstraint", Collections.singleton("Company"));
        results.put("BagConstraint2", "SELECT Company.CEOId AS \"Companyceoid\", Company.addressId AS \"Companyaddressid\", Company.id AS \"Companyid\", Company.name AS \"Companyname\", Company.vatNumber AS \"Companyvatnumber\" FROM Company AS Company WHERE (Company.id IN (" + id2 + ")) ORDER BY Company.id");
        results2.put("BagConstraint2", Collections.singleton("Company"));
        results.put("InterfaceField", NO_RESULT);
        results2.put("InterfaceField", NO_RESULT);
        results.put("InterfaceReference", NO_RESULT);
        results.put("InterfaceCollection", NO_RESULT);
        Set res = new HashSet();
        results.put("DynamicInterfacesAttribute", NO_RESULT);
        results2.put("DynamicInterfacesAttribute", NO_RESULT);
        results.put("DynamicClassInterface", NO_RESULT);
        results2.put("DynamicClassInterface", NO_RESULT);
        results.put("DynamicClassRef1", NO_RESULT);
        results2.put("DynamicClassRef1", NO_RESULT);
        results.put("DynamicClassRef2", NO_RESULT);
        results2.put("DynamicClassRef2", NO_RESULT);
        results.put("DynamicClassRef3", NO_RESULT);
        results2.put("DynamicClassRef3", NO_RESULT);
        results.put("DynamicClassRef4", NO_RESULT);
        results2.put("DynamicClassRef4", NO_RESULT);
        results.put("DynamicClassConstraint", NO_RESULT);
        results2.put("DynamicClassConstraint", NO_RESULT);
        results.put("ContainsConstraintNull", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' AND a1_.addressId IS NULL ORDER BY a1_.id");
        results2.put("ContainsConstraintNull", Collections.singleton("Employee"));
        results.put("ContainsConstraintNotNull", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' AND a1_.addressId IS NOT NULL ORDER BY a1_.id");
        results2.put("ContainsConstraintNotNull", Collections.singleton("Employee"));
        results.put("ContainsConstraintObjectRefObject", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' AND a1_.departmentId = 5 ORDER BY a1_.id");
        results2.put("ContainsConstraintObjectRefObject", Collections.singleton("Employee"));
        results.put("ContainsConstraintNotObjectRefObject", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' AND a1_.departmentId != 5 ORDER BY a1_.id");
        results2.put("ContainsConstraintNotObjectRefObject", Collections.singleton("Employee"));
        results.put("ContainsConstraintCollectionRefObject", "SELECT a1_.companyId AS a1_companyId, a1_.id AS a1_id, a1_.managerId AS a1_managerId, a1_.name AS a1_name FROM Department AS a1_, Employee AS indirect0 WHERE indirect0.class = 'org.intermine.model.testmodel.Employee' AND (a1_.id = indirect0.departmentId AND indirect0.id = 11) ORDER BY a1_.id");
        results2.put("ContainsConstraintCollectionRefObject", new HashSet(Arrays.asList(new String[] {"Department", "Employee"})));
        results.put("ContainsConstraintNotCollectionRefObject", "SELECT a1_.companyId AS a1_companyId, a1_.id AS a1_id, a1_.managerId AS a1_managerId, a1_.name AS a1_name FROM Department AS a1_, Employee AS indirect0 WHERE indirect0.class = 'org.intermine.model.testmodel.Employee' AND (a1_.id != indirect0.departmentId AND indirect0.id = 11) ORDER BY a1_.id");
        results2.put("ContainsConstraintNotCollectionRefObject", new HashSet(Arrays.asList(new String[] {"Department", "Employee"})));
        results.put("ContainsConstraintMMCollectionRefObject", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_, CompanysContractors AS indirect0 WHERE (a1_.id = indirect0.Contractors AND indirect0.Companys = 3) ORDER BY a1_.id");
        results2.put("ContainsConstraintMMCollectionRefObject", new HashSet(Arrays.asList(new String[] {"Company", "CompanysContractors"})));
        results.put("ContainsConstraintNotMMCollectionRefObject", NO_RESULT); //TODO: Fix this (ticket #445)
        //results.put("ContainsConstraintNotMMCollectionRefObject", "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_, CompanysContractors AS indirect0 WHERE (a1_.id != indirect0.Contractors AND indirect0.Companys = 3) ORDER BY a1_.id");
        //results2.put("ContainsConstraintNotMMCollectionRefObject", new HashSet(Arrays.asList(new String[] {"Company", "CompanysContractors"})));
        results.put("SimpleConstraintNull", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Manager' AND a1_.title IS NULL ORDER BY a1_.id");
        results2.put("SimpleConstraintNull", Collections.singleton("Employee"));
        results.put("SimpleConstraintNotNull", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Manager' AND a1_.title IS NOT NULL ORDER BY a1_.id");
        results2.put("SimpleConstraintNotNull", Collections.singleton("Employee"));
        results.put("TypeCast", "SELECT DISTINCT (a1_.age)::TEXT AS a2_ FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' ORDER BY (a1_.age)::TEXT");
        results2.put("TypeCast", Collections.singleton("Employee"));
        results.put("IndexOf", "SELECT STRPOS(a1_.name, 'oy') AS a2_ FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' ORDER BY STRPOS(a1_.name, 'oy')");
        results2.put("IndexOf", Collections.singleton("Employee"));
        results.put("Substring", "SELECT SUBSTR(a1_.name, 2, 2) AS a2_ FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' ORDER BY SUBSTR(a1_.name, 2, 2)");
        results2.put("Substring", Collections.singleton("Employee"));
        results.put("Substring2", "SELECT SUBSTR(a1_.name, 2) AS a2_ FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' ORDER BY SUBSTR(a1_.name, 2)");
        results2.put("Substring2", Collections.singleton("Employee"));
        results.put("OrderByReference", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' ORDER BY a1_.departmentId, a1_.id");
        results2.put("OrderByReference", Collections.singleton("Employee"));

        InputStream is = FlatModeSqlGeneratorTest.class.getClassLoader().getResourceAsStream("flatModeLargeBag.sql");
        if (is != null) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String largeBagConstraintText = br.readLine();
            results.put("LargeBagConstraint", largeBagConstraintText);
            results2.put("LargeBagConstraint", Collections.singleton("Employee"));
        } else {
            results.put("LargeBagConstraint", "");
        }

        is = FlatModeSqlGeneratorTest.class.getClassLoader().getResourceAsStream("flatModeLargeNotBag.sql");
        if (is != null) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String largeNotBagConstraintText = br.readLine();
            results.put("LargeBagNotConstraint", largeNotBagConstraintText);
            results2.put("LargeBagNotConstraint", Collections.singleton("Employee"));
        } else {
            results.put("LargeBagNotConstraint", "");
        }

        results.put("LargeBagConstraintUsingTable", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' AND a1_.name IN (SELECT value FROM " + LARGE_BAG_TABLE_NAME + ") ORDER BY a1_.id");
        results2.put("LargeBagConstraintUsingTable", Collections.singleton("Employee"));

        results.put("LargeBagNotConstraintUsingTable", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' AND NOT (a1_.name IN (SELECT value FROM " + LARGE_BAG_TABLE_NAME + ")) ORDER BY a1_.id");
        results2.put("LargeBagNotConstraintUsingTable", Collections.singleton("Employee"));

        results.put("NegativeNumbers", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' AND a1_.age > -51 ORDER BY a1_.id");
        results2.put("NegativeNumbers", Collections.singleton("Employee"));

        results.put("Lower", "SELECT LOWER(a1_.name) AS a2_ FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' ORDER BY LOWER(a1_.name)");
        results2.put("Lower", Collections.singleton("Employee"));

        results.put("Upper", "SELECT UPPER(a1_.name) AS a2_ FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' ORDER BY UPPER(a1_.name)");
        results2.put("Upper", Collections.singleton("Employee"));
        results.put("CollectionQueryOneMany", "SELECT a1_.addressId AS a1_addressId, a1_.age AS a1_age, a1_.companyId AS a1_companyId, a1_.departmentId AS a1_departmentId, a1_.departmentThatRejectedMeId AS a1_departmentThatRejectedMeId, a1_.fullTime AS a1_fullTime, a1_.id AS a1_id, a1_.intermine_end AS a1_intermine_end, a1_.name AS a1_name, a1_.salary AS a1_salary, a1_.seniority AS a1_seniority, a1_.title AS a1_title, a1_.objectclass AS a1_objectclass FROM Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee' AND " + id3 + " = a1_.departmentId ORDER BY a1_.id");
        results2.put("CollectionQueryOneMany", Collections.singleton("Employee"));
        Integer id4 = (Integer) TypeUtil.getFieldValue(data.get("CompanyB"), "id");
        results.put("CollectionQueryManyMany", "SELECT a1_.id AS a1_id, a1_.name AS a1_name FROM Secretary AS a1_, HasSecretarysSecretarys AS indirect0 WHERE (" + id4 + " = indirect0.Secretarys AND indirect0.HasSecretarys = a1_.id) ORDER BY a1_.id");
        results2.put("CollectionQueryManyMany", new HashSet(Arrays.asList(new String[] {"Secretary", "HasSecretarysSecretarys"})));
        Integer id5 = (Integer) ((Department) data.get("DepartmentA1")).getId();
        Integer id6 = (Integer) ((Department) data.get("DepartmentB1")).getId();
        results.put("QueryClassBag", "SELECT a2_.departmentId AS a3_, a2_.addressId AS a2_addressId, a2_.age AS a2_age, a2_.companyId AS a2_companyId, a2_.departmentId AS a2_departmentId, a2_.departmentThatRejectedMeId AS a2_departmentThatRejectedMeId, a2_.fullTime AS a2_fullTime, a2_.id AS a2_id, a2_.intermine_end AS a2_intermine_end, a2_.name AS a2_name, a2_.salary AS a2_salary, a2_.seniority AS a2_seniority, a2_.title AS a2_title, a2_.objectclass AS a2_objectclass FROM Employee AS a2_ WHERE a2_.class = 'org.intermine.model.testmodel.Employee' AND (a2_.departmentId IN (" + id5 + ", " + id6 + ")) ORDER BY a2_.departmentId, a2_.id");
        results2.put("QueryClassBag", Collections.singleton("Employee"));
        Integer companyAId = (Integer) ((Company) data.get("CompanyA")).getId();
        Integer companyBId = (Integer) ((Company) data.get("CompanyB")).getId();
        Integer employeeB1Id = (Integer) ((Employee) data.get("EmployeeB1")).getId();
        results.put("QueryClassBagMM", "SELECT indirect0.Secretarys AS a3_, a2_.id AS a2_id, a2_.name AS a2_name FROM Secretary AS a2_, HasSecretarysSecretarys AS indirect0 WHERE ((indirect0.Secretarys IN (" + companyAId + ", " + companyBId + ", " + employeeB1Id + ")) AND indirect0.HasSecretarys = a2_.id) ORDER BY indirect0.Secretarys, a2_.id");
        results2.put("QueryClassBagMM", new HashSet(Arrays.asList(new String[] {"Secretary", "HasSecretarysSecretarys"})));
        results.put("QueryClassBagDynamic", "SELECT indirect0.Secretarys AS a3_, a2_.id AS a2_id, a2_.name AS a2_name FROM Secretary AS a2_, HasSecretarysSecretarys AS indirect0 WHERE ((indirect0.Secretarys IN (" + employeeB1Id + ")) AND indirect0.HasSecretarys = a2_.id) ORDER BY indirect0.Secretarys, a2_.id");
        results2.put("QueryClassBagDynamic", new HashSet(Arrays.asList(new String[] {"Secretary", "HasSecretarysSecretarys"})));
        //results.put("DynamicBagConstraint", NO_RESULT); // See ticket #469
        results.put("DynamicBagConstraint2", NO_RESULT);
    }

    protected DatabaseSchema getSchema() throws Exception {
        //ArrayList truncated = new ArrayList();
        //truncated.add(model.getClassDescriptorByName("org.intermine.model.testmodel.
        //return new DatabaseSchema(model, Collections.EMPTY_LIST, true, new HashSet(Arrays.asList(new String[] {"intermineobject", "thing", "importantperson", "employable", "hasaddress", "hassecretarys", "randominterface", "employee", "manager"})));
        return ((ObjectStoreInterMineImpl) ObjectStoreFactory.getObjectStore("os.flatmodeunittest")).getSchema();
    }
    public String getRegisterOffset1() {
        return "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_ ORDER BY a1_.id";
    }
    public String getRegisterOffset2() {
        return "SELECT a1_.CEOId AS a1_CEOId, a1_.addressId AS a1_addressId, a1_.id AS a1_id, a1_.name AS a1_name, a1_.vatNumber AS a1_vatNumber FROM Company AS a1_ WHERE ";
    }
    public String getRegisterOffset3() {
        return "Employee AS a1_ WHERE a1_.class = 'org.intermine.model.testmodel.Employee'";
    }
    public String getRegisterOffset4() {
        return "AND";
    }
    public String precompTableString() {
        return "SELECT intermine_Alias.CEOId AS \"intermine_Aliasceoid\", intermine_Alias.addressId AS \"intermine_Aliasaddressid\", intermine_Alias.id AS \"intermine_Aliasid\", intermine_Alias.name AS \"intermine_Aliasname\", intermine_Alias.vatNumber AS \"intermine_Aliasvatnumber\" FROM Company AS intermine_Alias ORDER BY intermine_Alias.id";
    }
}
