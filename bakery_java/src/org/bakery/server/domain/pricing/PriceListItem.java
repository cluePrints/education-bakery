package org.bakery.server.domain.pricing;

import org.bakery.server.controllers.validation.CommonFormValidator;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.production.ProductType;
import org.springframework.validation.Errors;

/**
 * Represents some product price
 * 
 * @author Ivan_Sobolev1
 *
 */
public class PriceListItem extends BusinessEntity {
	private static final long serialVersionUID=1L;
	private PriceList parent=new PriceList();
	private float price;
	private ProductType product=new ProductType();
	
	
	@Override
	public void validate(Errors errors) {
		if (CommonFormValidator.isEmptyEntity(parent))
			errors.reject(null, "priceListItem.parent.empty");
		if (CommonFormValidator.isEmptyEntity(product))
			errors.reject(null, "priceListItem.product.empty");
	}
	@Override
	public String toString() {
		return product.getName()+" "+price+" "+parent.toString();
	}
	public PriceListItem() {
		super();
	}
	public PriceList getParent() {
		return parent;
	}
	public void setParent(PriceList parent) {
		this.parent = parent;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public ProductType getProduct() {
		return product;
	}
	public void setProduct(ProductType product) {
		this.product = product;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		final PriceListItem other = (PriceListItem) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}
	
	
}
