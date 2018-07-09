/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;

/**
 *
 * @author syntel
 */
public class SpecialMenu {
        String id;
	String name;
	char vegetarian;
	String description;
	String slot_ID;
	String photo;
	float price;
        float discount;
	ArrayList<String> itemIds;
        public SpecialMenu() {
            itemIds = new ArrayList<>();
        }
	public SpecialMenu(String id, String name, char vegetarian, String type, String description, String slot_ID, String photo, float price, float discount, ArrayList<String> itemIds) {
		super();
		this.id = id;
		this.name = name;
		this.vegetarian = vegetarian;
		this.description = description;
		this.slot_ID = slot_ID;
		this.photo = photo;
		this.price = price;
                this.discount = discount;
                this.itemIds = itemIds;
	}
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getVegetarian() {
		return vegetarian;
	}

	public void setVegetarian(char vegetarian) {
		this.vegetarian = vegetarian;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSlot_ID() {
		return slot_ID;
	}

	public void setSlot_ID(String slot_ID) {
		this.slot_ID = slot_ID;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

        public float getDiscount() {
            return discount;
        }

        public void setDiscount(float discount) {
            this.discount = discount;
        }

        public ArrayList<String> getItemIds() {
            return itemIds;
        }

        public void setItemIds(ArrayList<String> itemIds) {
            this.itemIds = itemIds;
        }
        
        public void addItemId(String itemId) {
            itemIds.add(itemId);
        }

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", vegetarian=" + vegetarian + ", description=" + description
				+ ", slot_ID=" + slot_ID + ", photo=" + photo + ", price=" + price + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + ((slot_ID == null) ? 0 : slot_ID.hashCode());
		result = prime * result + vegetarian;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (slot_ID == null) {
			if (other.slot_ID != null)
				return false;
		} else if (!slot_ID.equals(other.slot_ID))
			return false;
		
		if (vegetarian != other.vegetarian)
			return false;
		return true;
	}
}