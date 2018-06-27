package com.child.common.utils.ip;

public class IPResponse {
	private int code;

	private Result data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Result getData() {
		return data;
	}

	public void setData(Result data) {
		this.data = data;
	}

	public static class Result {
		private String ip;
		private String country;
		private String area;
		private String region;
		private String city;

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

	}
}
