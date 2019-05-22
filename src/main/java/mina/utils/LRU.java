package mina.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 科兴第一盖伦
 */
public class LRU
{
    public static void main(String[] args)
    {
        // test LRUCacheLinkedHashMap
        LRUCacheLinkedHashMap<Integer, Integer> lruCacheLinkedHashMap = new LRUCacheLinkedHashMap<>(3);
        lruCacheLinkedHashMap.put(1, 1);
        lruCacheLinkedHashMap.put(2, 2);
        lruCacheLinkedHashMap.put(3, 3);
        lruCacheLinkedHashMap.get(3);
        lruCacheLinkedHashMap.get(1);
        lruCacheLinkedHashMap.put(4, 4);
        System.out.println("size:" + lruCacheLinkedHashMap.keySet().size());

        // test LRUCache type 1
        LRUCache lruCache1 = new LRUCache(3, 1);
        lruCache1.set(1, 1);
        lruCache1.set(2, 2);
        lruCache1.set(3, 3);
        lruCache1.get(3);
        lruCache1.get(1);
        lruCache1.set(4, 4);
        System.out.println("size:" + lruCache1.map.size());

        // test LRUCache type 2
        LRUCache lruCache2 = new LRUCache(3, 2);
        lruCache2.set(1, 1);
        lruCache2.set(2, 2);
        lruCache2.set(3, 3);
        lruCache2.get(2);
        lruCache2.get(2);
        lruCache2.get(2);
        lruCache2.get(1);
        lruCache2.set(4, 4);
        System.out.println("size:" + lruCache2.map.size());

        // test LRUCache type 1
        LRUCache lruCache3 = new LRUCache(3, 3);
        lruCache3.set(1, 1);
        lruCache3.set(2, 2);
        lruCache3.set(3, 3);
        lruCache3.set(4, 4);
        System.out.println("size:" + lruCache3.map.size());
    }
}

/**
 * 1.重写LinkedHashMap的removeEldestEntry方法
 * 并发可通过显示锁或者sync等来控制
 */
class LRUCacheLinkedHashMap<K,V> extends LinkedHashMap<K,V>
{
    // 最大容量
    private int maximumCapacity;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public LRUCacheLinkedHashMap(int maximumCapacity)
    {
        super(maximumCapacity, DEFAULT_LOAD_FACTOR, true);
        this.maximumCapacity = maximumCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest)
    {
        return size() > maximumCapacity;
    }
}

/**
 * 2.HashMap + 双向链表/命中次数/时间
 * 排列未作额外处理，还是会把最新访问的放在链表尾部
 * 并发可通过显示锁或者sync等来控制
 */
class LRUCache
{
    class Node
    {
        Node pre;

        Node next;

        Integer key;

        Integer val;

        // 命中次数
        int count;

        // 最近访问时间
        long time;

        Node(Integer k, Integer v)
        {
            key = k;
            val = v;
            count = 0;
            time = 0;
        }
    }

    Map<Integer, Node> map = new HashMap<>();

    private Node head;

    private Node tail;

    private int cap;

    // 1：链表尾部删除，2：命中次数最低删除，3：访问时间最久删除
    private int type;

    public LRUCache(int capacity, int type)
    {
        cap = capacity;
        this.type = type;
        head = new Node(null, null);
        tail = new Node(null, null);
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key)
    {
        Node n = map.get(key);
        if(n != null)
        {
            n.pre.next = n.next;
            n.next.pre = n.pre;
            appendTail(n);

            n.count++;
            n.time = System.currentTimeMillis();
            return n.val;
        }

        return -1;
    }

    public void set(int key, int value)
    {
        Node n = map.get(key);
        if(n != null)
        {
            n.val = value;
            map.put(key, n);
            n.pre.next = n.next;
            n.next.pre = n.pre;
            appendTail(n);
            return;
        }

        if(map.size() == cap)
            removeByType();

        n = new Node(key, value);
        appendTail(n);
        n.count++;
        n.time = System.currentTimeMillis();
        map.put(key, n);
    }

    private void removeByType()
    {
        Node tmp = null;
        Node tmpPre = null;

        if (type == 1)
        {
            tmp = head.next;
            head.next = head.next.next;
            head.next.pre = head;
            tmp.pre = null;
            tmp.next = null;
            map.remove(tmp.key);
            return;
        }
        else if (type == 2)
        {
            int max = Integer.MAX_VALUE;
            for (Node node = head; node.next != tail; node = node.next)
            {
                Node n = node.next;
                if (n == null)
                    continue;

                if (n.count < max)
                {
                    tmpPre = node;
                    tmp = n;
                    max = n.count;
                }
            }
        }
        else if (type == 3)
        {
            long max = Long.MAX_VALUE;
            for (Node node = head; node.next != tail; node = node.next)
            {
                Node n = node.next;
                if (n == null)
                    continue;

                if (n.time < max)
                {
                    tmpPre = node;
                    tmp = n;
                    max = n.time;
                }
            }
        }

        if (tmp != null)
        {
            tmpPre.next = tmpPre.next.next;
            tmpPre.next.pre = tmpPre;
            tmp.pre = null;
            tmp.next = null;
            map.remove(tmp.key);
        }
    }

    /**
     * 放到尾部
     */
    private void appendTail(Node n)
    {
        n.next = tail;
        n.pre = tail.pre;
        tail.pre.next = n;
        tail.pre = n;
    }
}
