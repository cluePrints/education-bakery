package components{
	import mx.controls.dataGridClasses.DataGridColumn;
	
public class Util{
	namespace components;
	use namespace components;
	import mx.collections.ArrayCollection;
	/**
	 * Returns position of item in collection by it's id and name
	 * 
	 * Example getItemPos(availableUnits, 'unit', 3)
	 **/
	public function getItemPos(items:*, itemName:String, itemId:int):int{
		var result=0;
		for each (var unit in items[itemName]){ 
			if (unit.id == itemId) {
				return result;
			}
			result++;
		} 		
		return -1;		
	}
	
	/**
	 * Returns item by id
	 * 
	 **/
	public function getItemById(items:*, itemName:String, _id:*):*{
		var result=null;
		for each (var unit in items[itemName]){ 
			if (unit.id == _id) {
				result = unit;
			}
		} 
		return result;
	}
	
	/**
	 * Transform active state to string
	 */
	public function activeToString(item:Object, column:DataGridColumn):String{				
		return (item.active>0 ? "+" : "-"); 
	}
	public function minimizedToString(item:Object, column:DataGridColumn):String{				
		return (item.active>0 ? "мин." : "макс"); 
	}
	public function fixedToString(item:Object, column:DataGridColumn):String{				
		return (item.active>0 ? "конст." : "перем."); 
	}
}
}