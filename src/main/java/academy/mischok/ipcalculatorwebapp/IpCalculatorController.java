package academy.mischok.ipcalculatorwebapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class IpCalculatorController {

    @GetMapping("main")
    public String startMain(Model model,
                            @RequestParam String ipAddress,
                            @RequestParam String subNetMask) {
        try {
            IpAddress getIp = new IpAddress(ipAddress);
            SubnetMask getSnm = new SubnetMask(subNetMask);

            IpAddress netId = calculateNetId(getIp, getSnm);
            int cidr = getSnm.getCidr();

            model.addAttribute("ipAddress", getIp.toString() + " /" + cidr);
            model.addAttribute("ipBinary", getIp.toBinaryString());

            model.addAttribute("subNetMask", getSnm.toString());
            model.addAttribute("snmBinary", getSnm.toBinaryString());

            model.addAttribute("netId", netId);
            model.addAttribute("netIdBinary", netId.toBinaryString());

            model.addAttribute("broadcast", calculateBroadcastIp(netId, getSnm));
            model.addAttribute("broadcastBinary", calculateBroadcastIp(netId, getSnm).toBinaryString());

            model.addAttribute("clients", (int)SubnetMask.numberOfClients(cidr));
            return "mainTemplate";
        }catch (IllegalArgumentException ex ){
            return "errorTemplate";
        }
    }

    @GetMapping("input")
    public String input(){
        IpAddress ipAddress = new IpAddress();

        return "inputTemplate";
    }

    public static IpAddress calculateNetId(IpAddress ip, SubnetMask snm) {
        Objects.requireNonNull(ip);
        Objects.requireNonNull(snm);

        return ip.logicalAnd(snm);
    }
    public static IpAddress calculateBroadcastIp(IpAddress netId, SubnetMask subnetmask) {
        Objects.requireNonNull(netId);
        Objects.requireNonNull(subnetmask);

        IpAddress invertedSnm = subnetmask.invert();

        int first = netId.getFirst() + invertedSnm.getFirst();
        int second = netId.getSecond() + invertedSnm.getSecond();
        int third = netId.getThird() + invertedSnm.getThird();
        int fourth = netId.getFourth() + invertedSnm.getFourth();

        return new IpAddress(first, second, third, fourth);
    }
}