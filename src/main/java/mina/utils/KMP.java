package mina.utils;

/**
 * kmp算法
 * 时间复杂度O(n + m)
 * 相关算法：BM，Sunday
 *
 * @author 科兴第一盖伦
 * @version 2018/11/06
 */
public class KMP
{
    public static void main(String[] args)
    {
        int result = kmp("DDACDDADDDABDDAD", "DDABDDAD");
        System.out.println("result:" + result);
    }

    public static int[] getNext(String p)
    {
        char[] pArray= p.toCharArray();
        int length = p.length();
        int[] next = new int[length];
        next[0] = -1;
        int k = -1;
        int j = 0;

        while (j < length - 1)
        {
            if (k == -1 || pArray[j] == pArray[k])
            {
                ++j;
                ++k;

                if (pArray[j] != pArray[k])
                    next[j] = k;
                else
                    next[j] = next[k];
            }
            else
            {
                k = next[k];
            }
        }

        return next;
    }

    public static int kmp(String s, String p)
    {
        int sLength = s.length();
        int pLength = p.length();
        char[] sArray= s.toCharArray();
        char[] pArray= p.toCharArray();
        int[] next = getNext(p);
        int i = 0;
        int j = 0;
        while (i < sLength && j < pLength)
        {
            if (j == -1 || sArray[i] == pArray[j])
            {
                i++;
                j++;
            }
            else
            {
                j = next[j];
            }
        }

        if (j == pLength)
            return i - j;
        else
            return -1;
    }
}
