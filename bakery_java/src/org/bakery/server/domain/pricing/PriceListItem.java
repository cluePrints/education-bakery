package org.bakery.server.domain.pricing;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.production.ProductType;
import org.bakery.server.validation.CouldNotBeEmpty;
import org.bakery.server.validation.GreaterThen;

/**
 * Represents some product price
 * 
 * @author Ivan_Sobolev1
 *
 */
public class PriceListItem extends BusinessEntity {
	private static final long serialVersionUID=1L;
	private PriceList parent=new PriceList();
	private Double price;
	private ProductType product=new ProductType();
	

	@Override
	public String toString() {
		return product.getName()+" "+price+" "+parent.toString();
	}
	public PriceListItem() {
		super();
	}
	
	@CouldNotBeEmpty(message="ѕрайс-лист, к которому относитс€ эта цена на позицию продукции должен быть указан.")
	public PriceList getParent() {
		return parent;
	}
	public void setParent(PriceList parent) {
		this.parent = parent;
	}
	
	@GreaterThen(lowerLimit=0, including=false,
				message="—тоимость должна быть положительным числом.")
	@CouldNotBeEmpty(message="—тоимость единицы продукта должна быть задана.")
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@CouldNotBeEmpty(message="¬ид продукта должен быть указан.")
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
