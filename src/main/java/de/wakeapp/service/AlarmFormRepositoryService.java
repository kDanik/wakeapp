package de.wakeapp.service;

import de.wakeapp.bean.AlarmTimeFormBean;
import de.wakeapp.model.AlarmForm;
import de.wakeapp.repository.AlarmFormRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AlarmFormRepositoryService {
    private final AlarmFormRepository alarmFormRepository;

    public AlarmFormRepositoryService(AlarmFormRepository alarmFormRepository) {
        this.alarmFormRepository = alarmFormRepository;
    }

    public AlarmForm saveAlarmTimeFormBean(AlarmTimeFormBean alarmTimeFormBean) {
        AlarmForm newAlarmForm = new AlarmForm();

        newAlarmForm.setArrivalTime(alarmTimeFormBean.getArrivalTime());

        newAlarmForm.setTimeToGetReady(alarmTimeFormBean.getTimeToGetReady());

        newAlarmForm.setAddressDestination(alarmTimeFormBean.getAddressDestination());
        newAlarmForm.setAddressResidence(alarmTimeFormBean.getAddressResidence());

        newAlarmForm.setUseBus(alarmTimeFormBean.getUseBus());
        newAlarmForm.setUseSAndUBahn(alarmTimeFormBean.getUseSAndUBahn());
        newAlarmForm.setUseTram(alarmTimeFormBean.getUseTram());

        return alarmFormRepository.save(newAlarmForm);
    }

    public AlarmTimeFormBean retrieveAlarmFormAsBean(Long id) {
        Optional<AlarmForm> alarmFormOptional = alarmFormRepository.findById(id);
        if (!alarmFormOptional.isPresent()) {
            return null;
        }

        AlarmForm alarmForm = alarmFormOptional.get();
        AlarmTimeFormBean alarmTimeFormBean = new AlarmTimeFormBean();

        alarmTimeFormBean.setArrivalTime(alarmForm.getArrivalTime());

        alarmTimeFormBean.setTimeToGetReady(alarmForm.getTimeToGetReady());

        alarmTimeFormBean.setAddressDestination(alarmForm.getAddressDestination());
        alarmTimeFormBean.setAddressResidence(alarmForm.getAddressResidence());

        alarmTimeFormBean.setUseBus(alarmForm.getUseBus());
        alarmTimeFormBean.setUseSAndUBahn(alarmForm.getUseSAndUBahn());
        alarmTimeFormBean.setUseTram(alarmForm.getUseTram());

        return alarmTimeFormBean;
    }

    public Map<Integer, String> retrieveAllIdNamePairs() {
        Map<Integer, String> idNamePairs = new HashMap<>();

        for (AlarmForm alarmForm : alarmFormRepository.findAll()) {
            idNamePairs.put(alarmForm.getId(), alarmForm.getName());
        }

        return idNamePairs;
    }
}
