package io.github.kavahub.learnjava;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;


/**
 * 学生集合适配器
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class StudentMapAdapter extends XmlAdapter<StudentMap, Map<Integer, Student>> {
    public StudentMap marshal(Map<Integer, Student> boundMap) throws Exception {
        StudentMap valueMap = new StudentMap();
        for (Map.Entry<Integer, Student> boundEntry : boundMap.entrySet()) {
            StudentMap.StudentEntry valueEntry = new StudentMap.StudentEntry();
            valueEntry.setStudent(boundEntry.getValue());
            valueEntry.setId(boundEntry.getKey());
            valueMap.getEntries().add(valueEntry);
        }
        return valueMap;
    }

    public Map<Integer, Student> unmarshal(StudentMap valueMap) throws Exception {
        Map<Integer, Student> boundMap = new LinkedHashMap<Integer, Student>();
        for (StudentMap.StudentEntry studentEntry : valueMap.getEntries()) {
            boundMap.put(studentEntry.getId(), studentEntry.getStudent());
        }
        return boundMap;
    }
    
}
