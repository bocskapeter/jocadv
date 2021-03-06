package eu.bopet.jocadv.core.features;

import java.util.UUID;

public abstract class Feature {
    private UUID id;
    private String name;
    private boolean selected;

    public Feature() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
