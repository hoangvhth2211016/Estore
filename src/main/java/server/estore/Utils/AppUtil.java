package server.estore.Utils;


import server.estore.Exception.ConflictException;

import java.util.Arrays;

public class AppUtil {

    public static <T> void verifyObj(T firstObj, T secondObj){
        if(!firstObj.equals(secondObj)){
            throw new ConflictException("Request and target objects not match");
        }
    }

    public static String[] prepareSearchList(String search) {
        return Arrays.stream(search.split("\\s+")).map(str -> "%"+str+"%").toArray(String[]::new);
    }

}
