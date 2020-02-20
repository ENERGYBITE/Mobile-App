package com.ecar.energybite.util;

import android.text.Html;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtility {

    // Do not include ^ in the DELIM set.. Used in ProductSet

    public static final String EMPTY_STRING      = "";
    public static final String DELIM0            = "#";
    public static final String DELIM0_REGEX      = "[#]";
    private static final String[] DELIM             = { "|", "$", "@", ":", "~", "`", "!" };
    private static final String[] DELIM_REGEX       = { "[|]", "[$]", "[@]", "[:]", "[~]", "[`]", "[!]" };
    private static final String[] DELIM_CLEAN       = { "[|]+", "[|$]+", "[|$@]+", "[|$@:]+", "[|$@:~]+", "[|$@:~`]+",
            "[|$@:~`!]+"                           };
    private static final String[] DELIM_ESCAPE      = { " /#/% ", " /!/~ ", " /%/# ", " /~/% ", " /!/% ", " /%/| ",
            " /`/! "                               };
    private static final String SPL_CHARS         = "!@#$%&*";

    private static final String[] DELIM1            = { "[|]", "[$]" };

    public static final String EMAIL_REGEX       = "^[A-Za-z0-9\\.\\_\\-]+@[A-Za-z0-9\\.\\-]+\\.[a-zA-Z]{2,10}$";
    public static final String DATE_YYMMDD_REGEX = "";
    public static final String NAME_REGEX        = "^[a-zA-Z\\s]{1,}$";
    public static final String FULL_NAME_REGEX   = "^[\\w\\s]{1,}$";
    public static final String NUMERIC_REGEX     = "[0-9]{1,}";

    public static int parseIntAndNullEmptyIsMinus1(String str) {
        if (str == null || str.length() < 1) {
            return -1;
        } else {
            return Integer.parseInt(str);
        }
    }

    public static int parseIntAndNullEmptyIsZero(String str) {
        if (str == null || str.length() < 1) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }

    public static int parseIntWithDefault(String str, int defaultVal) {
        if (str == null || str.length() < 1) {
            return defaultVal;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (Exception e) {
                return defaultVal;
            }

        }
    }

    public static String getNewLineString() {
        return System.getProperty("line.separator");
    }

    public static boolean isValidPassword(String str, boolean isSplCharReq) {
        boolean isValid = false;
        str = trimAndEmptyIsNull(str);
        if (str != null) {
            if (isSplCharReq) {
                isValid = str.matches("^.*(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W\\_])(?=\\S+$).*$");

            } else {
                isValid = str.matches("^\\S{8,}$");
            }
        }
        return isValid;
    }

    public static boolean isValidCryptorPassword(String str) {
        return isValidCryptorPassword(str, false);
    }

    public static boolean isValidCryptorPassword(String str, boolean isSuffix) {
        boolean isValid = false;
        str = trimAndEmptyIsNull(str);
        if (str != null) {
            int minLength = 10;
            if (isSuffix) {
                minLength = 20;
            }
            isValid = str.matches("^\\S{" + minLength + ",}$");
        }
        return isValid;
    }

    public static boolean isValidEmail(String str) {
        return (str == null) ? false : str.matches(EMAIL_REGEX);
    }

    public static boolean isValidName(String str) {
        return (str == null) ? false : str.matches(NAME_REGEX);
    }

    public static boolean isValidFullName(String str) {
        return (str == null) ? false : str.matches(FULL_NAME_REGEX);
    }

    public static boolean isNumeric(String str) {
        boolean isValid = false;
        str = trimAndEmptyIsNull(str);
        if (str == null) {
            return isValid;
        }

        isValid = str.matches(NUMERIC_REGEX);
        return isValid;
    }

    public static String getDelim(int level) {
        return DELIM[level];
    }

    public static String getEscapeDelim(int level) {
        return DELIM_ESCAPE[level];
    }

    public static String getDelimRegex(int level) {
        return DELIM_REGEX[level];
    }

    public static String getDelimCleanerRegex(int level) {
        return DELIM_CLEAN[level];
    }

    public static String cleanStringOfDelims(String str, int level) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(DELIM_CLEAN[level], EMPTY_STRING);
    }

    public static String escapeStringOfDelims(String str, int level) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(DELIM_CLEAN[level], DELIM_ESCAPE[level]);
    }

    public static String trimCleanStringOfDelimsAndNullisEmpty(String str, int level) {
        if (str == null) {
            return EMPTY_STRING;
        }
        return str.replaceAll(DELIM_CLEAN[level], EMPTY_STRING).trim();
    }

    public static String trimCleanToAlphaNumbericNullisEmpty(String str) {
        if (str == null) {
            return EMPTY_STRING;
        }
        return str.replaceAll("[^0-9a-zA-Z]", EMPTY_STRING).trim();
    }

    public static String trimCleanToAlphaNullisEmpty(String str) {
        if (str == null) {
            return EMPTY_STRING;
        }
        return str.replaceAll("[^a-zA-Z ]", EMPTY_STRING).trim();
    }

    public static String trimCleanToHDFCUdfAllowedNullisEmpty(String str) {
        if (str == null) {
            return EMPTY_STRING;
        }
        return str.replaceAll("[^0-9a-zA-Z-_@.]", EMPTY_STRING).trim();
    }

    public static String removeNonDecimal(String str) {
        return str.replaceAll("Rs\\.", EMPTY_STRING).replaceAll("[^0-9.]", EMPTY_STRING);
    }

    public static String pad(String str, int len) {
        if (str == null) {
            str = EMPTY_STRING;
        }
        return pad(str, "0", len, true);
    }

    public static String pad(String str, String padWith, int len, boolean padLeftNotRight) {
        while (str.length() < len) {
            if (padLeftNotRight) {
                str = padWith + str;
            } else {
                str = str + padWith;
            }
        }
        return str;
    }

    public static String substring(String text, String prefixRegex, String suffixRegex, String includes) {
        Pattern prefixPattern = Pattern.compile(prefixRegex);
        Pattern suffixPattern = Pattern.compile(suffixRegex);
        Matcher prefixMatcher = prefixPattern.matcher(text);
        while (prefixMatcher.find()) {
            Matcher suffixMatcher = suffixPattern.matcher(text);
            while (suffixMatcher.find()) {
                if (suffixMatcher.start() > prefixMatcher.end()) {
                    String temp = text.substring(prefixMatcher.end(), suffixMatcher.start());
                    if (includes != null) {
                        if (temp.contains(includes))
                            return temp;
                        else
                            return null;
                    } else {
                        return temp;
                    }
                }
            }
        }
        return null;
    }

    public static boolean isNonEmpty(String str) {
        return str != null && str.trim ().length () > 0;
    }

    public static String trimAndEmptyIsNull(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        if (str.length() < 1) {
            return null;
        }
        return str;
    }

    public static String trimAndEmptyIsDefault(String str, String defaultVal) {
        if (str == null) {
            return defaultVal;
        }
        str = str.trim();
        if (str.length() < 1) {
            return defaultVal;
        }
        return str;
    }

    public static String trimAndNullIsEmpty(String str) {
        if (str == null) {
            return EMPTY_STRING;
        }
        str = str.trim();
        return str;
    }

    public static String trimAndNullIsEmpty(StringBuilder str) {
        if (str == null) {
            return EMPTY_STRING;
        }
        return trimAndNullIsEmpty(new String(str));
    }

    public static String trimAndNullIsEmpty(Integer a) {
        if (a == null) {
            return EMPTY_STRING;
        }

        return a.toString();
    }

    public static String trimAndNullIsEmpty(Double a) {
        if (a == null) {
            return EMPTY_STRING;
        }

        return a.toString();
    }

    public static boolean equalsWithTrimAndBothNullCheck(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 != null) {
            str1 = str1.trim();
        } else {
            return false;
        }
        if (str2 != null) {
            str2 = str2.trim();
        } else {
            return false;
        }
        return str1.equals(str2);
    }

    public static boolean equalsIgnoreCaseWithTrimAndBothNullCheck(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 != null) {
            str1 = str1.trim();
        } else {
            return false;
        }
        if (str2 != null) {
            str2 = str2.trim();
        } else {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }

    public static char getRandomAplanumeric() {
        // Generate random number 0-35 (both inclusive)
        int random = (int) Math.ceil(Math.random() * 35);
        if (random < 10) {
            return (char) ('0' + random);
        } else {
            int randomCase = (int) Math.ceil(Math.random() * 2);
            if (randomCase == 1) {
                return (char) ('a' + (random - 10));
            } else {
                return (char) ('A' + (random - 10));
            }
        }
    }

    public static String getRandomAplanumerics(int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int random = (int) Math.ceil(Math.random() * 35);
            if (random < 10) {
                result.append((char) ('0' + random));
            } else {
                int randomCase = (int) Math.ceil(Math.random() * 2);
                if (randomCase == 1) {
                    result.append((char) ('a' + (random - 10)));
                } else {
                    result.append((char) ('A' + (random - 10)));
                }
            }
        }
        return result.toString();
    }

    public static char getRandomSplChar() {
        int random = (int) Math.ceil(Math.random() * (SPL_CHARS.length() - 1));
        return SPL_CHARS.charAt(random);
    }

    public static int getRandomNumber() {
        return (int) Math.floor(Math.random() * 10);
    }

    public static String getRandomNumbers(int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append((char) ('0' + getRandomNumber()));
        }
        return result.toString();
    }

    public static char getRandomUpperCaseChar() {
        int random = (int) Math.ceil(Math.random() * 25);
        return (char) ('A' + random);
    }

    public static char getRandomLowerCaseChar() {
        int random = (int) Math.ceil(Math.random() * 25);
        return (char) ('a' + random);
    }

    public static char getRandomNumberChar() {
        int random = (int) Math.ceil(Math.random() * 9);
        return (char) ('0' + random);
    }

    public static String join(Map<String, Set<String>> map, String delim) {
        if (map == null || map.size() < 1) {
            return EMPTY_STRING;
        }
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            b.append(join(entry.getValue(), delim));
        }
        return b.toString();
    }

    public static String join(String[] str, String delim) {
        if (str == null || str.length < 1) {
            return EMPTY_STRING;
        }
        StringBuilder b = new StringBuilder();
        boolean isFirst = true;
        for (int i = 0; i < str.length; i++) {
            if(!isFirst) {
                b.append(delim);
            }
            b.append(trimAndNullIsEmpty(str[i]));
            isFirst = false;
        }
        return b.toString();
    }

    public static String join(Object[] str, String delim) {
        if (str == null || str.length < 1) {
            return EMPTY_STRING;
        }
        StringBuilder b = new StringBuilder();
        b.append(str[0]);
        for (int i = 1; i < str.length; i++) {
            b.append(delim).append(str[i]);
        }
        return b.toString();
    }

    public static String join(int[] str, String delim) {
        if (str == null || str.length < 1) {
            return EMPTY_STRING;
        }
        StringBuilder b = new StringBuilder();
        b.append(str[0]);
        for (int i = 1; i < str.length; i++) {
            b.append(delim).append(str[i]);
        }
        return b.toString();
    }

    public static String join(List<Integer> list, String delim) {
        if (list == null || list.size() < 1) {
            return EMPTY_STRING;
        }
        StringBuilder b = new StringBuilder();
        b.append(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            b.append(delim).append(list.get(i));
        }
        return b.toString();
    }

    public static String joinWithDelimBothEnd(List<Integer> list, String delim) {
        if (list == null || list.size() < 1) {
            return null;
        }
        StringBuilder b = new StringBuilder();
        b.append(delim);
        b.append(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            b.append(delim).append(list.get(i));
        }
        b.append(delim);
        return b.toString();
    }

    public static String join(Collection< ? extends Object> list, String delim1, String delim2) {
        if (list == null || list.size() < 1) {
            return EMPTY_STRING;
        }
        StringBuilder b = new StringBuilder();
        Iterator< ? extends Object> itr = list.iterator();
        while (itr.hasNext()) {
            if (b.length() > 0) {
                b.append(delim1);
            }
            b.append(delim2).append(itr.next().toString()).append(delim2);
        }
        return b.toString();
    }

    public static String join(List<int[]> list, String delim1, String delim2) {
        if (list == null || list.size() < 1) {
            return EMPTY_STRING;
        }
        StringBuilder b = new StringBuilder();
        b.append (StringUtility.join (list.get (0), delim2));
        for (int i = 1; i < list.size(); i++) {
            b.append(delim1).append (StringUtility.join (list.get (i), delim2));
        }
        return b.toString();
    }

    public static String joinNullForEmpty(String[] str, String delim) {
        if (str == null || str.length < 1) {
            return null;
        }
        StringBuilder b = new StringBuilder();
        b.append(str[0]);
        for (int i = 1; i < str.length; i++) {
            b.append(delim).append(str[i]);
        }
        return b.toString();
    }

    public static String[] splitNullForEmpty(String str, String regex) {
        if (str == null || str.length() < 1) {
            return null;
        }
        return str.split(regex, -1);
    }

    public static String trimAndRemoveStartingZeros(String str) {
        if (str == null || str.length() < 1) {
            return null;
        }
        str = str.trim().replaceAll("^[0]+", EMPTY_STRING);
        if (str.length() < 1) {
            return "0";
        } else {
            return str;
        }
    }

    public static String removeHtmltags(String string) {
        if (string == null) {
            return null;
        }
        string = string.replaceAll("\\<.*?\\>", EMPTY_STRING);
        return (string);
    }

    public static String join(Collection< ? extends Object> col, String delim) {

        if (col == null) {
            return EMPTY_STRING;
        }
        StringBuilder b = new StringBuilder();
        for (Object o : col) {
            if (b.length() > 0) {
                b.append(delim);
            }
            b.append(o != null ? o.toString() : EMPTY_STRING);
        }
        return b.toString();
    }

    public static String joinWithComma(Collection< ? extends Object> col, String suffix) {
        if (col == null) {
            return EMPTY_STRING;
        }
        StringBuilder b = new StringBuilder();
        for (Object o : col) {
            if (b.length() > 0) {
                b.append(", ");
            }
            b.append(o != null ? o.toString() : EMPTY_STRING);
        }
        if(isNonEmpty(suffix) && b.length() > 0) {
            b.append(" ").append(suffix);
        }
        return b.toString();
    }

    public static String joinEmptyForNull(String[] col, String delim) {
        if (col == null || col.length < 1) {
            return EMPTY_STRING;
        }
        StringBuilder b = new StringBuilder();
        for (String s : col) {
            if (b.length() > 0) {
                b.append(delim);
            }
            b.append(s == null ? EMPTY_STRING : s);
        }
        return b.toString();
    }

    public static String joinEmptyForNullList(Collection<String> col, int delimLevel) {
        if (col == null || col.size() < 1) {
            return EMPTY_STRING;
        }
        String delim = StringUtility.getDelim (delimLevel);
        StringBuilder b = new StringBuilder();
        for (String s : col) {
            if (b.length() > 0) {
                b.append(delim);
            }
            b.append(s == null ? EMPTY_STRING : s);
        }
        return b.toString();
    }

    public static String joinEmptyForNullListWithCleanDelim(Collection<String> col, int delimLevel) {
        if (col == null || col.size() < 1) {
            return EMPTY_STRING;
        }
        String delim = StringUtility.getDelim (delimLevel);
        String delimClean = StringUtility.getDelimCleanerRegex (delimLevel);
        StringBuilder b = new StringBuilder();
        for (String s : col) {
            if (b.length() > 0) {
                b.append(delim);
            }
            b.append(s == null ? EMPTY_STRING : s.replaceAll(delimClean, EMPTY_STRING));
        }
        return b.toString();
    }

    public static String joinEmptyForNullListWithEscapeDelim(Collection<String> col, int delimLevel) {
        if (col == null || col.size() < 1) {
            return EMPTY_STRING;
        }
        String delim = StringUtility.getDelim (delimLevel);
        StringBuilder b = new StringBuilder();
        for (String s : col) {
            if (b.length() > 0) {
                b.append(delim);
            }
            b.append (s == null ? EMPTY_STRING : s.replaceAll (delim, StringUtility.getEscapeDelim (delimLevel)));
        }
        return b.toString();
    }

    public static String joinEmptyForNull(Collection<String> col, String delim) {
        StringBuilder b = new StringBuilder();
        for (String s : col) {
            if (b.length() > 0) {
                b.append(delim);
            }
            b.append(s == null ? EMPTY_STRING : s);
        }
        return b.toString();
    }

    public static int compare(String str1, String str2) {
        if (str1 == null || str2 == null) {
            if (str1 == null && str2 == null) {
                return 0;
            }
            if (str1 == null) {
                return 1;
            }
            if (str2 == null) {
                return -1;
            }
        }
        return str1.compareTo(str2);
    }

    public static class StringArrComparator implements Comparator<String[]> {
        private int m_idx = -1;

        public StringArrComparator(int srtIdx) {
            m_idx = srtIdx;
        }

        @Override
        public int compare(String[] s1, String[] s2) {
            return s1[m_idx].compareTo(s2[m_idx]);
        }
    }

    public static List<String> splitToStringList(String serialized, int delimLevel) {
        return splitToStringList(serialized, delimLevel, false);
    }

    public static List<String> splitToStringList(String serialized, int delimLevel, boolean excludeEmptyString) {
        if (serialized == null || serialized.length() < 1) {
            return new ArrayList<String>();
        }
        List<String> res = new ArrayList<String>();
        String regex = getDelimRegex(delimLevel);
        String[] keys = serialized.split(regex, -1);
        for (String key : keys) {
            if (!excludeEmptyString || (excludeEmptyString && trimAndEmptyIsNull(key) != null))
                res.add(key);
        }
        return res;
    }

    public static List<String> splitToStringListWithEscapeDelim(String serialized, int delimLevel) {
        if (serialized == null || serialized.length() < 1) {
            return new ArrayList<String>();
        }
        List<String> res = new ArrayList<String>();
        String regex = getDelimRegex(delimLevel);
        String delim = DELIM[delimLevel];
        String[] keys = serialized.split(DELIM_ESCAPE[delimLevel], -1);
        for (String key : keys) {
            int j = 0;
            String[] key1 = key.split(regex, -1);
            for (String key2 : key1) {
                if (j == 0 && res.size() > 0) {
                    String key3 = res.remove(res.size() - 1);
                    res.add(key3 + delim + key2);
                } else {
                    res.add(key2);
                }
                j++;
            }
        }
        return res;
    }

    public static Set<String> splitToStringSet(String serialized, int delimLevel) {
        if (serialized == null || serialized.length() < 1) {
            return new HashSet<String>();
        }
        Set<String> res = new HashSet<String>();
        String regex = getDelimRegex(delimLevel);
        String[] keys = serialized.split(regex, -1);
        for (String key : keys) {
            res.add(key);
        }
        return res;
    }

    public static List<Integer> convertSetIntoList(Set<Integer> setInt) {
        List<Integer> listInt = new ArrayList<Integer>();
        if (setInt != null) {
            for (Integer num : setInt) {
                listInt.add(num);
            }
        }
        return listInt;
    }

    public static List<Integer> splitToIntList(String serialized, int delimLevel) throws Exception {
        if (serialized == null || serialized.length() < 1) {
            return new ArrayList<Integer>();
        }
        List<Integer> res = new ArrayList<Integer>();
        String regex = getDelimRegex(delimLevel);
        String[] keys = serialized.split(regex, -1);
        for (String key : keys) {
            res.add(Integer.valueOf(key));
        }

        return res;
    }

    public static List<Integer> splitToIntListForDelimBothEnd(String serialized, int delimLevel) throws Exception {
        if (serialized == null || serialized.length() < 1) {
            return null;
        }
        List<Integer> res = new ArrayList<Integer>();
        String regex = getDelimRegex(delimLevel);
        String[] keys = serialized.split(regex, -1);
        for (int i = 1; i < keys.length - 1; i++) {
            res.add(Integer.valueOf(keys[i]));
        }
        return res;
    }

    public static ArrayList<Double> splitToDoubleList(String serialized, int delimLevel) throws Exception {
        if (serialized == null || serialized.length() < 1) {
            return new ArrayList<Double>();
        }
        List<Double> res = new ArrayList<Double>();
        String regex = getDelimRegex(delimLevel);
        String[] keys = serialized.split(regex, -1);
        for (String key : keys) {
            res.add(Double.valueOf(key));
        }
        return (ArrayList<Double>) res;
    }

    public static String trimWithNullAndEmptyAsUnset(String str) {
        str = StringUtility.trimAndEmptyIsNull (str);
        if (str == null || str.length() < 0) {
            return "Unset";
        }
        return str;
    }

    public static String trimToSize(String str, int size) {
        return trimToSize(str, size, false);
    }

    public static String trimToSize(String str, int size, boolean isTrimFromLeft) {
        if (str == null || str.length() <= size) {
            return str;
        }
        if (!isTrimFromLeft) {
            return str.substring(0, size);
        }
        return str.substring(str.length() - size);
    }

    public static String trimToSizeAndNullIsEmpty(String str, int size) {
        if (str == null) {
            return EMPTY_STRING;
        } else if (size == -1 || str.length() <= size) {
            return str;
        }
        return str.substring(0, size);
    }

    public static String trimToSizeAndNullIsDefault(String str, int size, String def) {
        if (str == null) {
            return def;
        } else if (str.length() <= size) {
            return str;
        }
        return str.substring(0, size);
    }

    public static String trimNoSpecialSizeAndNullIsDefault(String str, int size, String def) {
        if (str == null) {
            return def;
        }
        str = str.replaceAll("[^\\w\\d\\s]", EMPTY_STRING).replaceAll("[\\s]+", " ").trim();
        if (str.length() < 1) {
            return def;
        }
        if (size == -1 || str.length() <= size) {
            return str;
        }
        return str.substring(0, size);
    }

    public static String convertToJavascriptArray(List<Integer> intList) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[ ");
        if (intList != null && intList.size() > 0) {
            buffer.append(intList.get(0));
            for (int i = 1; i < intList.size(); i++) {
                buffer.append(", ").append(intList.get(i));
            }
        }
        buffer.append(" ]");
        return buffer.toString();
    }

    public static String[] toEmailArr(String emails) {
        if (emails == null) {
            return null;
        }
        String[] toEmailArr = emails.split("[,;]");
        List<String> emailList = new ArrayList<String>(toEmailArr.length);
        for (int i = 0; i < toEmailArr.length; i++) {
            toEmailArr[i] = StringUtility.trimAndEmptyIsNull (toEmailArr[i]);
            if (toEmailArr[i] != null) {
                emailList.add(toEmailArr[i]);
            }
        }
        if (emailList.size() < 1) {
            return null;
        }
        return emailList.toArray(new String[0]);
    }

    public static String cleanForDatabase(String str) {
        if (str == null) {
            return str;
        }
        return str.replaceAll("[\\n\\r\\t\"']+", EMPTY_STRING);
    }

    public static String cleanForJson(String str) {
        if (str == null) {
            return EMPTY_STRING;
        }
        return str.replaceAll("[\\n\\r\\t\"\\']+", EMPTY_STRING);
    }

    public static String removeLeadingZeroes(String str) {
        if (str == null) {
            return null;
        }
        String finalString = EMPTY_STRING;
        try {
            Integer asInt = Integer.parseInt(str);
            finalString = asInt.toString();
        } catch (Exception e) {

        }
        return finalString;
    }

    public static String removeLeadingCharacter(String str, char c) {
        if (str == null) {
            return null;
        }
        String finalString = EMPTY_STRING;
        try {

            int index = 0;
            for (index = 0; index < str.length(); index++) {
                if (str.charAt(index) != c) {
                    break;
                }
            }
            finalString = str.substring(index);

        } catch (Exception e) {

        }
        return finalString;
    }

    public static String removeAllMatchingFromLongString(String orig, String[] keywords) {
        if (orig == null) {
            return null;
        }

        int i = -1;
        StringBuilder b = new StringBuilder(orig);
        for (String keyword : keywords) {
            keyword = StringUtility.trimAndEmptyIsNull (keyword);
            if (keyword != null) {
                while ((i = b.indexOf(keyword)) != -1) {
                    b.delete(i, i + keyword.length());
                }
            }
        }
        return b.toString();
    }

    public static String replaceAllMatchingFromLongString(String orig, String[] keywords) {
        if (orig == null) {
            return null;
        }
        if (keywords == null || keywords.length < 1) {
            return orig;
        }

        int i = -1;
        StringBuilder b = new StringBuilder(orig);
        for (String keyword : keywords) {
            keyword = StringUtility.trimAndEmptyIsNull (keyword);
            if (keyword != null) {
                while ((i = b.indexOf(keyword)) != -1) {
                    b.replace(i, i + keyword.length(), "XXX");
                }
            }
        }
        return b.toString();
    }

    public static String trimAndReplaceDelimeterCharToSpace(String str) {
        if (str == null) {
            return EMPTY_STRING;
        }
        String replacedString = new String(str);
        for (String delim : DELIM1) {
            replacedString = replacedString.replaceAll(delim, " ");
        }
        return trimAndNullIsEmpty(replacedString);
    }

    public static String[] replaceBreakIntoArray(String HTMLString) {
        String[] replacedString = HTMLString.replaceAll("\\r|\\n", " ").replaceAll("\\<br.*?/>", "EndLine")
                .replaceAll("\\<p.*?>", "EndLine").replaceAll("\\<.*?>", EMPTY_STRING).replaceAll("&nbsp;", "&#160;")
                .split("EndLine");
        return replacedString;
    }

    public static String replaceHtmlToTextBreakEveryTagToNewLine(String HTMLString) {
        String text_new_line = "\r\n";
        String replacedString = new String(HTMLString);

        replacedString = replacedString.replaceAll("[\\s\\t]+", " ");
        replacedString = replacedString.replaceAll("\\<(/).*?>", text_new_line);
        replacedString = replacedString.replaceAll("\\<.*li.*>", "- ");
        replacedString = replacedString.replaceAll("\\<.*?>", EMPTY_STRING);
        return trimAndNullIsEmpty(replacedString);
    }

    public static List<String> sortStringsList(List<String> strings) {
        if (strings != null && strings.size() > 1) {
            for (int counter = 0; counter < strings.size() - 1; counter++) { // Loop
                for (int index = 0; index < strings.size() - 1 - counter; index++) { // Once
                    if (strings.get(index).compareTo(strings.get(index + 1)) > 1) { // Test
                        String temp = strings.get(index);
                        strings.set(index, (strings.get(index + 1)));
                        strings.set(index + 1, temp);
                    }
                }
            }
        }
        return strings;

    }

    public static String getEscapedDoubleQuoteString(final String originalString) {
        if (originalString == null) {
            return null;
        }
        StringBuilder escapedString = new StringBuilder(originalString);
        int i = escapedString.indexOf("\"");
        while (i != -1) {
            escapedString.insert(i, "\\");
            i = escapedString.indexOf("\"", i + 2);
        }
        return escapedString.toString();
    }

    public static String unescapeStringOfDelims(String str, int level) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(DELIM_ESCAPE[level], DELIM[level]);
    }

    private StringUtility() {
    }

    public static String getMaskedString(String origStr) {
        if (origStr == null) {
            return "";
        }
        return getMaskedString(origStr, origStr.length(), 'X', true);
    }

    public static String getMaskedString(String origStr, int length, char c, boolean fromLeft) {
        if (origStr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (origStr.length() >= length) {
            int i = 0;
            if (fromLeft) {
                for (; i < length; i++) {
                    sb.append(c);
                }
                for (; i < origStr.length(); i++) {
                    sb.append(origStr.charAt(i));
                }
            } else {
                for (; i < origStr.length() - length; i++) {
                    sb.append(origStr.charAt(i));
                }
                for (; i < origStr.length(); i++) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static String padCCExceptLastFour(String cardNumber, char c) {
        StringBuilder sb = new StringBuilder();
        if (cardNumber != null && cardNumber.length() > 4) {
            int len = cardNumber.length();
            for (int i = 0; i < (len - 4); ++i) {
                sb.append(c);
            }
            sb.append(cardNumber.substring(len - 4));
        }
        return sb.toString();
    }

    public static String breakWordIfLongerThanLimit(String origString, int maxLimit) {
        if (origString == null) {
            return origString;
        }

        String[] words = origString.split("\\s");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int insertLoc = maxLimit;
            int length = word.length();
            while (length > maxLimit) {
                word = new StringBuilder(word).insert(insertLoc, " ").toString();
                length -= (maxLimit);
                insertLoc += (maxLimit + 1);
                System.out.print("\nword = " + word + "length = " + length);
            }

            words[i] = word;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0 && words[i].charAt(words[i].length() - 1) != ' ') {
                sb.append(" ");
            }
            sb.append(words[i]);
        }
        return sb.toString();
    }

    public static boolean isNull(String str) {
        return str == null || str.equals ("null");
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

    }

    public static String trimNoSpecialSizeAndNullIsDefaultStripAmp(String input, int length, String defVal) {
        String val = trimNoSpecialSizeAndNullIsDefault(input, length, defVal);
        if (val != null) {
            val = val.replaceAll("&", "");
        }
        return val;
    }

    public static boolean isSetProperty(String keyDirectory) {
        if (StringUtility.trimAndEmptyIsNull (keyDirectory) == null) {
            return false;
        }
        if (keyDirectory.startsWith("@")) {
            return false;
        }
        return keyDirectory.length() > 0;
    }

    public static String convertBytesToString(byte[] bytesArray) throws Exception {
        if (bytesArray == null) {
            return null;
        }
        return ((bytesArray.length == 0) ? "" : (new String(bytesArray, "UTF-8")));
    }

    public static String getBytesDecimalValuesAsString(byte[] bytesArray) throws Exception {
        if (bytesArray == null) {
            return null;
        }
        String decimalValues = new String("");
        for (int index = 0; index < bytesArray.length; index++) {
            decimalValues += " " + Integer.toString(bytesArray[index]);
        }
        return decimalValues;
    }

    public static String getWildCardOperatorForQuery(int delimCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        for (int i = 0; i < delimCount; i++) {
            sb.append (StringUtility.getDelim (0)).append ("%");
        }
        return sb.toString();
    }

    public static StringBuilder mapToString(Map<String, String> map) {
        if (map == null || map.entrySet().size() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            String value = map.get(key);
            try {
                stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
                stringBuilder.append("=");
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }
        return stringBuilder;
    }

    public static Map<String, String> stringToMap(String input) {
        if (input == null || input.length() == 0) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        String[] nameValuePairs = input.split("&");
        for (String nameValuePair : nameValuePairs) {
            String[] nameValue = nameValuePair.split("=");
            try {
                map.put(URLDecoder.decode(nameValue[0], "UTF-8"),
                        nameValue.length > 1 ? URLDecoder.decode(nameValue[1], "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }
        return map;
    }

    public static List<Integer> splitToIntList(String serialized, String regex) throws Exception {
        if (serialized == null || serialized.length() < 1) {
            return new ArrayList<Integer>();
        }
        List<Integer> res = new ArrayList<Integer>();
        String[] keys = serialized.split(regex, -1);
        for (String key : keys) {
            if(key != null && key.matches("^[0-9]+")) {
                res.add(Integer.valueOf(key));
            }
        }

        return res;
    }

    public static int[] splitToIntArray(String serialized, String regex) throws Exception {
        if (serialized == null || serialized.length() < 1) {
            return new int[0];
        }
        List<Integer> res = new ArrayList<Integer>();
        String[] keys = serialized.split(regex, -1);
        for (String key : keys) {
            if(key != null && key.matches("^[0-9]+") && !res.contains(Integer.valueOf(key))) {
                res.add(Integer.valueOf(key));
            }
        }
        int[] arr = new int[res.size()];
        for(int index=0; index < res.size(); index ++) {
            arr[index] = res.get(index).intValue();
        }

        return arr;
    }

    public static <T> List<T> splitToClassList(String serialized, String delim, Class<T> clazz) {
        List<T> res = new ArrayList<T>();
        try {
            if (serialized != null && serialized.length() > 1 && clazz.getConstructor(String.class) != null) {
                Constructor<T> cons = clazz.getConstructor(String.class);
                String[] keys = serialized.split(delim);
                for (String key : keys) {
                    if (key != null && !"".equals(key.trim()))
                        res.add(cons.newInstance(key));
                }
            }

        } catch (Exception e) {
            return res;
        }
        return res;
    }

    public static List<String> splitToStringList(String serialized, String regex, boolean excludeEmptyString) {
        if (serialized == null || serialized.length() < 1) {
            return new ArrayList<String>();
        }
        List<String> res = new ArrayList<String>();
        String[] keys = serialized.split(regex, -1);
        for (String key : keys) {
            if (!excludeEmptyString || (excludeEmptyString && trimAndEmptyIsNull(key) != null))
                res.add(key);
        }
        return res;
    }

    public static String[] getStringArrayFromIntRange(int i, int j) {
        String[] result = null;
        if (i < j) {
            result = new String[j - i + 1];
            int index = 0;
            for (; i <= j; i++, index++) {
                result[index] = "" + i;
            }
        }
        return result;
    }

    public static String[] getStringArrayFromIntList(List<Integer> list) {
        String[] result = null;
        if (list != null && !list.isEmpty()) {
            result = new String[list.size()];
            int index = 0;
            for (Integer val : list) {
                result[index] = "" + val.intValue();
                index++;
            }
        }
        return result;
    }

    public static int getIndexofItemInStringAscArr(String[] arr, String value) {
        if (trimAndEmptyIsNull(value) == null) {
            return 0;
        }
        for (int index = 0; index < arr.length; index++) {
            if (value.equalsIgnoreCase(arr[index])) {
                return index;
            }
        }
        return 0;
    }

    public static int getIndexofItemInStringAscArrWithIntValue(String[] arr, String value) {
        int intValue = 0;
        if (trimAndEmptyIsNull(value) == null) {
            return 0;
        }
        try {
            intValue = Integer.valueOf(value);
            if (arr.length > intValue) {
                int arrIndexVal = Integer.valueOf(arr[intValue]);
                if (arrIndexVal == intValue) {
                    return intValue;
                } else if (arrIndexVal < intValue) {
                    for (int index = arrIndexVal + 1; index < arr.length; index++) {
                        if (value.equalsIgnoreCase(arr[index])) {
                            return index;
                        }
                    }
                } else if (arrIndexVal > intValue) {
                    for (int index = arrIndexVal - 1; index >= 0; index--) {
                        if (value.equalsIgnoreCase(arr[index])) {
                            return index;
                        }
                    }
                }
            }

        } catch (NumberFormatException ne) {

        }
        return getIndexofItemInStringAscArr(arr, value);
    }

    public static boolean isValidFlightNumber(String flightNum) {
        if (trimAndEmptyIsNull(flightNum) != null) {
            return flightNum.matches("^[A-Za-z0-9]{2}[ -]{0,}[0-9]{3,}$");
        }
        return false;
    }

    public static boolean isValidTrainNumber(String trainNum) {
        if (trimAndEmptyIsNull(trainNum) != null) {
            return trainNum.matches("^[0-9]{1,}$");
        }
        return false;
    }

//    public static String getMoneyDisplayString(Money money) {
//        if (money != null) {
//            return money.format();
//        } else {
//            return "0";
//        }
//    }

//    public static String joinFareRules(List<String> rules) {
//        if(!CollectionUtility.isCollectionNullOrEmpty(rules)) {
//            StringBuilder builder = new StringBuilder();
//            for (String rule : rules) {
//                builder.append("\u2714 ").append(Html.fromHtml(rule)).append("\n\n");
//            }
//            return builder.toString();
//        }
//        return "";
//    }

    public static String trim(String str) {
        if(str != null) {
            str = str.trim();
            return str.replaceAll("\t\r\n", "");
        }
        return str;
    }

    public static boolean isNotNull(String...args){
        for(String i : args){
            if(i==null || i.trim().equalsIgnoreCase("") ||
                    i.trim().equalsIgnoreCase("null")){
                return false;
            }
        }

        return true;
    }
}
