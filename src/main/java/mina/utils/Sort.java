package mina.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 排序算法
 *
 * @author 科兴第一盖伦
 * @version 2018/11/07
 */
public class Sort
{
    public static void main(String[] args)
    {
        int[] test = {1, 3, 2, 5, 4, 1, 6, 8, 7, 9, 2, 3, 3, 3, 6, 6, 6, 7, 7, 7};

//        bubbleSort(test);
//        insertSort(test);
//        selectSort(test);
//        quickSort(test, 0, test.length - 1);
//        shellSort(test);
//        mergeSort(test, 0, test.length - 1, new int[test.length]);
//        radixSort(test);
//        heapSort(test);

        System.out.println(Arrays.toString(test));
    }

    /**
     * 冒泡排序
     * 稳定
     * 时间复杂度：最好O(n),其它O(n^2)
     * 空间复杂度：O(1)
     */
    public static void bubbleSort(int[] arry)
    {
        for (int i = 0; i < arry.length - 1; i++)
        {
            boolean flag = false;
            for (int j = 0; j < arry.length - 1; j++)
            {
                if (arry[j] > arry[j + 1])
                {
                    int temp = arry[j];
                    arry[j] = arry[j + 1];
                    arry[j + 1] = temp;

                    flag = true;
                }
            }

            if (!flag)
                break;
        }
    }

    /**
     * 插入排序
     * 稳定
     * 时间复杂度：最好O(n),其它O(n^2)
     * 空间复杂度：O(1)
     */
    public static void insertSort(int[] arry)
    {
        for (int i = 1; i < arry.length; i++)
        {
            for (int j = i; j > 0; j--)
            {
                if (arry[j] < arry[j - 1])
                {
                    int temp = arry[j];
                    arry[j] = arry[j - 1];
                    arry[j - 1] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     * 不稳定
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    public static void selectSort(int[] arry)
    {
        for (int i = arry.length - 1; i > 0; i--)
        {
            int maxIndex = i;
            int max = arry[i];
            for (int j = 0; j < i; j++)
            {
                if (arry[j] > max)
                {
                    maxIndex = j;
                    max = arry[j];
                }
            }

            if (maxIndex != i)
            {
                int temp = arry[maxIndex];
                arry[maxIndex] = arry[i];
                arry[i] = temp;
            }
        }
    }

    /**
     * 快速排序
     * 不稳定
     * 时间复杂度：最好,平均O(nlog2n)，最差O(n^2)
     * 空间复杂度：O(log2n)
     */
    public static void quickSort(int[] arry, int left, int right)
    {
        int _left = left;
        int _right = right;
        if (left >= right)
            return;

        // 基准值base
        int base = arry[(left + right) / 2];
        int empty = base;
        while (left != right)
        {
            while (left < right && arry[left] <= base)
                left++;

            arry[empty] = arry[left];
            empty = left;

            while (left < right && arry[right] >= base)
                right--;

            arry[empty] = arry[right];
            empty = right;
        }

        arry[empty] = base;
        quickSort(arry, _left, left - 1);
        quickSort(arry, right + 1, _right);
    }

    /**
     * 希尔排序
     * 不稳定
     * 时间复杂度：O(nlog2n)～O(n^2)
     * 空间复杂度：O(1)
     */
    public static void shellSort(int[] arry)
    {
        int d = arry.length;
        while (true)
        {
            d /= 2;
            for (int i = 0; i < d; i++)
            {
                // 插入排序（此处可替换为其它排序方法）
                for (int j = i + d; j < arry.length; j += d)
                {
                    for (int k = j; k > 0; k -= d)
                    {
                        if (arry[k] < arry[k - 1])
                        {
                            int temp = arry[k];
                            arry[k] = arry[k - 1];
                            arry[k - 1] = temp;
                        }
                    }
                }
            }

            if (d == 1)
                break;
        }
    }

    /**
     * 归并排序
     * 稳定
     * 时间复杂度：O(nlog2n)
     * 空间复杂度：O(n)
     */
    public static void mergeSort(int[] arry, int left, int right, int[] temp)
    {
        if (left >= right)
            return;

        int base = (left + right) / 2;
        mergeSort(arry, left, base, temp);
        mergeSort(arry, base + 1, right, temp);

        // merge
        int t = 0;
        int i = left;
        int j = base + 1;
        while (i <= base && j <= right)
        {
            if (arry[i] < arry[j])
                temp[t++] = arry[i++];
            else
                temp[t++] = arry[j++];
        }

        while (i <= base)
            temp[t++] = arry[i++];

        while (j <= right)
            temp[t++] = arry[j++];

        t = 0;
        while (left <= right)
            arry[left++] = temp[t++];
    }

    /**
     * 基数排序
     * 稳定
     * 时间复杂度：O(kn)，其中n是排序元素个数，k是数字位数
     * 空间复杂度：O(kn)
     */
    public static void radixSort(int[] arry)
    {
        int max = arry[0];
        for (int i = 1; i < arry.length; i++)
        {
            if (arry[i] > max)
                max = arry[i];
        }

        int time = 0;
        while (max > 0)
        {
            max /= 10;
            time++;
        }

        List<ArrayList> queue = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            ArrayList<Integer> queueIn = new ArrayList<>();
            queue.add(queueIn);
        }

        for (int i = 0; i < time; i++)
        {
            for (int anArry : arry)
            {
                int x = anArry % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                ArrayList<Integer> queue2 = queue.get(x);
                queue2.add(anArry);
                queue.set(x, queue2);
            }

            int count = 0;
            for (int k = 0; k < 10; k++)
            {
                while (queue.get(k).size() > 0)
                {
                    ArrayList<Integer> queue3 = queue.get(k);
                    arry[count] = queue3.get(0);
                    queue3.remove(0);
                    count++;
                }
            }
        }
    }

    /**
     * 堆排序
     * 不稳定
     * 时间复杂度：O(nlog2n)
     * 空间复杂度：O(1)
     */
    public static void heapSort(int[] arry)
    {
        int n = arry.length;
        int i;
        int temp;

        for (i = (n - 1) / 2; i >= 0; i--)
            CreateHeap(arry, n, i);

        for (i = n - 1; i > 0; i--)
        {
            temp = arry[0];
            arry[0] = arry[i];
            arry[i] = temp;

            CreateHeap(arry, i, 0);
        }
    }

    private static void CreateHeap(int a[], int n, int h)
    {
        int i, j, flag;
        int temp;

        i = h;
        j = 2 * i + 1;
        flag = 0;
        temp = a[i];
        while (j < n && flag != 1)
        {
            if (j < n - 1 && a[j] < a[j + 1])
                j++;

            if (temp > a[j])
                flag = 1;
            else
            {
                a[i] = a[j];
                i = j;
                j = 2 * i + 1;
            }
        }

        a[i] = temp;
    }
}
