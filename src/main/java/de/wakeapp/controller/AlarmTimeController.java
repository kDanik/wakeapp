package de.wakeapp.controller;

import de.wakeapp.bean.AlarmTimeFormBean;
import de.wakeapp.service.AlarmTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class AlarmTimeController {
    private final AlarmTimeService alarmTimeService;

    @Autowired
    public AlarmTimeController(AlarmTimeService alarmTimeService) {
        this.alarmTimeService = alarmTimeService;
    }

    @GetMapping({"", "/", "/alarmForm"})
    public String index(Model model) {
        model.addAttribute("alarmForm", new AlarmTimeFormBean());
        return "index";
    }

    @PostMapping("/alarmForm")
    public String calculateAlarmTime(@ModelAttribute AlarmTimeFormBean alarmTimeFormBean, Model model) {
        LocalDateTime alarmTime = alarmTimeService.calculateAlarmTime(alarmTimeFormBean);
        alarmTimeFormBean.setCalculatedAlarmTime(alarmTime.toString());

        model.addAttribute("alarmForm", alarmTimeFormBean);
        return "index";
    }
}
