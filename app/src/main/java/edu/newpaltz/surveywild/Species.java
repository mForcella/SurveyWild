package edu.newpaltz.surveywild;

public class Species {

    String id, code, cName, sName, kingdom, phylum, sclass, order, family, genus, date, flag;

    public Species(String id, String code, String cName, String sName, String kingdom, String phylum,
                   String sclass, String order, String family, String genus, String date, String flag) {
        this.id = id;
        this.code = code;
        this.cName = cName;
        this.sName = sName;
        this.kingdom = kingdom;
        this.phylum = phylum;
        this.sclass = sclass;
        this.order = order;
        this.family = family;
        this.genus = genus;
        this.date = date;
        this.flag = flag;
    }

    public int getId() {
        return Integer.parseInt(id);
    }

    public String getCode() {
        return code;
    }

    public String getcName() {
        return cName;
    }

    public String getsName() {
        return sName;
    }

    public String getKingdom() {
        return kingdom;
    }

    public String getPhylum() {
        return phylum;
    }

    public String getSclass() {
        return sclass;
    }

    public String getOrder() {
        return order;
    }

    public String getFamily() {
        return family;
    }

    public String getGenus() {
        return genus;
    }

    public String getDate() {
        return date;
    }

    public String getFlag() {
        return flag;
    }
}
