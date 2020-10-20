package com.mk.camel.model;

/*
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAttribute
*/
public class StudentAllocation {


    int nursery;

    int reception;

    int others;

    @Override
    public String toString() {
        return "{" +
                "nursery=" + nursery +
                ", reception=" + reception +
                ", others=" + others +
                '}';
    }

    public int getNursery() {
        return nursery;
    }

    public void setNursery(int nursery) {
        this.nursery = nursery;
    }

    public int getReception() {
        return reception;
    }

    public void setReception(int reception) {
        this.reception = reception;
    }

    public int getOthers() {
        return others;
    }

    public void setOthers(int others) {
        this.others = others;
    }
}
