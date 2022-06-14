package de.wakeapp.validator;

import de.wakeapp.bean.AlarmTimeFormBean;
import de.wakeapp.service.bvg.location.BvgLocationQueryService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Component
public class AlarmTimeFormBeanValidator implements Validator {
    @Override
    public boolean supports(Class<?> supportedClass) {
        return AlarmTimeFormBean.class.equals(supportedClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof AlarmTimeFormBean) {
            AlarmTimeFormBean alarmTimeFormBean = (AlarmTimeFormBean) target;

            validateArrivalTime(alarmTimeFormBean.getArrivalTime(), errors);
            validateTimeToGetReady(alarmTimeFormBean.getTimeToGetReady(), errors);
            validateAddressDestination(alarmTimeFormBean.getAddressDestination(), errors);
            validateAddressResidence(alarmTimeFormBean.getAddressResidence(), errors);
        }
    }

    private void validateArrivalTime(String arrivalTime, Errors errors) {
        try {
            LocalTime.parse(arrivalTime);
        } catch (DateTimeParseException e) {
            errors.rejectValue("arrivalTime", "error.arrivalTime.invalid");
        }
    }

    private void validateTimeToGetReady(Integer timeToGetReady, Errors errors) {
        if (timeToGetReady == null) {
            errors.rejectValue("timeToGetReady", "error.timeToGetReady.empty");
            return;
        }

        if (timeToGetReady < 0) {
            errors.rejectValue("timeToGetReady", "error.timeToGetReady.invalid");
        }
    }

    private void validateAddressResidence(String addressResidence, Errors errors) {
        if (addressResidence == null || addressResidence.isEmpty() || addressResidence.isBlank()) {
            errors.rejectValue("addressResidence", "error.residence.empty");
            return;
        }
        if (BvgLocationQueryService.getLocationInfoForQuery(addressResidence) == null) {
            errors.rejectValue("addressResidence", "error.residence.invalid");
        }
    }

    private void validateAddressDestination(String addressDestination, Errors errors) {
        if (addressDestination == null || addressDestination.isEmpty() || addressDestination.isBlank()) {
            errors.rejectValue("addressDestination", "error.destination.empty");
            return;
        }

        if (BvgLocationQueryService.getLocationInfoForQuery(addressDestination) == null) {
            errors.rejectValue("addressDestination", "error.destination.invalid");
        }
    }
}
