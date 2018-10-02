package xsl.cms.pojo;

import java.util.Date;

public class XslMasterLevelExperience {
    private Integer id;

    private Integer masterlevelid;

    private Short level;

    private Short experience;

    private Date createdate;

    private Date updatedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasterlevelid() {
        return masterlevelid;
    }

    public void setMasterlevelid(Integer masterlevelid) {
        this.masterlevelid = masterlevelid;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Short getExperience() {
        return experience;
    }

    public void setExperience(Short experience) {
        this.experience = experience;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}