package mydomain.model;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable = "true")
@Extensions({
    @Extension(vendorName = "datanucleus", key = "multitenancy-column-name", value = "TENANT")
    ,
    @Extension(vendorName = "datanucleus", key = "multitenancy-column-length", value = "24"),})
public class Person {

    @PrimaryKey
    Long id;

    String name;

    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
