package utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static ArrayList reverseList(ArrayList list) {

        if(list.size() > 1) {
            Object value = list.remove(0);
            reverse(list);
            list.add(value);
        }
        return list;
    }

    private static ArrayList<Object> reverse(ArrayList<Object> list) {
        for(int i = 0, j = list.size() - 1; i < j; i++) {
            list.add(i, list.remove(j));
        }
        return list;
    }
}
