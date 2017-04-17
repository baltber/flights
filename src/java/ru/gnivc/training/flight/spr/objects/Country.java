
package ru.gnivc.training.flight.spr.objects;

/**
 *
 * @author TaylakovSA
 */
public class Country {
    private long id;
    private String name;
    private String desc;
    private String code;
    private byte [] flag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String shortName) {
        this.code = shortName;
    }

    public byte[] getFlag() {
        return flag;
    }

    public void setFlag(byte[] flag) {
        this.flag = flag;
    }
    
}
