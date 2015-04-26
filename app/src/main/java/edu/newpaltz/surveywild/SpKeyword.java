package edu.newpaltz.surveywild;

public class SpKeyword {

    String id, sid, keyword, flag;

    public SpKeyword(String id, String sid, String keyword, String flag) {
        this.id = id;
        this.sid = sid;
        this.keyword = keyword;
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getFlag() {
        return flag;
    }
}
