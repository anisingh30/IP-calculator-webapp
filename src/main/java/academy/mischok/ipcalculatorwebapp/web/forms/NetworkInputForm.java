package academy.mischok.ipcalculatorwebapp.web.forms;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class NetworkInputForm {

    @NotBlank
    private String ipAddress;

    //@NotBlank
    private String subNetMask;

    //@NotBlank
    private Integer cidr;

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

    public Integer getCidr() {
        return cidr;
    }

    public void setCidr(Integer cidr) {
        this.cidr = cidr;
    }

    @Override
    public String toString() {
        return "NetworkInputForm{" +
                "ipAddress='" + ipAddress + '\'' +
                ", subNetMask='" + subNetMask + '\'' +
                ", cidr='" + cidr + '\'' +
                '}';
    }
}