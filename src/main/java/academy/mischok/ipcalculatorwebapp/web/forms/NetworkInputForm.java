package academy.mischok.ipcalculatorwebapp.web.forms;

import javax.validation.constraints.NotBlank;

public class NetworkInputForm {

    //@NotBlank
    private String ipAddress;

    //@NotBlank
    private String subNetMask;

    //@NotBlank
    private int cidr;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSubNetMask() {
        return subNetMask;
    }

    public void setSubNetMask(String subNetMask) {
        this.subNetMask = subNetMask;
    }

    public int getCidr() {
        return cidr;
    }

    public void setCidr(int cidr) {
        this.cidr = cidr;
    }

    @Override
    public String toString() {
        return "NetworkInputForm{" +
                "ipAddress='" + ipAddress + '\'' +
                ", subNetMask='" + subNetMask + '\'' +
                '}';
    }
}