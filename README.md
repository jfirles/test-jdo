test-jdo
========

I have checked that the problem is related with the query cachue (QueryDatastoreCompilationCache) in the core project. The first tenantId setted persist over the later queries.
The key used for cache the queries not complain the multitenancy.

I have fixed the problem with this code:

```
diff --git src/main/java/org/datanucleus/store/query/AbstractJDOQLQuery.java src/main/java/org/datanucleus/store/query/AbstractJDOQLQuery.java
index 9708eedd..b550c5da 100644
--- src/main/java/org/datanucleus/store/query/AbstractJDOQLQuery.java
+++ src/main/java/org/datanucleus/store/query/AbstractJDOQLQuery.java
@@ -212,9 +212,13 @@ public abstract class AbstractJDOQLQuery extends AbstractJavaQuery
         {
             queryCacheKey += (" " + getFetchPlan().toString());
         }
-
-        return queryCacheKey;
+       
+       String multiTenancyId = ec.getNucleusContext().getMultiTenancyId(ec, getCandidateClassMetaData());
+       if (multiTenancyId != null) {
+           return multiTenancyId + " " + queryCacheKey;
        }
+        return queryCacheKey;
+    }

     /**
      * Method to take the defined parameters for the query and form a single string.
```

