package mydomain.model;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
@Extension(vendorName = "datanucleus", key = "multitenancy-disable", value = "true")
public class Company
{
    @PrimaryKey
    Long id;

    String name;

    public Company(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
