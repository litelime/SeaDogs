package domain;

public class Location {
	
	String locationId;
	String street;
	String city;
	String country;
	String state;
	String zip;
        String userId;
	
	public Location() {
		super();
	}
	
	public Location(String locationId, String userID, String street, String city, String state, String country, String zip) {
		super();
		this.locationId = locationId;
		this.street = street;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
                this.userId = userID;
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

        public String getAddress(){
            
            String address = this.street + ", " + this.city + ", " + this.state +", "+this.zip;
            return address;
        }
        
	@Override
	public String toString() {
                return street + " St " + city + ", "
				+ state + " " + country + " " + zip;
	}
        
        @Override
        public boolean equals(Object o){
            return (this.toString().equals(o.toString()));
        }
	
	
	
	
}

