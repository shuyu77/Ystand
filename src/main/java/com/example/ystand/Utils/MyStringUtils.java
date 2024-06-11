package com.example.ystand.Utils;

public class MyStringUtils {
    public static String truncateString(String content, int num) {
        if (content == null || content.isEmpty()) {
            return content;
        }

        int count = 0;
        StringBuilder truncatedContent = new StringBuilder();
        for (char c : content.toCharArray()) {
            if (count >= num) {
                break;
            }
            truncatedContent.append(c);
            count += isChineseCharacter(c) ? 2 : 1;
        }

        if (count > num) {
            truncatedContent.deleteCharAt(truncatedContent.length() - 1);
            truncatedContent.append("...");
        }

        return truncatedContent.toString();
    }

    public static boolean isChineseCharacter(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || block == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || block == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT);
    }
}
