public class Phonenumber {

	private Country country;
	private String number;
	private String prefix;

	public Phonenumber(Country country, String number, String prefix) {
		this.country = country;
		this.number = number;
		this.prefix = prefix;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}