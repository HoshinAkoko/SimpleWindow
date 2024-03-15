package moe.moti.simplewindow.util;

import java.util.*;

public class DataUtil {
    @SuppressWarnings("UnusedReturnValue")
    public static List<Map<String, Object>> distinct(List<Map<String, Object>> list, String... keys) {
        Set<String> distinctSet = new HashSet<>();
        List<Map<String, Object>> deleteList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            StringBuilder sb = new StringBuilder();
            for (String key : keys) {
                sb.append(map.get(key));
                sb.append("_");
            }
            if (distinctSet.contains(sb.toString())) {
                deleteList.add(map);
            } else {
                distinctSet.add(sb.toString());
            }
        }
        list.removeAll(deleteList);
        return list;
    }
}
