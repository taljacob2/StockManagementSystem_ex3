package com.team.shared.engine.data.order;

import com.team.shared.engine.data.collection.EngineCollection;
import com.team.shared.engine.data.collection.list.SortableLinkedList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * {@link Order}s {@code Collection}. wrapped in a special class. Has a {@code
 * Collection} field of all the {@link Order}s together.
 * <p>
 * annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 * </p>
 *
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "rse-sell-orders")
public class SellOrders
        extends EngineCollection<SortableLinkedList<Order>, Order>
        implements Serializable, Cloneable {

    private static final long serialVersionUID = -4172679817949201440L;

    /**
     * <b><i>important:</i></b>
     * The <i>default</i> {@code Constructor} is <i>initializing</i> the {@link
     * SortableLinkedList}.
     */
    public SellOrders() {
        setCollection(new SortableLinkedList<>());
    }

    public SellOrders clone() throws CloneNotSupportedException {
        SellOrders clone = (SellOrders) super.clone();
        clone.setCollection((SortableLinkedList<Order>) getCollection().clone());
        return clone;
    }

    @Override public SortableLinkedList<Order> getCollection() {
        return super.getCollection();
    }

    @XmlElement(name = "rse-sell-order")
    public void setCollection(SortableLinkedList<Order> collection) {
        super.setCollection(collection);
    }

}
