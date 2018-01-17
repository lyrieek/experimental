package pers.http.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import pers.http.TryGet;
import pers.http.UserManager;
import pers.th.util.date.DateFormat;
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
	 * 他关注的人数量
	 */
	private int watch;

	/**
	 * 粉丝数量
	 */
	private int follower;

	/**
	 * 详细信息
	 */
	private String detail;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 抓取时间
	 */
	private final Date createDate;

	/**
	 * 抓取时间
	 */
	private transient String[] relationalUser;

	public User(String userId) {
		createDate = new Date();
		try {
			String html = TryGet.HTTP_CLIENT.get("http://my.csdn.net/" + userId);
			Document doc = Jsoup.parse(html);
			setUserId(userId);
			if (doc.getElementsByClass("upgrading-error").size() != 0) {
				setRemark("upgrading error (被封号)");
				return;
			}
			Elements elems = doc.select(".header.clearfix>a");
			relationalUser = elems.eachAttr("href").toArray(new String[0]);
			setName(doc.getElementsByClass("person-nick-name").eq(0).text());
			setFollower(Integer.parseInt(doc.select(".focus_num>b").text()));
			setWatch(Integer.parseInt(doc.select(".fans_num>b").text()));
			setDetail(XStrings.join(doc.getElementsByClass("person-detail").text().split("|")));
		} catch (Exception e) {
			System.err.println(userId + ":error");
			e.printStackTrace();
		}
	}

	public String[] getRelationalUser() {
		if (relationalUser == null) {
			return new String[] { "" };
		}
		return relationalUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		if (name == null) {
			return "[userId=" + userId + ", remark=" + remark + ", createDate=" + DateFormat.DEFAULT.format(createDate)
					+ "]";
		}
		return "[userId=" + userId + ", name=" + name + ", follower=" + watch + ", watch=" + follower + ", detail="
				+ detail + ", createDate=" + DateFormat.DEFAULT.format(createDate) + "]";
	}

	public static void main(String[] args) throws Exception {
		Set<String> users = UserManager.users();
		Set<String> relational = new HashSet<>();
		for (String user : users) {
			User item = new User(user);
			relational.addAll(Arrays.asList(item.getRelationalUser()));
			System.out.println(item);
			Thread.sleep(500);
		}
		System.out.println(users.size());
		users.addAll(relational);
		System.out.println(users.size());
		UserManager.writeUser();
	}

}
