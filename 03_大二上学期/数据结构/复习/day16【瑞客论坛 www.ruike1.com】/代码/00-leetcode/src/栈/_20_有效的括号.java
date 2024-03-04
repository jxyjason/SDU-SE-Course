package 栈;

import java.util.HashMap;
import java.util.Stack;

public class _20_有效的括号 {
	private static HashMap<Character, Character> map = new HashMap<>();
	static {
		// key - value
		map.put('(', ')');
		map.put('{', '}');
		map.put('[', ']');
	}
	
	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<>();
		
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (map.containsKey(c)) { // 左括号
				stack.push(c);
			} else { // 右括号
				if (stack.isEmpty()) return false;
				
				if (c != map.get(stack.pop())) return false;
			}
		}
		return stack.isEmpty();
    }
	
	public boolean isValid1(String s) {
		Stack<Character> stack = new Stack<>();
		
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c == '(' || c == '{' || c == '[') { // 左括号
				stack.push(c);
			} else { // 右括号
				if (stack.isEmpty()) return false;
				
				char left = stack.pop();
				if (left == '(' && c != ')') return false;
				if (left == '{' && c != '}') return false;
				if (left == '[' && c != ']') return false;
			}
		}
		return stack.isEmpty();
    }

	public boolean isValid2(String s) {
		while (s.contains("{}")
				|| s.contains("[]")
				|| s.contains("()")) {
			s = s.replace("{}", "");
			s = s.replace("()", "");
			s = s.replace("[]", "");
		}
		return s.isEmpty();
    }
	
}
