package com.team.shared.engine.data.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team.shared.engine.data.collection.EngineCollection;
import com.team.shared.engine.data.collection.list.SortableLinkedList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * {@link Transaction}s {@code Collection}. wrapped in a special class. Has a
 * {@code Collection} field of all the {@link Transaction}s together.
 * <p>
 * annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 * </p>
 *
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rse-successfully-finished-transactions")
public class Transactions
        extends EngineCollection<SortableLinkedList<Transaction>,
        Transaction> implements Serializable {

    private static final long serialVersionUID = 613319685593013418L;

    /**
     * <b><i>important:</i></b>
     * The <i>default</i> {@code Constructor} is <i>initializing</i> the {@link
     * SortableLinkedList}.
     */
    public Transactions() {
        setCollection(new SortableLinkedList<>());
    }

    @JsonIgnore
    @Override public SortableLinkedList<Transaction> getCollection() {
        return super.getCollection();
    }

    @JsonIgnore
    @XmlElement(name = "rse-transaction")
    public void setCollection(SortableLinkedList<Transaction> collection) {
        super.setCollection(collection);
    }

}
