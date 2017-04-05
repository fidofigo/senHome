package com.senhome.shell.common.lang.string;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class StringSplitUtil {

    public static <E> List<E> splitToList(String str, String separatorChars, Class<E> clazz) {

        List<E> list = Lists.newArrayList();

        String[] array = StringUtils.split(str, separatorChars);

        if (ArrayUtils.isEmpty(array)) {
            return list;
        }

        for (String s : array) {

            if (StringUtils.isEmpty(s)) {
                continue;
            }

            @SuppressWarnings("unchecked")
            E e = (E) ConvertUtils.convert(s, clazz);

            if (e != null) {
                list.add(e);
            }

        }

        return list;
    }

}
