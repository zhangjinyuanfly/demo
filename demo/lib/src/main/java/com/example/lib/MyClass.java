package com.example.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MyClass {
    public static void main(String[] args) {

        Solution s = new Solution();
        ListNode listNode2 = new ListNode(2);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode3 = new ListNode(3);
        listNode2.next = listNode4;
        listNode4.next = listNode3;

        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);
        ListNode listNode44 = new ListNode(4);
        listNode5.next = listNode6;
        listNode6.next = listNode44;
        ListNode re = s.addTwoNumbers(listNode2, listNode5);
        System.out.println(re.val);
        System.out.println(re.next.val);
        System.out.println(re.next.next.val);

//        System.out.print(s.lengthOfLongestSubstring("tmmzuxt"));
//        System.out.print(s.lengthOfLongestSubstring("abcabcbb"));
//        System.out.print(s.lengthOfLongestSubstring("bbbbb"));
//        System.out.print(s.lengthOfLongestSubstring("dvdf"));
//        System.out.println(s.findMedianSortedArrays(new int[]{1},new int[]{2,3}));

    }




}

 //Definition for singly-linked list.
  class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

class Solution {
//    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
//        int len = nums1.length+nums2.length;
//        int m = (len+1)/2;
//        if(len == 2) {
//            m = len;
//        }
//
//        ArrayList count = new ArrayList();
//
//        int i =0;
//        int n1 = 0,n2 = 0;
//        int v1,v2;
//        while (i < m) {
//            if (n1<nums1.length) {
//                v1 = nums1[n1];
//            } else {
//                v1 = 0;
//            }
//            if(n2 < nums2.length) {
//                v2 = nums2[n2];
//            } else {
//                v2 = 0;
//            }
//            if(v1 > v2) {
//                n1++;
//                count[i] = v1;
//            } else {
//                n2++;
//                count[i] = v2;
//            }
//            i++;
//        }
//        if(len%2 == 0) {
//            return ((double)(count[m-1]+count[m-2]))/2f;
//        }
//        return count[m-1];
//    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode r = result;
        int carry = 0;
        while(l1 != null || l2 != null || carry != 0) {
            int n1 = l1!=null?l1.val:0;
            int n2 = l2!=null?l2.val:0;
            int n = n1+n2+carry;
            if(n >= 10) {
                carry = n/10;
            } else {
                carry = 0;
            }
            ListNode listNode = new ListNode(n%10);
            r.next = listNode;
            r = listNode;

            l1 = l1.next;
            l2 = l2.next;

        }
        return result.next;

    }


    public int lengthOfLongestSubstring(String s) {

        char[] chars = s.toCharArray();
        int len = 0;
        int max = 0;
        int start = 0;

        HashMap<Character, Integer> set = new HashMap();
        for(int i = 0;i<chars.length;i++) {
            char c = chars[i];
            if(set.containsKey(c)) {
                len = set.get(c);
                if(len >= start) {
                    max = Math.max(i-start,max);
                    start = len;
                }
            }
            max = Math.max(max, i-start+1);
            set.put(c, i+1);
        }

        return max;
    }

}
