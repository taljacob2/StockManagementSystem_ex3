package com.team.shared.engine.data.user;

import com.team.shared.engine.data.collection.EngineCollection;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link User}s {@code Collection}. wrapped in a special class. Has a {@code
 * Collection} field of all the {@link User}s together.
 * <p>
 * annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 * </p>
 *
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "rse-users")
@ToString public class Users extends EngineCollection<List<User>, User> {

    public Users() {
        setCollection(new ArrayList<User>());
    }

    /**
     * {@code Constructor} that initializes the {@link java.util.Collection}.
     */
    public Users(boolean setCollection) {
        if (setCollection) {
            setCollection(new ArrayList<User>());
        }
    }


    @Override public List<User> getCollection() {
        return super.getCollection();
    }

    // there must be at least 1 stock in the system, thus 'required = true':
    @XmlElement(name = "rse-user", required = true) public void setCollection(
            List<User> collection) {
        super.setCollection(collection);
    }

}
