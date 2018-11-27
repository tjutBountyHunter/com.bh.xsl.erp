package xsl.cms.pojo;

public class XslOutputBill {
    private Integer id;

    private String outputid;

    private Double outputMoney;

    private String tradetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutputid() {
        return outputid;
    }

    public void setOutputid(String outputid) {
        this.outputid = outputid == null ? null : outputid.trim();
    }

    public Double getOutputMoney() {
        return outputMoney;
    }

    public void setOutputMoney(Double outputMoney) {
        this.outputMoney = outputMoney;
    }

    public String getTradetime() {
        return tradetime;
    }

    public void setTradetime(String tradetime) {
        this.tradetime = tradetime;
    }
}