package de.wakeapp.controller;

import de.wakeapp.bean.AlarmTimeFormBean;
import de.wakeapp.service.AlarmTimeService;
import de.wakeapp.service.bvg.location.BvgLocationQueryService;
import de.wakeapp.validator.AlarmTimeFormBeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class AlarmTimeController {
    private final AlarmTimeService alarmTimeService;

    private final AlarmTimeFormBeanValidator alarmTimeFormBeanValidator;

    @Autowired
    public AlarmTimeController(AlarmTimeService alarmTimeService, AlarmTimeFormBeanValidator alarmTimeFormBeanValidator) {
        this.alarmTimeService = alarmTimeService;
        this.alarmTimeFormBeanValidator = alarmTimeFormBeanValidator;
    }

    @InitBinder("alarmTimeFormBean")
    protected void initAlarmTimeFormBinder(WebDataBinder binder) {
        binder.setValidator(alarmTimeFormBeanValidator);
    }


    @GetMapping({"", "/", "/alarmForm"})
    public String index(Model model) {
        model.addAttribute("alarmForm", new AlarmTimeFormBean());
        return "index";
    }

    @PostMapping("/alarmForm")
    public String calculateAlarmTime(@Validated @ModelAttribute AlarmTimeFormBean alarmTimeFormBean, BindingResult bindingResult, Model model) {
        model.addAttribute("bindingResult", bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("alarmForm", alarmTimeFormBean);
            return "index";
        }

        replaceAddressesWithDescriptiveNames(alarmTimeFormBean);

        LocalDateTime alarmTime = alarmTimeService.calculateAlarmTime(alarmTimeFormBean);
        alarmTimeFormBean.setCalculatedAlarmTime(alarmTime.toString());

        model.addAttribute("alarmForm", alarmTimeFormBean);
        return "index";
    }

    private void replaceAddressesWithDescriptiveNames(AlarmTimeFormBean alarmTimeFormBean) {
        String descriptiveDestinationName = BvgLocationQueryService.getLocationInfoForQuery(alarmTimeFormBean.getAddressDestination()).getDescriptiveName();
        String descriptiveResidenceName = BvgLocationQueryService.getLocationInfoForQuery(alarmTimeFormBean.getAddressResidence()).getDescriptiveName();

        alarmTimeFormBean.setAddressDestination(descriptiveDestinationName);
        alarmTimeFormBean.setAddressResidence(descriptiveResidenceName);
    }
}
