package academy.mischok.ipcalculatorwebapp.web;

import academy.mischok.ipcalculatorwebapp.domain.IpAddress;
import academy.mischok.ipcalculatorwebapp.domain.SubnetMask;
import academy.mischok.ipcalculatorwebapp.web.forms.NetworkInputForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class IpCalculatorController {

    @PostMapping("input")
    public String startMain(@Valid NetworkInputForm networkInputForm, BindingResult bindingResult, Model model) {

        System.out.println("form: " + networkInputForm);
        System.out.println("bindingResult: " + bindingResult);

        if (!Objects.isNull(networkInputForm.getCidr()) && networkInputForm.getSubNetMask() != ""){
            bindingResult.rejectValue("ipAddress", "invalid.snmCidr", "Please insert just the CIDR or the Subnetmask.");
        }

        validate(networkInputForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "inputTemplate";
        }

        try {

            IpAddress getIp = new IpAddress(networkInputForm.getIpAddress());
            SubnetMask getSnm;

            if(networkInputForm.getSubNetMask() != ""){
                getSnm = new SubnetMask(networkInputForm.getSubNetMask());
            }
            else{
                getSnm = SubnetMask.fromCidrSuffix(networkInputForm.getCidr());
            }

            IpAddress netId = calculateNetId(getIp, getSnm);
            int cidr = getSnm.writeCidr();

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

    private void validate(NetworkInputForm networkInputForm, BindingResult bindingResult) {
        Objects.requireNonNull(networkInputForm);
        Objects.requireNonNull(bindingResult);

        if (!bindingResult.hasFieldErrors("ipAddress")) {
            try {
                new IpAddress(networkInputForm.getIpAddress());
            } catch (IllegalArgumentException e) {
                bindingResult.rejectValue("ipAddress", "invalid.ip", "IP address not valid.");
            }
        }

        if (!bindingResult.hasFieldErrors("subNetMask") && networkInputForm.getSubNetMask() != "")  {
            try {
                new SubnetMask(networkInputForm.getSubNetMask());
            } catch (IllegalArgumentException e) {
                bindingResult.rejectValue("subNetMask", "invalid.subNetMask", "Subnetmask not valid.");
            }
        }

        if (!bindingResult.hasFieldErrors("cidr") && !Objects.isNull(networkInputForm.getCidr())) {
            try {
                new SubnetMask(networkInputForm.getCidr());
            } catch (IllegalArgumentException e) {
                bindingResult.rejectValue("cidr", "invalid.cidr", "Cidr not valid.");
            }
        }
    }

    @GetMapping("input")
    public String input(Model model){
        NetworkInputForm networkInputForm = new NetworkInputForm();

        model.addAttribute("networkInputForm", networkInputForm);

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