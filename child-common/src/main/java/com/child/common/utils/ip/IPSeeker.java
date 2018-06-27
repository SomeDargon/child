package com.child.common.utils.ip;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class IPSeeker {
	private static final String QUERY_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

	private static final String UNKNOWN_IP = "unknown";

	public static String getCityFromRequest(HttpServletRequest request) {
		return getRegionByIP(getIpAddress(request));
	}

	public static String getRegionByIP(String ip) {
		try {
			String s = AolForwarding(ip);
			JSONObject jsonObject = JSONObject.parseObject(s);
			if(jsonObject.getString("code").equals("0")) {
				JSONObject jsonObject1 = JSONObject.parseObject(jsonObject.getString("data"));
				return  jsonObject1.getString("region");
			/*HttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet(QUERY_URL + ip);
			HttpResponse response = client.execute(get);
			System.out.println(response);*/
			/*String json = EntityUtils.toString(response.getEntity());
			IPResponse resp = new Gson().fromJson(json, IPResponse.class);
			if (resp.getCode() == 0) {
				return resp.getData().getCity();
			}*/
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String AolForwarding(String data){
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = QUERY_URL +data;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
				System.out.println(result);
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}


	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static void main(String[] args) {
		System.out.println(getRegionByIP("61.167.241.181"));
	}
}
