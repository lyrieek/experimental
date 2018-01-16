package pers.http.entity;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import pers.http.TryGet;
import pers.th.util.text.XStrings;

/**
 * 用户资料收集
 * 
 * @author Tianhao
 *
 */
public class User {

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 用户昵称
	 */
	private String name;

	/**
	 * 粉丝
	 */
	private int follower;

	/**
	 * 关注
	 */
	private int watch;

	/**
	 * 详细信息
	 */
	private String detail;

	/**
	 * 抓取时间
	 */
	private final Date createDate;

	public User(String userId) {
		createDate = new Date();
		try {
			String html = TryGet.HTTP_CLIENT.get("http://my.csdn.net/" + userId);
			Document doc = Jsoup.parse(html);
			setUserId(userId);
			setName(doc.getElementsByClass("person-nick-name").eq(0).text());
			setFollower(Integer.parseInt(doc.select(".focus_num>b").text()));
			setWatch(Integer.parseInt(doc.select(".fans_num>b").text()));
			setDetail(XStrings.join(doc.getElementsByClass("person-detail").text().split("|")));
		} catch (Exception e) {
			System.err.println(userId + ":error");
			e.printStackTrace();
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getFollower() {
		return follower;
	}

	public void setFollower(int follower) {
		this.follower = follower;
	}

	public int getWatch() {
		return watch;
	}

	public void setWatch(int watch) {
		this.watch = watch;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() { 
		return createDate;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", follower=" + follower + ", watch=" + watch + ", detail="
				+ detail + ", createDate=" + createDate + "]";
	}

	public static void main(String[] args) throws Exception {
		System.out.println(new User("zhao4zhong1"));
	}

}
