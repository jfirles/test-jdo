/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mydomain.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author jfirles
 */
@PersistenceCapable(table = "ITEMS", detachable = "true")
public class Item {

    @PrimaryKey
    @Persistent(column = "ID", valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent(column = "NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
