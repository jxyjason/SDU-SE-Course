package 链表;

public class _206_反转链表 {
	
	public ListNode reverseList(ListNode head) {
		if (head == null || head.next == null) return head;
	
		ListNode newHead = reverseList(head.next);
		head.next.next = head;
		head.next = null;
		return newHead;
    }

	
	public ListNode reverseList2(ListNode head) {
		if (head == null || head.next == null) return head;
	
		ListNode newHead = null;
		while (head != null) {
			ListNode tmp = head.next;
			head.next = newHead;
			newHead = head;
			head = tmp;
		}
		
		return newHead;
    }

}
