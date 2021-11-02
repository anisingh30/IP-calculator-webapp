package academy.mischok.ipcalculatorwebapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IpCalculatorController {

    @GetMapping("main")
    public String startMain(Model model,
                            @RequestParam String ipAddress,
                            @RequestParam String subNetMask) {
        model.addAttribute("ipAddress", ipAddress);
        model.addAttribute("subNetMask", subNetMask);
        return "mainTemplate";
    }
}