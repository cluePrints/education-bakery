package LogComp{
	import flash.events.Event;
	
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
	public function childToString(item:Object, column:DataGridColumn):String{				
		return (item.child >0 ? "дочерн." : ""); 
	}
	public function extractItemCollection(proxy:*, collectionName:String):ArrayCollection{
		var result:ArrayCollection = null;
		if (proxy != null){
			if (proxy[collectionName] != null){
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
		Alert.show("Главный сервер приложения недоступен");
	} 
	
	/**
	 *	Try to handle common errors
	 **/ 
	public function getHttpRespondedErrors(event:*):ArrayCollection{
		var errors = event.result.errors;		
		var errorCollection:ArrayCollection;
		if (errors == null)
			return new ArrayCollection();
		
		if (errors instanceof ArrayCollection){
			errorCollection = errors
		} else {
			errorCollection = new ArrayCollection();
			errorCollection.addItem(errors);	
		}		
		return errorCollection;
	}
	public function arrayCopy(dest:ArrayCollection):ArrayCollection{
		var result:ArrayCollection = new ArrayCollection();
		if ((dest !=null) && (dest.length>0)){
			var i:int=0;
			for (i=0; i<dest.length; i++){
				result.addItem(dest.getItemAt(i));
			}
		}
		return result;	
	}
	public function removeElementsWith(dest:ArrayCollection, fldName:String, fldValue:*):ArrayCollection{
		var result:ArrayCollection = new ArrayCollection();
		if ((dest !=null) && (dest.length>0)){
			var i:int=0;
			for (i=0; i<dest.length; i++){
				var p = dest.getItemAt(i);
				if ((p!=null) && (p[fldName]!=null) && (p[fldName]!=fldValue)){
					result.addItem(p);
				}
			}
		}
		return result;
	}
	public var numberAllowedChars:ArrayCollection = new ArrayCollection(
			['0','1','2','3','4','5','6','7','8','9','.']
	);
	public function isValidNumber(a:String):Boolean {
		var valid:Boolean = true;
		var signumUsed:Boolean=false;
		var dotUsed:Boolean=false;
		var i:int=0;
		for (i=0; i<a.length; i++) {
			if (!numberAllowedChars.contains(a.charAt(i)) && (a.charAt(i)!='+') && (a.charAt(i)!='-')) {
				valid = false;	
			}
			if ((a.charAt(i)=='.') && (dotUsed)){
				valid = false
			}
			if ((a.charAt(i)=='-') || (a.charAt(i)=='+') && signumUsed){
				valid = false;
			}
			if ((a.charAt(i)=='-') || (a.charAt(i)=='+')) {
				signumUsed = true;
			}
			
			if (a.charAt(i)=='.'){
				dotUsed=true;
			}
		}
		return valid;
	}
}
}