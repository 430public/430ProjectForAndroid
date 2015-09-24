package com.projectforandroid.utils.objectutils;

/**
 * 收集 by 大灯泡 on 2015/9/20.
 */

/**
 * Object Utils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-10-24
 */
public class ObjectUtils {

    private ObjectUtils() {
        throw new AssertionError();
    }

    /**
     * 比较两个对象是否相等
     *
     * @return <ul>
     * <li>都为空，返回true</li>
     * <li>return actual.{@link Object#equals(Object)}</li>
     * </ul>
     */
    public static boolean isEquals(Object actual, Object expected) {
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }

    /**
     * 比较两个同类型对象
     * <ul>
     * <strong>返回结果</strong>
     * <li>如果 v1 > v2, 返回 1</li>
     * <li>如果 v1 = v2, 返回 0</li>
     * <li>如果 v1 < v2, 返回 -1</li>
     * </ul>
     * <ul>
     * <strong>判断规则</strong>
     * <li>如果 v1 is null, v2 is null, 则返回 0</li>
     * <li>如果 v1 is null, v2 is not null, 则返回 -1</li>
     * <li>如果 v1 is not null, v2 is null, 则返回 1</li>
     * <li>return v1.{@link Comparable#compareTo(Object)}</li>
     * </ul>
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <V> int compare(V v1, V v2) {
        return v1 == null ? (v2 == null ? 0 : -1)
            : (v2 == null ? 1 : ((Comparable) v1).compareTo(v2));
    }
}
