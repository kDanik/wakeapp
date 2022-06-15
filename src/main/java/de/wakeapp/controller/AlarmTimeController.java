package de.wakeapp.controller;

import de.wakeapp.bean.AlarmTimeFormBean;
import de.wakeapp.model.AlarmForm;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getForm(Model model) {
        model.addAttribute("alarmFormNameIdPairs", alarmFormRepositoryService.retrieveAllIdNamePairs());

        if (model.containsAttribute("retrieveAlarmFormWithId")) {
            try {
                Integer id = (Integer) model.getAttribute("retrieveAlarmFormWithId");
                AlarmTimeFormBean alarmTimeFormBean = alarmFormRepositoryService.retrieveAlarmFormAsBean(id);

                model.addAttribute("alarmForm", alarmTimeFormBean);
            } catch (Exception e) {
                model.addAttribute("alarmForm", new AlarmTimeFormBean());
            }
        } else {
            model.addAttribute("alarmForm", new AlarmTimeFormBean());
        }

        return "index";
    }

    @GetMapping("/alarmForm/{id}")
    public String getSavedForm(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (alarmFormRepositoryService.isValidId(id)) {
            redirectAttributes.addFlashAttribute("retrieveAlarmFormWithId", id);
        }

        return "redirect:/alarmForm";
    }

    @RequestMapping(value = "/alarmForm", method = RequestMethod.POST, params = "action=calculate")
    public String calculateAlarmTime(@Validated @ModelAttribute AlarmTimeFormBean alarmTimeFormBean, BindingResult bindingResult, Model model) {
        model.addAttribute("alarmFormNameIdPairs", alarmFormRepositoryService.retrieveAllIdNamePairs());
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
    public String saveAlarmTimeForm(@Validated @ModelAttribute AlarmTimeFormBean alarmTimeFormBean, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("bindingResult", bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("alarmFormNameIdPairs", alarmFormRepositoryService.retrieveAllIdNamePairs());
            model.addAttribute("alarmForm", alarmTimeFormBean);
            return "index";
        }

        replaceAddressesWithDescriptiveNames(alarmTimeFormBean);
        AlarmForm savedForm = alarmFormRepositoryService.saveAlarmTimeFormBean(alarmTimeFormBean);

        redirectAttributes.addFlashAttribute("alarmFormSavedToDb", true);
        redirectAttributes.addFlashAttribute("retrieveAlarmFormWithId", savedForm.getId());

        return "redirect:/alarmForm";
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
