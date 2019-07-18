package org.datanucleus.test;

import java.util.*;
import org.junit.*;
import javax.jdo.*;

import static org.junit.Assert.*;
import mydomain.model.*;
import org.datanucleus.util.NucleusLogger;

public class SimpleTest {

    @Test
    public void testSimple() {
        NucleusLogger.GENERAL.info(">> test START");
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("MyTest");

        // first step, prepare de data
        PersistenceManager pm = pmf.getPersistenceManager();
        // I set the tenantId to null
        pm.setProperty("datanucleus.tenantId", null);
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            // I create 2 companies with null tenant
            Company com1 = new Company(1, "company 1");
            pm.makePersistent(com1);
            Company com2 = new Company(2, "company 2");
            pm.makePersistent(com2);

            // now, I create 1 person in company 1, tenantid 1
            // I use the id of the company as tenantId
            pm.setProperty("datanucleus.tenantId", String.valueOf(com1.getId()));
            Person person1 = new Person(1, "person 1 company 1");
            pm.makePersistent(person1);
            // 1 person in company 2
            pm.setProperty("datanucleus.tenantId", String.valueOf(com2.getId()));
            Person person2 = new Person(2, "person 2 company 2");
            pm.makePersistent(person2);
            tx.commit();
        } catch (Throwable thr) {
            NucleusLogger.GENERAL.error(">> Exception in test", thr);
            fail("Failed test : " + thr.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        // second step, verify the multitenancy with the queries
        pm = pmf.getPersistenceManager();
        tx = pm.currentTransaction();
        try {
            tx.begin();

            // get the persons for company 1
            pm.setProperty("datanucleus.tenantId", String.valueOf(1));
            // in this query I must found person with id "1"
            List<Person> persons = (List<Person>) pm.newQuery(Person.class).execute();
            if (persons.size() != 1) {
                NucleusLogger.GENERAL.error(">> Exception in test, more than one person in company 1");
                fail("Failed test : more than one person in company 1");
            }
            Person person1 = persons.get(0);
            if (person1.getId() != 1) {
                NucleusLogger.GENERAL.error(">> Exception in test, person 1 not found for company 1");
                fail("Failed test : person 1 not found for company 1");
            }

            // now, get the persons for company 2
            pm.setProperty("datanucleus.tenantId", String.valueOf(2));
            // in this query I must found person with id "2"
            persons = (List<Person>) pm.newQuery(Person.class).execute();
            if (persons.size() != 1) {
                NucleusLogger.GENERAL.error(">> Exception in test, more than one person in company 2");
                fail("Failed test : more than one person in company 2");
            }
            Person person2 = persons.get(0);
            if (person2.getId() != 2) {
                NucleusLogger.GENERAL.error(">> Exception in test, person 2 not found for company 2");
                fail("Failed test : person 2 not found for company 2");
            }
            tx.commit();
        } catch (Throwable thr) {
            NucleusLogger.GENERAL.error(">> Exception in test", thr);
            fail("Failed test : " + thr.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        pmf.close();
        NucleusLogger.GENERAL.info(">> test END");
    }
}
