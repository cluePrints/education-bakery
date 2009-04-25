package components{
	import flash.events.Event;
	
	import mx.charts.chartClasses.InstanceCache;
	import mx.controls.Alert;
	import mx.controls.dataGridClasses.DataGridColumn;
	
public class Util{
	namespace components;
	use namespace components;
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	/**
	 * Filters collection to get items with field active!=0
	 *  
	 **/
	public function getActiveItems(items:ArrayCollection):ArrayCollection{
		var result = new ArrayCollection();		
		for each (var item in items){
			if (item.hasOwnProperty('active')){
				if (item.active > 0){
					result.addItem(item);
				}
			}			
		} 		
		return result;		
	}
	
	/**
	 * Returns position of item in collection by it's id and name
	 * 
	 * Example getItemPos(availableUnits, 'unit', 3)
	 **/
	public function getItemPos(items:*, itemId:int):int{
		var result=0;
		for each (var unit in items){ 
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
	public function getItemById(items:*, _id:*):*{
		var result=null;
		if (items == null)
			return null;
		for each (var unit in items){ 
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
		return (item.minimize>0 ? "мин." : "макс"); 
	}
	public function consumedToString(item:Object, column:DataGridColumn):String{				
		return (item.consumed>0 ? "in" : "out"); 
	}
	public function fixedToString(item:Object, column:DataGridColumn):String{				
		return (item.changable >0 ? "перем." : "конст."); 
	}
	public function extractItemCollection(proxy:*, collectionName:String):ArrayCollection{
		var result:ArrayCollection = null;
		if (proxy != null){
			if (proxy.hasOwnProperty(collectionName)){
				if (proxy[collectionName] != null){
					if (proxy[collectionName] instanceof ArrayCollection) {
						result = proxy[collectionName];
					} else {					
						result = new ArrayCollection();
						result.addItem(proxy[collectionName]);	
					}	
				}
			}
		}
		return result;	
	}
	
	/**
	 * Common server exception handling function
	 **/
	public function httpFaultHandler(event:Event):void{
			Alert.show("HTTP error");

	} 
}
}