package org.datanucleus.test;

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
            // create 1 Item without primary key (IdGeneratorStrategy.IDENTITY)
            Item item = new Item();
            item.setName("item 1");
            pm.makePersistent(item);
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
