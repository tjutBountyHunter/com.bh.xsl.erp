package xsl.erp.pojo;

import java.util.Date;

public class XslHunterLevelPrivilege {
    private Integer id;

    private Integer hunterlevelid;

    private Integer privilegeid;

    private Date createdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHunterlevelid() {
        return hunterlevelid;
    }

    public void setHunterlevelid(Integer hunterlevelid) {
        this.hunterlevelid = hunterlevelid;
    }

    public Integer getPrivilegeid() {
        return privilegeid;
    }

    public void setPrivilegeid(Integer privilegeid) {
        this.privilegeid = privilegeid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}