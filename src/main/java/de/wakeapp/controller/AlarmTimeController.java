package de.wakeapp.controller;

import de.wakeapp.bean.AlarmTimeFormBean;
import de.wakeapp.service.AlarmFormRepositoryService;
import de.wakeapp.service.AlarmTimeService;
import de.wakeapp.service.bvg.location.BvgLocationQueryService;
import de.wakeapp.validator.AlarmTimeFormBeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class AlarmTimeController {
    private final AlarmTimeService alarmTimeService;

    private final AlarmTimeFormBeanValidator alarmTimeFormBeanValidator;

    private final AlarmFormRepositoryService alarmFormRepositoryService;

    @Autowired
    public AlarmTimeController(AlarmTimeService alarmTimeService, AlarmTimeFormBeanValidator alarmTimeFormBeanValidator,
                               AlarmFormRepositoryService alarmFormRepositoryService) {
        this.alarmTimeService = alarmTimeService;
        this.alarmTimeFormBeanValidator = alarmTimeFormBeanValidator;
        this.alarmFormRepositoryService = alarmFormRepositoryService;
    }

    @InitBinder("alarmTimeFormBean")
    protected void initAlarmTimeFormBinder(WebDataBinder binder) {
        binder.setValidator(alarmTimeFormBeanValidator);
    }


    @GetMapping({"", "/", "/alarmForm"})
    public String getNewForm(Model model) {
        model.addAttribute("alarmForm", new AlarmTimeFormBean());
        return "index";
    }

    @GetMapping("/alarmForm/{id}")
    public String getForm(@PathVariable Long id, Model model) {
        model.addAttribute("alarmForm", alarmFormRepositoryService.retrieveAlarmFormAsBean(id));
        return "index";
    }

    @RequestMapping(value = "/alarmForm", method = RequestMethod.POST, params = "action=calculate")
    public String calculateAlarmTime(@Validated @ModelAttribute AlarmTimeFormBean alarmTimeFormBean, BindingResult bindingResult, Model model) {
        model.addAttribute("bindingResult", bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("alarmForm", alarmTimeFormBean);
            return "index";
        }

        replaceAddressesWithDescriptiveNames(alarmTimeFormBean);

        LocalDateTime alarmTime = alarmTimeService.calculateAlarmTime(alarmTimeFormBean);
        alarmTimeFormBean.setCalculatedAlarmTime(formatCalculatedAlarmTime(alarmTime.toString()));

        model.addAttribute("alarmForm", alarmTimeFormBean);
        return "index";
    }

    @RequestMapping(value = "/alarmForm", method = RequestMethod.POST, params = "action=save")
    public String saveAlarmTimeForm(@Validated @ModelAttribute AlarmTimeFormBean alarmTimeFormBean, BindingResult bindingResult, Model model) {
        model.addAttribute("bindingResult", bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("alarmForm", alarmTimeFormBean);
            return "index";
        }

        replaceAddressesWithDescriptiveNames(alarmTimeFormBean);

        alarmFormRepositoryService.saveAlarmTimeFormBean(alarmTimeFormBean);

        model.addAttribute("alarmFormSavedToDb", true);
        model.addAttribute("alarmForm", alarmTimeFormBean);
        return "index";
    }

    private void replaceAddressesWithDescriptiveNames(AlarmTimeFormBean alarmTimeFormBean) {
        String descriptiveDestinationName = BvgLocationQueryService.getLocationInfoForQuery(alarmTimeFormBean.getAddressDestination()).getDescriptiveName();
        String descriptiveResidenceName = BvgLocationQueryService.getLocationInfoForQuery(alarmTimeFormBean.getAddressResidence()).getDescriptiveName();

        alarmTimeFormBean.setAddressDestination(descriptiveDestinationName);
        alarmTimeFormBean.setAddressResidence(descriptiveResidenceName);
    }

    private String formatCalculatedAlarmTime(String unformattedAlarmTime) {
        unformattedAlarmTime = unformattedAlarmTime.replace("T", " ");
        return unformattedAlarmTime;
    }
}
