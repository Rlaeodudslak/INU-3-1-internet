package db;

/*
 * name(제품명),company(회사명),link(상세설명링크) 등을 정의하고 불러와 사용할 수 있게 하는 클래스
*/
public class info{
	private String name,company,link;
	
	public info() {}
	
	public String getname() {return name;}
	public String getcompany() {return company;}
	public String getlink() {return link;}
	
	public void setname(String name) {this.name=name;}
	public void setcompany(String company) {this.company=company;}
	public void setlink(String link) {this.link=link;}

	@Override
	public String toString() {
		return "info{" +
				"name='" + name + '\'' +
				", company='" + company + '\'' +
				", link='" + link + '\'' +
				'}';
	}
}