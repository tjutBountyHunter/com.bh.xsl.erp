package xsl.cms.pojo;

public class XslThrowinglog {
    private Integer id;

    private String ip;

    private String throwing;

    private String operationer;

    private String throwingtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getThrowing() {
        return throwing;
    }

    public void setThrowing(String throwing) {
        this.throwing = throwing == null ? null : throwing.trim();
    }

    public String getOperationer() {
        return operationer;
    }

    public void setOperationer(String operationer) {
        this.operationer = operationer == null ? null : operationer.trim();
    }

    public String getThrowingtime() {
        return throwingtime;
    }

    public void setThrowingtime(String throwingtime) {
        this.throwingtime = throwingtime;
    }
}