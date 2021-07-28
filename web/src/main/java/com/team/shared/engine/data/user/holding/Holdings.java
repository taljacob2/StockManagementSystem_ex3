package com.team.shared.engine.data.user.holding;

import com.team.shared.engine.data.collection.EngineCollection;
import com.team.shared.engine.data.user.holding.item.Item;
import com.team.shared.engine.data.xjc.generated.RseHoldings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link Item}s {@code Collection}. wrapped in a special class. Has a {@code
 * Collection} field of all the {@link Item}s together.
 * <p>
 * annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 * </p>
 *
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "rse-holdings")
public class Holdings extends EngineCollection<List<Item>, Item> {

    /**
     * <b><i>important:</i></b>
     * The <i>default</i> {@code Constructor} is <i>initializing</i> the {@link
     * List}.
     */
    public Holdings() {
        setCollection(new ArrayList<>());
    }

    public Holdings(RseHoldings rseHoldings) {
        setCollection(new ArrayList<>());
        this.setCollection(rseHoldings.getRseItem().stream().map(Item::new)
                .collect(Collectors.toList()));
    }

    @Override public List<Item> getCollection() {
        return super.getCollection();
    }

    @XmlElement(name = "rse-item")
    public void setCollection(ArrayList<Item> collection) {
        super.setCollection(collection);
    }

}
