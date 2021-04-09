package db;

//리콜 db의 회사,제품명에서 주식회사,숫자,- 같은 문자를 제거하는 클래스

public class eraser {
	public String erase(String a) {
		if(a.contains("(주)")){
		String match = "[\u0028주\u0029]";
		a= a.replaceAll(match, "");
		if(a.contains("식회사")) {a = a.replaceAll("식회사", "");}
		}
		
		if(a.contains("주식회사")) {
			a= a.replaceAll("주식회사", "");
		}
		
			a=a.replaceAll("[0-9]", "");
		
		
			a= a.replaceAll("-", "");
		
		return a;
		
	}

}
