package mina.utils;

import java.util.Random;

/**
 * @author 科兴第一盖伦
 * @version 2018/12/28
 */
public class FindByByte
{
    public static void main(String[] args)
    {
        find2Byte(4);
        findByByte();
    }

    private static void find2Byte(int n)
    {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // int array（random 10^7 number）
        int intLength = 1 << n;
        int[] intArray = new int[intLength];
        for (int i = 0; i < intLength; i++)
        {
            intArray[i] = random.nextInt(intLength);
            sb.append(intArray[i]).append(" ");
        }

        // print
        System.out.println(sb.toString());

        // byte mark array
        int multiple = 1 << 3;
        int byteLength = intLength / multiple;
        byte[] byteArray = new byte[byteLength];

        // foreach
        sb = new StringBuilder();
        for (int num : intArray)
        {
            int index = num / multiple;
            int remaining = num % multiple;
            int result = byteArray[index] & (1 << remaining);
            if (result == 0)
                byteArray[index] += (1 << remaining);
            else if (result > 0)
                sb.append(num).append(" ");
        }

        // print
        System.out.println(sb.toString());
    }

    private static void findByByte()
    {
        int num = 0;
        int M = 3;
        int N = 2;
        int[] sourceArray = new int[]{2, 2, 2, 6, 6, 8, 8, 7, 7};
        int[] resultArray = new int[32];
        for (int i = 0; i < 32; i++)
        {
            for (int aSourceArray : sourceArray)
                resultArray[i] += (aSourceArray >> i) & 1;

            num |= resultArray[i] % N > 0 ? 1 << i : 0;
        }

        System.out.println("num:[" + num + "]");
    }
}