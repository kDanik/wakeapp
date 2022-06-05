package de.wakeapp.controller;

import de.wakeapp.bean.AlarmTimeFormBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AlarmTimeController {
    @GetMapping({"", "/", "/alarmForm"})
    public String index(Model model) {
        model.addAttribute("alarmForm", new AlarmTimeFormBean());
        return "index";
    }

    @PostMapping("/alarmForm")
    public String calculateAlarmTime(@ModelAttribute AlarmTimeFormBean alarmTimeFormBean, Model model) {
        alarmTimeFormBean.setCalculatedAlarmTime("test");
        model.addAttribute("alarmForm", alarmTimeFormBean);
        return "index";
    }
}
