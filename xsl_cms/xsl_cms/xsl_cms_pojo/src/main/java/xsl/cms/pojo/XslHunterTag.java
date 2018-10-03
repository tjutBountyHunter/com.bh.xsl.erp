package xsl.cms.pojo;

import java.util.Date;

public class XslHunterTag {
    private Integer id;

    private Integer hunterid;

    private Integer tagid;

    private String createdate;

    private Boolean state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHunterid() {
        return hunterid;
    }

    public void setHunterid(Integer hunterid) {
        this.hunterid = hunterid;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}